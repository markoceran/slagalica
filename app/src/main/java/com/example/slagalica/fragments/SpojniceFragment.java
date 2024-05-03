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

public class SpojniceFragment extends Fragment {

    private TextView tema;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;

    private static int brojBodova;
    public Map<String, Object> data = new HashMap<>();
    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;
    private Button bluePlayer;

    private String clickedButton = "";
    private int color;

    private Drawable originalColor;

    private List<Button> leftButtons = new ArrayList<>();
    private List<Button> rightButtons = new ArrayList<>();

    private List<List> allParovi = new ArrayList<>();



    public static SpojniceFragment newInstance(int someParam){

        brojBodova = someParam;

        Bundle args = new Bundle();
        args.putString("key","test");

        SpojniceFragment fragment = new SpojniceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spojnice, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        tema = view.findViewById(R.id.temaTextView);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);
        button10 = view.findViewById(R.id.button10);
        bluePlayer = view.findViewById(R.id.blue_player);
        bluePlayer.setText(String.valueOf(brojBodova));

        leftButtons.add(button1);
        leftButtons.add(button3);
        leftButtons.add(button5);
        leftButtons.add(button7);
        leftButtons.add(button9);
        rightButtons.add(button2);
        rightButtons.add(button4);
        rightButtons.add(button6);
        rightButtons.add(button8);
        rightButtons.add(button10);

        originalColor = button1.getBackground();
        color = ContextCompat.getColor(getContext(), R.color.correct);



        timerButton = view.findViewById(R.id.stopwatch);
        handler = new Handler();

        // Set the duration of the timer
        long timerDurationMillis = 31000;

        countDownTimer = new CountDownTimer(timerDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                long secondsRemaining = millisUntilFinished / 1000;
                timerButton.setText("" + secondsRemaining + "");

            }

            @Override
            public void onFinish() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform the desired action after the delay
                        // Navigate to another fragment
                        AsocijacijeFragment asocijacijeFragment = AsocijacijeFragment.newInstance(brojBodova);
                        getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout, asocijacijeFragment).commit();
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

        MainActivity.db.collection("spojnice")
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

                                allParovi.add((List) data.get("PAR1"));
                                allParovi.add((List) data.get("PAR2"));
                                allParovi.add((List) data.get("PAR3"));
                                allParovi.add((List) data.get("PAR4"));
                                allParovi.add((List) data.get("PAR5"));

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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "button1";

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "button3";

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "button5";

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "button7";

            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton = "button9";

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(clickedButton != ""){

                    for(Button b:leftButtons){

                        String buttonIdString = getResources().getResourceEntryName(b.getId());
                        if(clickedButton.equals(buttonIdString)){

                            for(List p : allParovi){

                                if((p.contains(b.getText().toString())) && (p.contains(button2.getText().toString()))){

                                    b.setBackgroundColor(color);
                                    button2.setBackgroundColor(color);

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    button2.setOnClickListener(null);
                                    button2.setEnabled(false);
                                    button2.setFocusable(false);

                                    brojBodova = brojBodova + 2;
                                    bluePlayer.setText(String.valueOf(brojBodova));

                                    break;

                                }
                                else{

                                    b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    clickedButton = "";

                                }
                            }
                        }
                    }
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickedButton != ""){

                    for(Button b:leftButtons){

                        String buttonIdString = getResources().getResourceEntryName(b.getId());
                        if(clickedButton.equals(buttonIdString)){

                            for(List p : allParovi){

                                if((p.contains(b.getText().toString())) && (p.contains(button4.getText().toString()))){

                                    b.setBackgroundColor(color);
                                    button4.setBackgroundColor(color);

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    button4.setOnClickListener(null);
                                    button4.setEnabled(false);
                                    button4.setFocusable(false);

                                    brojBodova = brojBodova + 2;
                                    bluePlayer.setText(String.valueOf(brojBodova));

                                    break;

                                }
                                else{

                                    b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    clickedButton = "";

                                }
                            }
                        }
                    }
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickedButton != ""){

                    for(Button b:leftButtons){

                        String buttonIdString = getResources().getResourceEntryName(b.getId());
                        if(clickedButton.equals(buttonIdString)){

                            for(List p : allParovi){

                                if((p.contains(b.getText().toString())) && (p.contains(button6.getText().toString()))){

                                    b.setBackgroundColor(color);
                                    button6.setBackgroundColor(color);

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    button6.setOnClickListener(null);
                                    button6.setEnabled(false);
                                    button6.setFocusable(false);

                                    brojBodova = brojBodova + 2;
                                    bluePlayer.setText(String.valueOf(brojBodova));

                                    break;

                                }
                                else{

                                    b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    clickedButton = "";

                                }
                            }
                        }
                    }
                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickedButton != ""){

                    for(Button b:leftButtons){

                        String buttonIdString = getResources().getResourceEntryName(b.getId());
                        if(clickedButton.equals(buttonIdString)){

                            for(List p : allParovi){

                                if((p.contains(b.getText().toString())) && (p.contains(button8.getText().toString()))){

                                    b.setBackgroundColor(color);
                                    button8.setBackgroundColor(color);

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    button8.setOnClickListener(null);
                                    button8.setEnabled(false);
                                    button8.setFocusable(false);

                                    brojBodova = brojBodova + 2;
                                    bluePlayer.setText(String.valueOf(brojBodova));

                                    break;

                                }
                                else{

                                    b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    clickedButton = "";

                                }
                            }
                        }
                    }
                }
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickedButton != ""){

                    for(Button b:leftButtons){

                        String buttonIdString = getResources().getResourceEntryName(b.getId());
                        if(clickedButton.equals(buttonIdString)){

                            for(List p : allParovi){

                                if((p.contains(b.getText().toString())) && (p.contains(button10.getText().toString()))){

                                    b.setBackgroundColor(color);
                                    button10.setBackgroundColor(color);

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    button10.setOnClickListener(null);
                                    button10.setEnabled(false);
                                    button10.setFocusable(false);

                                    brojBodova = brojBodova + 2;
                                    bluePlayer.setText(String.valueOf(brojBodova));

                                    break;

                                }
                                else{

                                    b.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));

                                    b.setOnClickListener(null);
                                    b.setEnabled(false);
                                    b.setFocusable(false);

                                    clickedButton = "";

                                }
                            }
                        }
                    }
                }
            }
        });



    }

    private void showData(){

        tema.setText((String)data.get("TEMA"));

        //Collections.shuffle(allParovi);
        Collections.shuffle(leftButtons);
        Collections.shuffle(rightButtons);

        for (int i = 0; i < allParovi.size(); i++) {

            List par = allParovi.get(i);
            Button leftButton = leftButtons.get(i);
            Button rightButton = rightButtons.get(i);

            leftButton.setText((String)par.get(0));
            rightButton.setText((String)par.get(1));
        }

    }
}
