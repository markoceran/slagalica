package com.example.slagalica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.slagalica.activities.Login;
import com.example.slagalica.fragments.AsocijacijeFragment;
import com.example.slagalica.fragments.KoZnaZnaFragment;
import com.example.slagalica.fragments.PocetnaStranicaFragment;
import com.example.slagalica.fragments.SpojniceFragment;
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