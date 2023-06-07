package com.example.slagalica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slagalica.fragments.KoZnaZnaFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*if korisnik ulogovan
        PocetnaStranicaFragment pocetnaStranica = PocetnaStranicaFragment.newInstance("test");
        getSupportFragmentManager().beginTransaction().add(R.id.activityMainLayout,pocetnaStranica).commit();*/
        //else
        KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance("test");
        getSupportFragmentManager().beginTransaction().add(R.id.activityMainLayout,koZnaZnaFragment).commit();

    }
}