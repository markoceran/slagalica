package com.example.slagalica.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private Map<String, Object> data = new HashMap<>();

    private boolean isLogged = false;
    TextView btn;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn=findViewById(R.id.textViewSignUp);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Login.this);

        getData();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
            }
        });
/*
        TextView btnLogin=findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
*/

    }
    private void checkCredentials() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail, "Email nije validan!");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword, "Šifra mora da sadrži 7 karaktera");
        }
        else
        {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Molimo sačekajte da proverimo kredencijale");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            for (Object value : data.values()) {
                if (value instanceof Map) {
                    Map<String, Object> user = (Map<String, Object>) value;
                    if (user.get("email").equals(email) && user.get("sifra").equals(password)) {
                        isLogged = true;
                        Toast.makeText(Login.this, "Hello " + user.get("korisnickoIme"), Toast.LENGTH_LONG).show();

                        break;
                    }
                }
            }
            mLoadingBar.dismiss();

            if(isLogged){

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("emailKorisnika" , email);
                startActivity(intent);

            }else {
                Toast.makeText(Login.this, "Neuspešna prijava", Toast.LENGTH_SHORT).show();
            }




            /*mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        mLoadingBar.dismiss();
                        Intent intent = new Intent(Login.this, StartUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });*/
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }

    public void getData() {
        MainActivity.db.collection("korisnici")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Get the document data and convert it to a map
                        Map<String, Object> documentData = document.getData();

                        // Extract the 'korisnickoIme' field as the key
                        String korisnickoIme = (String) documentData.get("korisnickoIme");

                        // Add the document data to the 'data' map using 'korisnickoIme' as the key
                        data.put(korisnickoIme, documentData);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
                });
    }


}