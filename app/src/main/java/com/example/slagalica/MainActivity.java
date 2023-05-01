package com.example.slagalica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.slagalica.fragments.AsocijacijeFragment;
import com.example.slagalica.fragments.KoZnaZnaFragment;
import com.example.slagalica.fragments.PocetnaStranicaFragment;
import com.example.slagalica.fragments.SpojniceFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //com.example.slagalica.tools.FragmentTransition.to(PocetnaStranicaFragment.newInstance("FTN"), this, false, R.id.activityMainLayout);
    }
}