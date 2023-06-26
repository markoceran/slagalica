package com.example.slagalica.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference collectionRef = firestore.collection("korisnici");
    //DocumentReference documentRef = collectionRef.document("k2FdSWmo7q6VJR6xz48g");
    public Map<String, Object> data = new HashMap<>();

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

        getData();
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conformPassword = inputConformPassword.getText().toString();

        if (username.isEmpty() || username.length()<7)
        {
            showError(inputUsername, "Vaše korisničko ime nije validno!");
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
            Map<String, Object> data = new HashMap<>();
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Molimo sačekajte dok proverimo kredencijale");
            data.put("korisnickoIme", username);
            data.put("email", email);
            data.put("sifra", password);

            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            // Set the data in Firestore
            collectionRef.add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Document added successfully
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error adding document
                        }
                    });

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }

    public void getData() {

        //Dobavljanje podataka

        MainActivity.db.collection("korisnici")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {

                                // Get a random document from the query snapshot
                                int randomIndex = (int) (Math.random() * querySnapshot.size());
                                DocumentSnapshot randomDocument = querySnapshot.getDocuments().get(randomIndex);

                                data = randomDocument.getData();

                                //setButtonClickListener();


                            } else {
                                Toast.makeText(getApplicationContext(), "Error load data", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Error getting documents", Toast.LENGTH_SHORT).show();
                        }

                    }
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