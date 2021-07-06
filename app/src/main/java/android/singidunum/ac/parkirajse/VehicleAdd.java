package android.singidunum.ac.parkirajse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleAdd extends AppCompatActivity implements View.OnClickListener {
    private Button buttonVratiSeDataAdd , buttonDodajAutoAdd;
    private EditText inputImeAutaAdd , inputRegistracijaAdd;
    private APIInterface apiInterface;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_add);
        initComponents();
    }
    private void initComponents(){
        buttonVratiSeDataAdd = findViewById(R.id.buttonVratiSeAdd);
        buttonVratiSeDataAdd.setOnClickListener(this);

        inputImeAutaAdd = findViewById(R.id.inputImeAutaAdd);
        inputRegistracijaAdd = findViewById(R.id.inputRegistracijaAdd);

        buttonDodajAutoAdd = findViewById(R.id.buttonDodajAutoAdd);
        buttonDodajAutoAdd.setOnClickListener(this);

        preferences= getSharedPreferences("userLogin", MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonDodajAutoAdd) {
            addVehicle();
        } else if (view.getId() == R.id.buttonVratiSeAdd) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
    private void addVehicle(){
        String carName = inputImeAutaAdd.getText().toString().trim();
        String plateLicence = inputRegistracijaAdd.getText().toString().trim();
        //Validacija prilikom unosa
        if (TextUtils.isEmpty(carName)) {
            inputImeAutaAdd.setError("Neste uneli naziv vozila!");
            inputImeAutaAdd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(plateLicence)) {
            inputRegistracijaAdd.setError("Niste uneli registarsku oznaku!");
            inputRegistracijaAdd.requestFocus();
            return;
        }
        String name = preferences.getString("name", null);
        String lastName = preferences.getString("lastName", null);
        String email = preferences.getString("email", null);
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        String phone = preferences.getString("phone", null);
        int userId = preferences.getInt("user_id",0);

        User user = new User(userId,name,lastName,email,username,password,phone);
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);

        Vehicle vehicle = new Vehicle(carName,plateLicence,userList);

        Call<Vehicle> callVehicle = apiInterface.createVehicle(vehicle);
        callVehicle.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                Toast.makeText(VehicleAdd.this,"Uspešno ste dodali vozilo.", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
            }
        });
        startActivity(new Intent(this, MainActivity.class));
    }
}