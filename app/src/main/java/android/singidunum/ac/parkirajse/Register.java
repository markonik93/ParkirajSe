package android.singidunum.ac.parkirajse;

import android.content.Intent;
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



public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText inputIme, inputPrezime, inputEmail, inputUsername, inputPassword, inputKontakt;
    private TextView  LogInbtnReg;
    private Button buttonRegistrujSeRegistracija, buttonOdustaniRegistracija;
    private APIInterface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();
    }

    private void initComponents() {
        inputIme = findViewById(R.id.inputIme);
        inputPrezime = findViewById(R.id.inputPrezime);
        inputEmail = findViewById(R.id.inputEmail);
        inputUsername = findViewById(R.id.inputUsernameLogIn);
        inputPassword = findViewById(R.id.inputPasswordLogin);
        inputKontakt = findViewById(R.id.inputKontakt);
        LogInbtnReg = findViewById(R.id.LogInbtnReg);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        buttonRegistrujSeRegistracija = findViewById(R.id.buttonRegistrujSeRegistracija);
        buttonOdustaniRegistracija = findViewById(R.id.buttonOdustaniRegistracija);
        progressBar = findViewById(R.id.progressBarRegistracija);

        buttonRegistrujSeRegistracija.setOnClickListener(this);
        LogInbtnReg.setOnClickListener(this);
        buttonOdustaniRegistracija.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonRegistrujSeRegistracija) {
            registerUser();
            progressBar.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.LogInbtnReg) {
            startActivity(new Intent(this, Login.class));
        } else if (view.getId() == R.id.buttonOdustaniRegistracija) {
            finish();
        }

    }

    private void registerUser() {
         String ime = inputIme.getText().toString().trim();
         String prezime = inputPrezime.getText().toString().trim();
         String email = inputEmail.getText().toString().trim();
         String username = inputUsername.getText().toString().trim();
         String password = inputPassword.getText().toString().trim();
         String kontakt = inputKontakt.getText().toString().trim();

         //Validacija prilikom unosa
        if (TextUtils.isEmpty(ime)) {
            inputIme.setError("Niste uneli ime!");
            inputIme.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(prezime)) {
            inputPrezime.setError("Niste uneli prezime!");
            inputPrezime.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("E-mail je obavezan!");
            inputEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            inputUsername.setError("Username je obavezan!");
            inputUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Password je obavezan!");
            inputPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            inputPassword.setError("Password mora imati 6 ili vise karaktera!");
            inputPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(kontakt)) {
            inputKontakt.setError("Niste uneli kontakt!");
            inputKontakt.requestFocus();
            return;
        }
       //Upisujemo usera u bazu (proverava da li postoji i ako ne postoji upisuje, ako vec postoji vraca odgovarajucu poruku)
        User user = new User(ime,prezime,email,username,password,kontakt);
        Call<User> call1 = apiInterface.createUser(user);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if(user!=null){
                    Toast.makeText(Register.this,"Uspešno ste registrovani. Molimo prijavite se", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Register.this, Login.class));
                }else{
                    Toast.makeText(Register.this,"Email ili username već postoji.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Register.this,"Nespešna registracija.", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}
