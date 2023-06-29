package com.example.slagalica.fragments;

import android.os.Bundle;
import android.util.Log;
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

import io.socket.emitter.Emitter;

public class PocetnaStranicaFragment extends Fragment {

    private Map<String, Object> data = new HashMap<>();
    private Korisnik logovanKorisnik = new Korisnik();

    private static String korisnikEmail;

    private TextView brojTokena;

    private TextView brojZvezda;


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


        btnZapocniIgru.setOnClickListener(v -> {
            MainActivity.socket.emit("zapocni igru");
        });

        /*btnZapocniIgru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance("test");
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,koZnaZnaFragment).commit();
            }
        });*/


        ImageView btnProfil = view.findViewById(R.id.profil);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilFragment profilFragment = ProfilFragment.newInstance(logovanKorisnik);
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,profilFragment).commit();
            }
        });

        brojTokena = view.findViewById(R.id.brojTokenaText);
        brojZvezda = view.findViewById(R.id.brojZvezdaText);

        return view;



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.socket.on("zapocni igru", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // Handle the 'zapocni igru' event from the server
                // Note: This will be executed on a background thread, so if you want to update UI components, you should use runOnUiThread or similar methods
                if (args.length > 0 && args[0] != null) {
                    Log.i("SOCKET", args[0].toString());

                    // Perform UI operations in the main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance(korisnikEmail);
                            getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout, koZnaZnaFragment).commit();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();

    }

    private void setData() {
        brojTokena.setText(String.valueOf(logovanKorisnik.getTokeni()));
        brojZvezda.setText(String.valueOf(logovanKorisnik.getZvezde()));
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
                    setData();

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
                    logovanKorisnik.setSifra((String) user.get("sifra"));
                    logovanKorisnik.setTokeni((Long) user.get("tokeni"));
                    logovanKorisnik.setZvezde((Long) user.get("zvezde"));
                    logovanKorisnik.setProfilePicture((String) user.get("profilePicture"));

                    logovanKorisnik.setBodoviKoZnaZna((Long) user.get("bodoviKoZnaZna"));
                    logovanKorisnik.setBodoviKorakPoKorak((Long) user.get("bodoviKorakPoKorak"));
                    logovanKorisnik.setBodoviAsocijacije((Long) user.get("bodoviAsocijacije"));
                    logovanKorisnik.setBodoviMojBroj((Long) user.get("bodoviMojBroj"));
                    logovanKorisnik.setBodoviSkocko((Long) user.get("bodoviSkocko"));
                    logovanKorisnik.setBodoviSpojnice((Long) user.get("bodoviSpojnice"));

                    logovanKorisnik.setPobedjenePartije((Long) user.get("pobedjenePartije"));
                    logovanKorisnik.setIzgubljenePartije((Long) user.get("izgubljenePartije"));

                    break;
                }
            }
        }
    }

}
