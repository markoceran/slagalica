package com.example.slagalica.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;

public class StartUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        Button btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUpActivity.this, Login.class));
            }
        });

        Button btnreg = findViewById(R.id.btnreg);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUpActivity.this, RegisterActivity.class));
            }
        });

        Button btnguest = findViewById(R.id.btnguest);
        btnguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an AlertDialog.Builder instance
                AlertDialog.Builder builder = new AlertDialog.Builder(StartUpActivity.this);

                // Set the dialog title and message
                builder.setTitle("IGRA")
                        .setMessage("Započni igru");


                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                    }
                });


                builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}