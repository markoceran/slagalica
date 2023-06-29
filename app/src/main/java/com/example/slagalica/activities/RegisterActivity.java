package com.example.slagalica.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    CollectionReference collectionRef =  MainActivity.db.collection("korisnici");
    //DocumentReference documentRef = collectionRef.document("k2FdSWmo7q6VJR6xz48g");
    private Map<String, Object> data = new HashMap<>();

    TextView btn;
    private EditText inputUsername, inputPassword, inputEmail, inputConformPassword;
    Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.alreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(RegisterActivity.this);

        getData();

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });


        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,Login.class));
            }
        });

    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String conformPassword = inputConformPassword.getText().toString().trim();

        if (username.isEmpty() || username.length()<7)
        {
            showError(inputUsername, "Vaše korisničko ime nije validno!");
        }
        else if (isEmailExists(email))
        {
            showError(inputEmail, "Korisnik sa unetim email-om već postoji");
        }
        else if (isUsernameExists(username))
        {
            showError(inputUsername, "Korisnik sa unetim korisničkim imenom već postoji");
        }
        else if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail, "Email nije validan!");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword, "Šifra mora da sadrži 7 karaktera");
        }
        else if (conformPassword.isEmpty() || !conformPassword.equals(password))
        {
            showError(inputConformPassword, "Šifre se ne poklapaju!");
        }
        else
        {
            Map<String, Object> dataUser = new HashMap<>();
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Molimo sačekajte dok proverimo kredencijale");

            dataUser.put("korisnickoIme", username);
            dataUser.put("email", email);
            dataUser.put("sifra", password);
            dataUser.put("profilePicture", "");
            dataUser.put("tokeni", 5);
            dataUser.put("zvezde", 0);

            dataUser.put("bodoviKoZnaZna", 0);
            dataUser.put("bodoviKorakPoKorak", 0);
            dataUser.put("bodoviAsocijacije", 0);
            dataUser.put("bodoviMojBroj", 0);
            dataUser.put("bodoviSkocko", 0);
            dataUser.put("bodoviSpojnice", 0);

            dataUser.put("pobedjenePartije", 0);
            dataUser.put("izgubljenePartije", 0);


            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            // Set the data in Firestore
            collectionRef.add(dataUser)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterActivity.this, "Uspešna registracija", Toast.LENGTH_SHORT).show();

                            mLoadingBar.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, StartUpActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Neuspešna registracija", Toast.LENGTH_SHORT).show();
                        }
                    });

            /*mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "Uspešna registracija", Toast.LENGTH_SHORT).show();

                        mLoadingBar.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, StartUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }

    private boolean isEmailExists(String email) {

        for (Object value : data.values()) {
            if (value instanceof Map) {
                Map<String, Object> user = (Map<String, Object>) value;
                if (user.get("email").equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUsernameExists(String username) {

        for (Object value : data.values()) {
            if (value instanceof Map) {
                Map<String, Object> user = (Map<String, Object>) value;
                if (user.get("korisnickoIme").equals(username)) {
                    return true;
                }
            }
        }
        return false;
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
/*
    private void setButtonClickListener() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.put("korisnickoIme", inputUsername);
                inputUsername.getText().toString();
                data.put("email", inputEmail);
                inputEmail.getText().toString();
                data.put("sifra", inputEmail);
                inputPassword.getText().toString();
            }
        });
    }
*/
}