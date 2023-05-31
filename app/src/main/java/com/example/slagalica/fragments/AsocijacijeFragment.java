package com.example.slagalica.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsocijacijeFragment extends Fragment {

    public Map<String, Object> data = new HashMap<>();
    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;

    private Button a1;
    private Button a2;
    private Button a3;
    private Button a4;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button c1;
    private Button c2;
    private Button c3;
    private Button c4;
    private Button d1;
    private Button d2;
    private Button d3;
    private Button d4;

    private TextInputEditText resenjeA;
    private TextInputEditText resenjeB;
    private TextInputEditText resenjeC;
    private TextInputEditText resenjeD;
    private TextInputEditText konacno;

    private int poeniKolonaA = 6;
    private int poeniKolonaB = 6;
    private int poeniKolonaC = 6;
    private int poeniKolonaD = 6;

    private int poeniKonacno = 0;
    private int poeniUkupno = 0;

    private Button bluePlayer;

    public static AsocijacijeFragment newInstance(String someParam) {
        Bundle args = new Bundle();
        args.putString("key", "test");

        AsocijacijeFragment fragment = new AsocijacijeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asocijacije, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */


        a1 = view.findViewById(R.id.a1);
        a2 = view.findViewById(R.id.a2);
        a3 = view.findViewById(R.id.a3);
        a4 = view.findViewById(R.id.a4);
        b1 = view.findViewById(R.id.b1);
        b2 = view.findViewById(R.id.b2);
        b3 = view.findViewById(R.id.b3);
        b4 = view.findViewById(R.id.b4);
        c1 = view.findViewById(R.id.c1);
        c2 = view.findViewById(R.id.c2);
        c3 = view.findViewById(R.id.c3);
        c4 = view.findViewById(R.id.c4);
        d1 = view.findViewById(R.id.d1);
        d2 = view.findViewById(R.id.d2);
        d3 = view.findViewById(R.id.d3);
        d4 = view.findViewById(R.id.d4);

        resenjeA = view.findViewById(R.id.resenjeA);
        resenjeB = view.findViewById(R.id.resenjeB);
        resenjeC = view.findViewById(R.id.resenjeC);
        resenjeD = view.findViewById(R.id.resenjeD);
        konacno = view.findViewById(R.id.konacno);

        bluePlayer = view.findViewById(R.id.blue_player);

        timerButton = view.findViewById(R.id.stopwatch);
        handler = new Handler();

        // Set the duration of the timer
        long timerDurationMillis = 120000;

        countDownTimer = new CountDownTimer(timerDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                long secondsRemaining = millisUntilFinished / 1000;
                timerButton.setText("" + secondsRemaining + "");
            }

            @Override
            public void onFinish() {

                // Perform the action when the timer is over

                openAllFields();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform the desired action after the delay
                        // Navigate to another fragment
                        SkockoFragment skockoFragment = SkockoFragment.newInstance("test");
                        getParentFragmentManager().beginTransaction().replace(R.id.asocijacije_layout, skockoFragment).commit();
                    }
                }, 7000); // 7 seconds delay
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

        MainActivity.db.collection("asocijacija")
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

    private void setButtonClickListener() {

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("A1");
                a1.setText(value);
                a1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                a1.setOnClickListener(null);
                poeniKolonaA = poeniKolonaA - 1;

            }
        });


        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("A2");
                a2.setText(value);
                a2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                a2.setOnClickListener(null);
                poeniKolonaA = poeniKolonaA - 1;

            }
        });


        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("A3");
                a3.setText(value);
                a3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                a3.setOnClickListener(null);
                poeniKolonaA = poeniKolonaA - 1;

            }
        });


        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("A4");
                a4.setText(value);
                a4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                a4.setOnClickListener(null);
                poeniKolonaA = poeniKolonaA - 1;

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("B1");
                b1.setText(value);
                b1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                b1.setOnClickListener(null);
                poeniKolonaB = poeniKolonaB - 1;

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("B2");
                b2.setText(value);
                b2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                b2.setOnClickListener(null);
                poeniKolonaB = poeniKolonaB - 1;

            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("B3");
                b3.setText(value);
                b3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                b3.setOnClickListener(null);
                poeniKolonaB = poeniKolonaB - 1;

            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("B4");
                b4.setText(value);
                b4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                b4.setOnClickListener(null);
                poeniKolonaB = poeniKolonaB - 1;

            }
        });


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("C1");
                c1.setText(value);
                c1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                c1.setOnClickListener(null);
                poeniKolonaC = poeniKolonaC - 1;

            }
        });


        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("C2");
                c2.setText(value);
                c2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                c2.setOnClickListener(null);
                poeniKolonaC = poeniKolonaC - 1;

            }
        });


        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("C3");
                c3.setText(value);
                c3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                c3.setOnClickListener(null);
                poeniKolonaC = poeniKolonaC - 1;

            }
        });


        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("C4");
                c4.setText(value);
                c4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                c4.setOnClickListener(null);
                poeniKolonaC = poeniKolonaC - 1;

            }
        });


        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("D1");
                d1.setText(value);
                d1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                d1.setOnClickListener(null);
                poeniKolonaD = poeniKolonaD - 1;

            }
        });


        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("D2");
                d2.setText(value);
                d2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                d2.setOnClickListener(null);
                poeniKolonaD = poeniKolonaD - 1;

            }
        });


        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("D3");
                d3.setText(value);
                d3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                d3.setOnClickListener(null);
                poeniKolonaD = poeniKolonaD - 1;

            }
        });


        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = (String) data.get("D4");
                d4.setText(value);
                d4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                d4.setOnClickListener(null);
                poeniKolonaD = poeniKolonaD - 1;

            }
        });


        resenjeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resenjeA.setText("");
            }
        });
        resenjeA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (resenjeA.getText().toString().equalsIgnoreCase((String) data.get("RESENJE A"))) {


                        //Poeni
                        //poeniKolonaA = poeniKolonaA + 2;
                        poeniUkupno = poeniUkupno + poeniKolonaA;
                        bluePlayer.setText(""+poeniUkupno+"");

                        resenjeA.setText((String) data.get("RESENJE A"));
                        resenjeA.setBackgroundColor(Color.parseColor("#2A9DF4"));

                        a1.setText((String) data.get("A1"));
                        a1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        a1.setOnClickListener(null);

                        a2.setText((String) data.get("A2"));
                        a2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        a2.setOnClickListener(null);

                        a3.setText((String) data.get("A3"));
                        a3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        a3.setOnClickListener(null);

                        a4.setText((String) data.get("A4"));
                        a4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        a4.setOnClickListener(null);

                        resenjeA.setEnabled(false);
                        resenjeA.setFocusable(false);
                    }
                }
                return false;
            }
        });

        resenjeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resenjeB.setText("");
            }
        });
        resenjeB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (resenjeB.getText().toString().equalsIgnoreCase((String) data.get("RESENJE B"))) {

                        //Poeni
                        //poeniKolonaB = poeniKolonaB + 2;
                        poeniUkupno = poeniUkupno + poeniKolonaB;
                        bluePlayer.setText(""+poeniUkupno+"");


                        resenjeB.setText((String) data.get("RESENJE B"));
                        resenjeB.setBackgroundColor(Color.parseColor("#2A9DF4"));

                        b1.setText((String) data.get("B1"));
                        b1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        b1.setOnClickListener(null);

                        b2.setText((String) data.get("B2"));
                        b2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        b2.setOnClickListener(null);

                        b3.setText((String) data.get("B3"));
                        b3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        b3.setOnClickListener(null);

                        b4.setText((String) data.get("B4"));
                        b4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        b4.setOnClickListener(null);

                        resenjeB.setEnabled(false);
                        resenjeB.setFocusable(false);
                    }
                }
                return false;
            }
        });

        resenjeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resenjeC.setText("");
            }
        });
        resenjeC.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (resenjeC.getText().toString().equalsIgnoreCase((String) data.get("RESENJE C"))) {

                        //Poeni
                        //poeniKolonaC = poeniKolonaC + 2;
                        poeniUkupno = poeniUkupno + poeniKolonaC;
                        bluePlayer.setText(""+poeniUkupno+"");

                        resenjeC.setText((String) data.get("RESENJE C"));
                        resenjeC.setBackgroundColor(Color.parseColor("#2A9DF4"));

                        c1.setText((String) data.get("C1"));
                        c1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        c1.setOnClickListener(null);

                        c2.setText((String) data.get("C2"));
                        c2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        c2.setOnClickListener(null);

                        c3.setText((String) data.get("C3"));
                        c3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        c3.setOnClickListener(null);

                        c4.setText((String) data.get("C4"));
                        c4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        c4.setOnClickListener(null);

                        resenjeC.setEnabled(false);
                        resenjeC.setFocusable(false);
                    }

                }
                return false;
            }
        });

        resenjeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resenjeD.setText("");
            }
        });
        resenjeD.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (resenjeD.getText().toString().equalsIgnoreCase((String) data.get("RESENJE D"))) {

                        //Poeni
                        //poeniKolonaD = poeniKolonaD + 2;
                        poeniUkupno = poeniUkupno + poeniKolonaD;
                        bluePlayer.setText(""+poeniUkupno+"");

                        resenjeD.setText((String) data.get("RESENJE D"));
                        resenjeD.setBackgroundColor(Color.parseColor("#2A9DF4"));

                        d1.setText((String) data.get("D1"));
                        d1.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        d1.setOnClickListener(null);

                        d2.setText((String) data.get("D2"));
                        d2.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        d2.setOnClickListener(null);

                        d3.setText((String) data.get("D3"));
                        d3.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        d3.setOnClickListener(null);

                        d4.setText((String) data.get("D4"));
                        d4.setBackgroundColor(Color.parseColor("#2A9DF4"));
                        d4.setOnClickListener(null);

                        resenjeD.setEnabled(false);
                        resenjeD.setFocusable(false);
                    }

                }
                return false;
            }
        });

        konacno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konacno.setText("");
            }
        });
        konacno.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (konacno.getText().toString().equalsIgnoreCase((String) data.get("KONACNO"))) {

                        addPointsForKonacno();
                        openAllFields();

                    }
                }
                return false;
            }
        });


    }


    private void openAllFields() {
        konacno.setText((String) data.get("KONACNO"));
        konacno.setBackgroundColor(Color.parseColor("#2A9DF4"));

        a1.setText((String) data.get("A1"));
        a1.setBackgroundColor(Color.parseColor("#2A9DF4"));
        a1.setOnClickListener(null);

        a2.setText((String) data.get("A2"));
        a2.setBackgroundColor(Color.parseColor("#2A9DF4"));
        a2.setOnClickListener(null);

        a3.setText((String) data.get("A3"));
        a3.setBackgroundColor(Color.parseColor("#2A9DF4"));
        a3.setOnClickListener(null);

        a4.setText((String) data.get("A4"));
        a4.setBackgroundColor(Color.parseColor("#2A9DF4"));
        a4.setOnClickListener(null);

        b1.setText((String) data.get("B1"));
        b1.setBackgroundColor(Color.parseColor("#2A9DF4"));
        b1.setOnClickListener(null);

        b2.setText((String) data.get("B2"));
        b2.setBackgroundColor(Color.parseColor("#2A9DF4"));
        b2.setOnClickListener(null);

        b3.setText((String) data.get("B3"));
        b3.setBackgroundColor(Color.parseColor("#2A9DF4"));
        b3.setOnClickListener(null);

        b4.setText((String) data.get("B4"));
        b4.setBackgroundColor(Color.parseColor("#2A9DF4"));
        b4.setOnClickListener(null);

        c1.setText((String) data.get("C1"));
        c1.setBackgroundColor(Color.parseColor("#2A9DF4"));
        c1.setOnClickListener(null);

        c2.setText((String) data.get("C2"));
        c2.setBackgroundColor(Color.parseColor("#2A9DF4"));
        c2.setOnClickListener(null);

        c3.setText((String) data.get("C3"));
        c3.setBackgroundColor(Color.parseColor("#2A9DF4"));
        c3.setOnClickListener(null);

        c4.setText((String) data.get("C4"));
        c4.setBackgroundColor(Color.parseColor("#2A9DF4"));
        c4.setOnClickListener(null);

        d1.setText((String) data.get("D1"));
        d1.setBackgroundColor(Color.parseColor("#2A9DF4"));
        d1.setOnClickListener(null);

        d2.setText((String) data.get("D2"));
        d2.setBackgroundColor(Color.parseColor("#2A9DF4"));
        d2.setOnClickListener(null);

        d3.setText((String) data.get("D3"));
        d3.setBackgroundColor(Color.parseColor("#2A9DF4"));
        d3.setOnClickListener(null);

        d4.setText((String) data.get("D4"));
        d4.setBackgroundColor(Color.parseColor("#2A9DF4"));
        d4.setOnClickListener(null);

        resenjeA.setText((String) data.get("RESENJE A"));
        resenjeA.setBackgroundColor(Color.parseColor("#2A9DF4"));
        resenjeA.setOnClickListener(null);
        resenjeA.setEnabled(false);
        resenjeA.setFocusable(false);

        resenjeB.setText((String) data.get("RESENJE B"));
        resenjeB.setBackgroundColor(Color.parseColor("#2A9DF4"));
        resenjeB.setOnClickListener(null);
        resenjeB.setEnabled(false);
        resenjeB.setFocusable(false);

        resenjeC.setText((String) data.get("RESENJE C"));
        resenjeC.setBackgroundColor(Color.parseColor("#2A9DF4"));
        resenjeC.setOnClickListener(null);
        resenjeC.setEnabled(false);
        resenjeC.setFocusable(false);

        resenjeD.setText((String) data.get("RESENJE D"));
        resenjeD.setBackgroundColor(Color.parseColor("#2A9DF4"));
        resenjeD.setOnClickListener(null);
        resenjeD.setEnabled(false);
        resenjeD.setFocusable(false);

        konacno.setEnabled(false);
        konacno.setFocusable(false);
    }

    private void addPointsForKonacno(){

        boolean neotvorenaA = true;
        boolean neotvorenaB = true;
        boolean neotvorenaC = true;
        boolean neotvorenaD = true;
        List<Boolean> neotvoreneKolone = new ArrayList<>();

        if (!resenjeA.getText().toString().equalsIgnoreCase((String) data.get("RESENJE A"))){
            neotvoreneKolone.add(neotvorenaA);
            poeniKonacno = poeniKonacno + poeniKolonaA;
        }
        if (!resenjeB.getText().toString().equalsIgnoreCase((String) data.get("RESENJE B"))){
            neotvoreneKolone.add(neotvorenaB);
            poeniKonacno = poeniKonacno + poeniKolonaB;
        }
        if (!resenjeC.getText().toString().equalsIgnoreCase((String) data.get("RESENJE C"))){
            neotvoreneKolone.add(neotvorenaC);
            poeniKonacno = poeniKonacno + poeniKolonaC;
        }
        if (!resenjeD.getText().toString().equalsIgnoreCase((String) data.get("RESENJE D"))){
            neotvoreneKolone.add(neotvorenaD);
            poeniKonacno = poeniKonacno + poeniKolonaD;
        }


        poeniKonacno = poeniKonacno + 7;
        poeniUkupno = poeniUkupno + poeniKonacno;

        bluePlayer.setText(""+poeniUkupno+"");
    }

    private void disableClickListenerOnAllButtons(View view) {

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof Button) {
                    child.setOnClickListener(null);
                } else if (child instanceof ViewGroup) {
                    disableClickListenerOnAllButtons(child); // Recursive call for nested view groups
                }
            }
        }

    }


    private void disableEditOnAllInputField(View view) {

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof TextInputEditText) {
                    TextInputEditText inputEditText = (TextInputEditText) child;
                    inputEditText.setEnabled(false);
                    inputEditText.setFocusable(false);
                    inputEditText.setClickable(false);
                } else if (child instanceof ViewGroup) {
                    disableEditOnAllInputField(child); // Recursive call for nested view groups
                }
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancel the timer if the activity is destroyed
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
