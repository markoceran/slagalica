package com.example.slagalica.fragments;

import static com.example.slagalica.MainActivity.db;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.example.slagalica.activities.StartUpActivity;
import com.example.slagalica.model.Korisnik;
import com.example.slagalica.tools.SocketHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.socket.client.Socket;

public class PocetnaStranicaFragment extends Fragment {

    private Map<String, Object> data = new HashMap<>();
    private Korisnik logovanKorisnik = new Korisnik();

    private static String korisnikEmail;

    private TextView brojTokena;

    public static Socket socket;

    private TextView brojZvezda;

    private Button btnZapocniIgru;

    private ImageView btnProfil;


    public static PocetnaStranicaFragment newInstance(String someParam) {

        korisnikEmail = someParam;
        Bundle args = new Bundle();
        args.putString("key", "test");

        PocetnaStranicaFragment fragment = new PocetnaStranicaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocetna_stranica, container, false);


        btnZapocniIgru = view.findViewById(R.id.zapocniIgruButton);
        brojTokena = view.findViewById(R.id.brojTokenaText);
        brojZvezda = view.findViewById(R.id.brojZvezdaText);
        btnProfil = view.findViewById(R.id.profil);


        btnZapocniIgru = view.findViewById(R.id.zapocniIgruButton);

        SocketHandler.setSocket();

        socket = SocketHandler.getSocket();
        try {
            socket.connect();
        } catch (Exception e){
            System.out.println("ERROR " + e);
        }

        Map<String, String> userData = new HashMap<>();

        socket.on("zapocniIgru", args -> {
            if (args.length > 0 && args[0] instanceof JSONObject) {
                JSONObject data = (JSONObject) args[0];

                for (Iterator<String> it = data.keys(); it.hasNext(); ) {
                    String socket = it.next();
                    try {
                        if (!data.get(socket).toString().equals(logovanKorisnik.getKorisnickoIme()))
                            userData.put("protivnik", data.get(socket).toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                userData.put("ja", logovanKorisnik.getKorisnickoIme());

                KoZnaZnaFragment koZnaZnaFragment = KoZnaZnaFragment.newInstance(userData);
                getParentFragmentManager().beginTransaction().replace(R.id.pocetnaStranicaLayout,koZnaZnaFragment).commit();
            }
        });

        btnZapocniIgru.setOnClickListener(v -> {
            btnZapocniIgru.setEnabled(false);

            if(logovanKorisnik.getTokeni() > 0){

                socket.emit("pridruziSeIgri", logovanKorisnik.getKorisnickoIme());

            }else{
                Toast.makeText(getActivity(), "Nemate dovoljno tokena!", Toast.LENGTH_SHORT).show();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilFragment profilFragment = ProfilFragment.newInstance(logovanKorisnik);
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout, profilFragment).commit();
            }
        });


        return view;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void findLoggedUser(String email) {

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
