package com.example.slagalica;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.slagalica.fragments.KoZnaZnaFragment;
import com.example.slagalica.fragments.PocetnaStranicaFragment;
import com.example.slagalica.fragments.ProfilFragment;
import com.example.slagalica.tools.SocketHandler;
import com.google.firebase.firestore.FirebaseFirestore;

import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {

    public static Socket socket;
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
            KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance("");
            getSupportFragmentManager().beginTransaction().add(R.id.activityMainLayout, koZnaZnaFragment).commit();
        }

        //Socket
        SocketHandler.setSocket();

        socket = SocketHandler.getSocket();
        socket.connect();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.activityMainLayout);

        if (currentFragment instanceof ProfilFragment) {
            // Navigate back to PocetnaStranicaFragment
            PocetnaStranicaFragment pocetnaStranicaFragment = PocetnaStranicaFragment.newInstance(emailKorisnika);
            fragmentManager.beginTransaction()
                    .replace(R.id.activityMainLayout, pocetnaStranicaFragment)
                    .commit();
        } else if (currentFragment instanceof PocetnaStranicaFragment) {
            // Handle back press for PocetnaStranicaFragment
            // Back press is blocked

        }else {
            super.onBackPressed(); // Perform the default back button behavior
        }

    }
}