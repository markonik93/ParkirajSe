package android.singidunum.ac.parkirajse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Data extends AppCompatActivity implements View.OnClickListener{
    private EditText inputImeData, inputPrezimeData, inputEmailData, inputUsernameData, inputPasswordData, inputKontaktData;
    private Button buttonObrisiData,buttonVratiSeData , buttonIzmeniData ;
    private APIInterface apiInterface;
    private SharedPreferences preferences;
    private SharedPreferences.Editor savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initComponents();
        prepairedData();

    }
    private void initComponents() {
        inputImeData = findViewById(R.id.inputImeData);
        inputPrezimeData = findViewById(R.id.inputPrezimeData);
        inputEmailData = findViewById(R.id.inputEmailData);
        inputUsernameData = findViewById(R.id.inputUsernameData);
        inputPasswordData = findViewById(R.id.inputPasswordData);
        inputKontaktData = findViewById(R.id.inputKontaktData);

        buttonObrisiData = findViewById(R.id.buttonObrisiData);
        buttonObrisiData.setOnClickListener(this);

        buttonIzmeniData = findViewById(R.id.buttonIzmeniData);
        buttonIzmeniData.setOnClickListener(this);

        buttonVratiSeData = findViewById(R.id.buttonVratiSeData);
        buttonVratiSeData.setOnClickListener(this);

        preferences= getSharedPreferences("userLogin", MODE_PRIVATE);
        savedUser = preferences.edit();
        apiInterface = APIClient.getClient().create(APIInterface.class);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonIzmeniData) {
            editData();
            //Toast.makeText(Data.this, userUpdate.toString(), Toast.LENGTH_LONG).show();
        } else if (view.getId() == R.id.buttonVratiSeData) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (view.getId() == R.id.buttonObrisiData) {
            deleteUser();
        }
    }
    private void prepairedData() {
        //podaci usera koji se ulogovao
        String name = preferences.getString("name", null);
        String lastName = preferences.getString("lastName", null);
        String email = preferences.getString("email", null);
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        String phone = preferences.getString("phone", null);

        inputImeData.setText(name, EditText.BufferType.EDITABLE);
        inputPrezimeData.setText(lastName, TextView.BufferType.EDITABLE);
        inputEmailData.setText(email, TextView.BufferType.EDITABLE);
        inputUsernameData.setText(username, TextView.BufferType.EDITABLE);
        inputPasswordData.setText(password, TextView.BufferType.EDITABLE);
        inputKontaktData.setText(phone, TextView.BufferType.EDITABLE);
    }

    //popunjava lepo gore , ali kad se izmene nece da posalje fejluje
    private void editData(){
        int userId = preferences.getInt("user_id", 0);
        String ime = inputImeData.getText().toString().trim();
        String prezime = inputPrezimeData.getText().toString().trim();
        String email = inputEmailData.getText().toString().trim();
        String username = inputUsernameData.getText().toString().trim();
        String password = inputPasswordData.getText().toString().trim();
        String kontakt = inputKontaktData.getText().toString().trim();

        User userUpdate = new User(userId,ime,prezime,email,username,password,kontakt);
        //Toast.makeText(Data.this, userUpdate.toString(), Toast.LENGTH_LONG).show();

        Call<User> callUserUpdate = apiInterface.updateUser(new User(userId,ime,prezime,email,username,password,kontakt));
        callUserUpdate.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User res = response.body();
                if(res!=null) {
                    Toast.makeText(Data.this, "Podaci su uspešno promenjeni.", Toast.LENGTH_LONG).show();
                    savePreferences(userUpdate);
                    startActivity(new Intent(Data.this, MainActivity.class));
                }else{
                    Toast.makeText(Data.this,"Nema promene.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Data.this,"Došlo je do greške.", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void savePreferences(User user){
        savedUser.putString("name",user.name);
        savedUser.putString("lastName",user.lastName);
        savedUser.putString("email",user.email);
        savedUser.putString("username",user.username);
        savedUser.putString("password",user.password);
        savedUser.putString("phone",user.phone);
        savedUser.commit();
    }

    private void deleteUser(){
        Call<String> userCall = apiInterface.deleteUserById(preferences.getInt("user_id",0));
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                savedUser.clear().commit();
                startActivity(new Intent(Data.this,Login.class));
            }
        });
    }
}