package com.example.slagalica.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.example.slagalica.activities.Login;
import com.example.slagalica.tools.FragmentTransition;

public class PocetnaStranicaFragment extends Fragment {

    public static PocetnaStranicaFragment newInstance(String someParam){
        Bundle args = new Bundle();
        args.putString("key","test");

        PocetnaStranicaFragment fragment = new PocetnaStranicaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocetna_stranica, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        TextView btnZapocniIgru = view.findViewById(R.id.zapocniIgruButton);
        btnZapocniIgru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance("test");
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,koZnaZnaFragment).commit();
            }
        });

        ImageView btnProfil = view.findViewById(R.id.profil);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilFragment profilFragment = ProfilFragment.newInstance("test");
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,profilFragment).commit();
            }
        });

        return view;



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Toast.makeText(getActivity(), "onDestroyView()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Toast.makeText(getActivity(), "onDeatach()", Toast.LENGTH_SHORT).show();
    }
}
