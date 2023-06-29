package com.example.slagalica.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.slagalica.R;
import com.example.slagalica.activities.StartUpActivity;
import com.example.slagalica.model.Korisnik;

public class ProfilFragment extends Fragment {

    private static Korisnik logovaniKorisnik1;
    private TextView korisnickoIme;
    private TextView email;
    private TextView lozinka;

    private ImageView profilePicture;

    private Button odjava;

    private  Button izmeniPodatke;

    private boolean isPasswordVisible = false;



    public static ProfilFragment newInstance(Korisnik korisnik){
        Bundle args = new Bundle();
        args.putString("key","test");

        logovaniKorisnik1 = korisnik;

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
        profilePicture = view.findViewById(R.id.avatar);
        odjava = view.findViewById(R.id.logout);
        izmeniPodatke  = view.findViewById(R.id.izmeniPodatke);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfilData();
        setOnClickListener();
    }

    private void setProfilData(){

        email.setText(logovaniKorisnik1.getEmail());
        korisnickoIme.setText(logovaniKorisnik1.getKorisnickoIme());
        lozinka.setText(logovaniKorisnik1.getsifra());

        String base64Image = logovaniKorisnik1.getProfilePicture();
        if (!TextUtils.isEmpty(base64Image)) {
            // Decode the Base64 encoded string to a byte array
            byte[] imageData = Base64.decode(base64Image, Base64.DEFAULT);

            // Convert the byte array to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            // Wait for the ImageView to be measured
            profilePicture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Ensure this listener is only called once
                    profilePicture.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // Scale the bitmap to match the size of the ImageView
                    int desiredWidth = profilePicture.getWidth();
                    int desiredHeight = profilePicture.getHeight();
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);

                    // Set the scaled bitmap as the profile picture in the ImageView
                    profilePicture.setImageBitmap(scaledBitmap);
                }
            });
        } else {
            Log.d("ProfilePicture", "No profile picture available");
        }


    }

    private void setOnClickListener(){

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
                startActivity(new Intent(getActivity(), StartUpActivity.class));
            }
        });

        izmeniPodatke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Fragment secFragment = new IzmeniProfilFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.activityMainLayout, secFragment).commit();*/

                IzmeniProfilFragment izmeniProfilFragment = IzmeniProfilFragment.newInstance(logovaniKorisnik1);
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,izmeniProfilFragment).commit();

/*
                Fragment izmeniProfilFragment = new IzmeniProfilFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activityMainLayout, izmeniProfilFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });


    }
}
