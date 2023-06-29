package com.example.slagalica;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slagalica.fragments.KoZnaZnaFragment;
import com.example.slagalica.fragments.PocetnaStranicaFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String emailKorisnika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("emailKorisnika")) {
            // Retrieve the parameter values using the get<Type>Extra() methods
            emailKorisnika = intent.getStringExtra("emailKorisnika");

            PocetnaStranicaFragment pocetnaStranica = PocetnaStranicaFragment.newInstance(emailKorisnika);
            getSupportFragmentManager().beginTransaction().add(R.id.activityMainLayout, pocetnaStranica).commit();

        } else {
            KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance("test");
            getSupportFragmentManager().beginTransaction().add(R.id.activityMainLayout, koZnaZnaFragment).commit();
        }


    }
}