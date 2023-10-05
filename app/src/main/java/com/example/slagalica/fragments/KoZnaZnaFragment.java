package com.example.slagalica.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class KoZnaZnaFragment extends Fragment {

    public Map<String, Object> data = new HashMap<>();

    public static Map<String, String> userData = new HashMap<>();

    private List<Button> odgovoriButtons = new ArrayList<>();
    private List<String> listaOdgovora = new ArrayList<>();

    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;
    private Button bluePlayer;
    private TextView redniBrojPitanja;
    private TextView pitanje;
    private Button odgovor1;
    private Button odgovor2;
    private Button odgovor3;
    private Button odgovor4;
    private int brojacPitanja = 1;

    private int brojBodova = 0;

    private int color;

    private Drawable originalColor;


    public static KoZnaZnaFragment newInstance(Map<String, String> someParam){

        userData = someParam;

        KoZnaZnaFragment fragment = new KoZnaZnaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ko_zna_zna, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        redniBrojPitanja = view.findViewById(R.id.redniBrojPitanja);
        pitanje = view.findViewById(R.id.pitanje);
        odgovor1 = view.findViewById(R.id.odgovor1Button);
        odgovor2 = view.findViewById(R.id.odgovor2Button);
        odgovor3 = view.findViewById(R.id.odgovor3Button);
        odgovor4 = view.findViewById(R.id.odgovor4Button);
        bluePlayer = view.findViewById(R.id.blue_player);

        odgovoriButtons.add(odgovor1);
        odgovoriButtons.add(odgovor2);
        odgovoriButtons.add(odgovor3);
        odgovoriButtons.add(odgovor4);


        originalColor = odgovor1.getBackground();
        color = ContextCompat.getColor(getContext(), R.color.correct);


        timerButton = view.findViewById(R.id.stopwatch);
        handler = new Handler();

        // Set the duration of the timer
        long timerDurationMillis = 26000;

        countDownTimer = new CountDownTimer(timerDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                long secondsRemaining = millisUntilFinished / 1000;
                timerButton.setText("" + secondsRemaining + "");

                // Check if 5 seconds have elapsed
                //Posle 5 sekundi promeni pitanje
                long elapsedSeconds = (timerDurationMillis - millisUntilFinished) / 1000;
                if (elapsedSeconds == 5 || elapsedSeconds == 10 || elapsedSeconds == 15 || elapsedSeconds == 20) {

                    for(Button b:odgovoriButtons){
                        b.setBackground(originalColor);
                    }
                    brojacPitanja = brojacPitanja + 1;
                    redniBrojPitanja.setText("" + brojacPitanja + "");
                    getData();
                }
            }

            @Override
            public void onFinish() {

                //openAllFields();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform the desired action after the delay
                        // Navigate to another fragment
                        SpojniceFragment spojniceFragment = SpojniceFragment.newInstance(brojBodova);
                        getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout, spojniceFragment).commit();
                    }
                }, 3); // 3 seconds delay
            }
        };

        // Start the timer
        countDownTimer.start();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();

    }

    public void getData() {

        //Dobavljanje podataka

        MainActivity.db.collection("ko-zna-zna")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {

                                // Get a random document from the query snapshot
                                int randomIndex = (int) (Math.random() * querySnapshot.size());
                                DocumentSnapshot randomDocument = querySnapshot.getDocuments().get(randomIndex);

                                data = randomDocument.getData();
                                showData();
                                setButtonClickListener();

                            } else {
                                Toast.makeText(getActivity(), "Error load data", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getActivity(), "Error getting documents", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void setButtonClickListener(){

        odgovor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(odgovor1.getText().toString().equals(data.get("TACNO"))){

                    odgovor1.setBackgroundColor(color);

                    brojBodova = brojBodova + 10;
                    bluePlayer.setText(String.valueOf(brojBodova));

                }else{
                    odgovor1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    for(Button b:odgovoriButtons){
                        if(b.getText().toString().equals(data.get("TACNO"))){

                            b.setBackgroundColor(color);

                        }
                    }
                    brojBodova = brojBodova - 5;
                    bluePlayer.setText(String.valueOf(brojBodova));
                }
            }
        });

        odgovor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(odgovor2.getText().toString().equals(data.get("TACNO"))){

                    odgovor2.setBackgroundColor(color);

                    brojBodova = brojBodova + 10;
                    bluePlayer.setText(String.valueOf(brojBodova));

                }else{
                    odgovor2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    for(Button b:odgovoriButtons){
                        if(b.getText().toString().equals(data.get("TACNO"))){

                            b.setBackgroundColor(color);

                        }
                    }
                    brojBodova = brojBodova - 5;
                    bluePlayer.setText(String.valueOf(brojBodova));
                }
            }
        });

        odgovor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(odgovor3.getText().toString().equals(data.get("TACNO"))){

                    odgovor3.setBackgroundColor(color);
                    brojBodova = brojBodova + 10;
                    bluePlayer.setText(String.valueOf(brojBodova));

                }else{
                    odgovor3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    for(Button b:odgovoriButtons){
                        if(b.getText().toString().equals(data.get("TACNO"))){

                            b.setBackgroundColor(color);
                        }
                    }
                    brojBodova = brojBodova - 5;
                    bluePlayer.setText(String.valueOf(brojBodova));
                }
            }
        });

        odgovor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(odgovor4.getText().toString().equals(data.get("TACNO"))){

                    odgovor4.setBackgroundColor(color);
                    brojBodova = brojBodova + 10;
                    bluePlayer.setText(String.valueOf(brojBodova));

                }else{
                    odgovor4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    for(Button b:odgovoriButtons){
                        if(b.getText().toString().equals(data.get("TACNO"))){

                            b.setBackgroundColor(color);
                        }
                    }
                    brojBodova = brojBodova - 5;
                    bluePlayer.setText(String.valueOf(brojBodova));
                }
            }
        });


    }

    private void showData(){

        redniBrojPitanja.setText("" + brojacPitanja + "");
        String value = (String) data.get("PITANJE");
        pitanje.setText(value);

        listaOdgovora.clear();
        listaOdgovora.add((String) data.get("ODGOVOR1"));
        listaOdgovora.add((String) data.get("ODGOVOR2"));
        listaOdgovora.add((String) data.get("ODGOVOR3"));
        listaOdgovora.add((String) data.get("TACNO"));

        // Shuffle the buttonList
        Collections.shuffle(odgovoriButtons);
        Collections.shuffle(listaOdgovora);

        for (int i = 0; i < odgovoriButtons.size(); i++) {

            Button button = odgovoriButtons.get(i);
            String odgovor = listaOdgovora.get(i);
            button.setText(odgovor);
        }

    }
}
