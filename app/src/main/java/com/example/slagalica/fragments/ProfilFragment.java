package com.example.slagalica.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slagalica.R;
import com.example.slagalica.activities.StartUpActivity;
import com.example.slagalica.model.Korisnik;

public class ProfilFragment extends Fragment {

    private static Korisnik logovaniKorisnik;
    private TextView korisnickoIme;
    private TextView email;
    private TextView lozinka;

    private Button odjava;

    private boolean isPasswordVisible = false;


    public static ProfilFragment newInstance(Korisnik korisnik) {
        Bundle args = new Bundle();
        args.putString("key", "test");

        logovaniKorisnik = korisnik;

        ProfilFragment fragment = new ProfilFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        korisnickoIme = view.findViewById(R.id.korisnickoIme);
        email = view.findViewById(R.id.email);
        lozinka = view.findViewById(R.id.lozinka);
        odjava = view.findViewById(R.id.logout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfilData();
        setOnClickListener();

    }


    private void setProfilData() {

        email.setText(logovaniKorisnik.getEmail());
        korisnickoIme.setText(logovaniKorisnik.getKorisnickoIme());
        lozinka.setText(logovaniKorisnik.getSifra());

    }

    private void setOnClickListener() {

        lozinka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPasswordVisible) {
                    lozinka.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordVisible = false;
                } else {
                    lozinka.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordVisible = true;
                }

            }
        });

        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an AlertDialog.Builder instance
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Set the dialog title and message
                builder.setTitle("ODJAVA")
                        .setMessage("Da li ste sigurni da želite da se odjavite sa profila?");


                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), StartUpActivity.class));
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


