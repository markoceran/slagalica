package com.example.slagalica.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.example.slagalica.model.Korisnik;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class PocetnaStranicaFragment extends Fragment {

    private Map<String, Object> data = new HashMap<>();
    private Korisnik logovanKorisnik = new Korisnik();

    private static String korisnikEmail;

    public static PocetnaStranicaFragment newInstance(String someParam){

        korisnikEmail = someParam;
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
                ProfilFragment profilFragment = ProfilFragment.newInstance(logovanKorisnik);
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,profilFragment).commit();
            }
        });

        return view;



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
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

    public void getData() {
        MainActivity.db.collection("korisnici")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Get the document data and convert it to a map
                        Map<String, Object> documentData = document.getData();

                        // Extract the 'korisnickoIme' field as the key
                        String korisnickoIme = (String) documentData.get("korisnickoIme");

                        // Add the document data to the 'data' map using 'korisnickoIme' as the key
                        data.put(korisnickoIme, documentData);
                    }

                    findLoggedUser(korisnikEmail);
                    if (logovanKorisnik != null) {
                        Toast.makeText(getActivity(), "Hello " + logovanKorisnik.getKorisnickoIme(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                });
    }

    private void findLoggedUser(String email){

        for (Object value : data.values()) {
            if (value instanceof Map) {
                Map<String, Object> user = (Map<String, Object>) value;
                if (user.get("email").equals(email)) {

                    logovanKorisnik.setKorisnickoIme((String) user.get("korisnickoIme"));
                    logovanKorisnik.setEmail((String) user.get("email"));
                    logovanKorisnik.setsifra((String) user.get("sifra"));

                    break;
                }
            }
        }
    }
}
