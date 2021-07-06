package android.singidunum.ac.parkirajse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText inputUsernameLogin,inputPasswordLogin;
    private Button buttonPrijaviSePrijava;
    private TextView labelRegInLogin;
    private ProgressBar progressBar;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        inputUsernameLogin=findViewById(R.id.inputUsernameLogIn);
        inputPasswordLogin=findViewById(R.id.inputPasswordLogin);

        progressBar=findViewById(R.id.progressBarLogin);

        buttonPrijaviSePrijava=findViewById(R.id.buttonPrijaviSePrijava);

        labelRegInLogin=findViewById(R.id.labelRegInLogin);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        buttonPrijaviSePrijava.setOnClickListener(this);
        labelRegInLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonPrijaviSePrijava){
            loginUser();
            progressBar.setVisibility(View.VISIBLE);
        }

        else if(view.getId()==R.id.labelRegInLogin){
            startActivity(new Intent(getApplicationContext(),Register.class));
        }
    }

    private void loginUser() {
            String username = inputUsernameLogin.getText().toString();
            String password= inputPasswordLogin.getText().toString();

            // validacija unetih podataka
            if(TextUtils.isEmpty(username)){
                inputUsernameLogin.setError("Username je obavezan!");
                inputUsernameLogin.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                inputPasswordLogin.setError("Password je obavezan!");
                inputPasswordLogin.requestFocus();
                return;
            }
            if(password.length()<6){
                inputPasswordLogin.setError("Password mora imati 6 ili vise karaktera!");
                inputPasswordLogin.requestFocus();
                return;
            }

            User user= new User(username,password);

        //pravimo poziv u kome prosleđujemo usera kojeg smo napravili od unetih podataka
        //i pozivamo isLogin metodu koja poziva metodu isExist u backendu
        //ukoliko postoji user sa unetim parametrima on vraca usera kao ceo objekat i mi izvlaćimo njegove podatke, radi kasnijeg manipulisanja
        //ukoliko ne postoji izbacuije odgovarajuću poruku.
        //Toast.makeText(Login.this,"Uspesno ste ulogovani.", Toast.LENGTH_LONG).show();

        Call<User> call1 = apiInterface.isLogIn(user);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User re = response.body();
                if(re!=null){
                    //Toast.makeText(Login.this,"Uspesno ste ulogovani.", Toast.LENGTH_LONG).show();
                    //napravili smo instancu objekta za cuvanje (lokalni database)
                    SharedPreferences preferences= getSharedPreferences("userLogin", MODE_PRIVATE);
                    SharedPreferences.Editor savedUser = preferences.edit();
                    savedUser.putInt("user_id",re.userId);
                    savedUser.putString("name",re.name);
                    savedUser.putString("lastName",re.lastName);
                    savedUser.putString("email",re.email);
                    savedUser.putString("username",re.username);
                    savedUser.putString("password",re.password);
                    savedUser.putString("phone",re.phone);
                    savedUser.commit();
                    
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }

            //ukoliko uneti podaci nisu ispravni
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Login.this,"Pogresan username ili password.", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}
