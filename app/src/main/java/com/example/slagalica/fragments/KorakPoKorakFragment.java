package com.example.slagalica.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class KorakPoKorakFragment extends Fragment {

    public Map<String, Object> data = new HashMap<>();
    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;
    private Handler handler2;
    private Button button_row1;
    private Button button_row2;
    private Button button_row3;
    private Button button_row4;
    private Button button_row5;
    private Button button_row6;
    private Button button_row7;
    private TextInputEditText button_row8;

    private int poeniKorak1 = 20;

    private static int poeniUkupno;

    private Button bluePlayer;




    public static KorakPoKorakFragment newInstance(int someParam){

        poeniUkupno = someParam;
        Bundle args = new Bundle();
        args.putString("key","test");

        KorakPoKorakFragment fragment = new KorakPoKorakFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_korak_po_korak, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        button_row1 = view.findViewById(R.id.button_row1);
        button_row2 = view.findViewById(R.id.button_row2);
        button_row3 = view.findViewById(R.id.button_row3);
        button_row4 = view.findViewById(R.id.button_row4);
        button_row5 = view.findViewById(R.id.button_row5);
        button_row6 = view.findViewById(R.id.button_row6);
        button_row7 = view.findViewById(R.id.button_row7);

        button_row8 = view.findViewById(R.id.button_row8);

        bluePlayer = view.findViewById(R.id.blue_player);
        bluePlayer.setText(String.valueOf(poeniUkupno));

        timerButton = view.findViewById(R.id.stopwatch);
        handler = new Handler();
        handler2 = new Handler();

// Set the duration of the timer
        long timerDurationMillis = 71000;

        countDownTimer = new CountDownTimer(timerDurationMillis, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                long secondsRemaining = millisUntilFinished / 1000;
                timerButton.setText("" + secondsRemaining + "");

                long elapsedSeconds = (timerDurationMillis - millisUntilFinished) / 1000;
                if(elapsedSeconds == 1 ){
                    String value = (String) data.get("KORAK1");
                    button_row1.setText(value);
                }
                else if(elapsedSeconds < 10 && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 10 ) {
                    String value = (String) data.get("KORAK2");
                    button_row2.setText(value);
                }
                else if(elapsedSeconds >10 && elapsedSeconds <20  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 20 ) {
                    String value = (String) data.get("KORAK3");
                    button_row3.setText(value);
                }
                else if(elapsedSeconds >20 && elapsedSeconds <30  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 30 ) {
                    String value = (String) data.get("KORAK4");
                    button_row4.setText(value);
                }
                else if(elapsedSeconds >30 && elapsedSeconds <40  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 40 ) {
                    String value = (String) data.get("KORAK5");
                    button_row5.setText(value);
                }
                else if(elapsedSeconds >40 && elapsedSeconds <50  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 50 ) {
                    String value = (String) data.get("KORAK6");
                    button_row6.setText(value);
                }
                else if(elapsedSeconds >50 && elapsedSeconds <60  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 60 ) {
                    String value = (String) data.get("KORAK7");
                    button_row7.setText(value);
                }
                else if(elapsedSeconds >60 && elapsedSeconds <70  && setButtonClickListener()){
                    setButtonClickListener();
                }
                else if(elapsedSeconds == 70 ) {
                    setButtonClickListener();
                    bluePlayer.setText(""+poeniUkupno+"");
                }


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
                        MojBrojFragment mojBrojFragment = MojBrojFragment.newInstance(poeniUkupno);
                        getParentFragmentManager().beginTransaction().replace(R.id.korakPoKorakPage,mojBrojFragment).commit();
                    }
                }, 4000); // 4 seconds delay
            }
        };

        // Start the timer
        countDownTimer.start();

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

    public void getData() {

        //Dobavljanje podataka

        MainActivity.db.collection("korak-po-korak")
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

    private boolean setButtonClickListener() {

        button_row8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_row8.setText("");
            }
        });

        button_row8.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE"))) {

                        addPointsForKonacno();
                        openAllFields();

                        // To stop the timer, call cancel() and onFinish()
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }

                    }
                }
                return false;
            }
        });
        return false;
    }

    private void openAllFields() {

        button_row8.setText((String) data.get("RESENJE"));

        button_row1.setText((String) data.get("KORAK1"));
        button_row1.setEnabled(false);
        button_row1.setFocusable(false);
        button_row2.setText((String) data.get("KORAK2"));
        button_row2.setEnabled(false);
        button_row2.setFocusable(false);
        button_row3.setText((String) data.get("KORAK3"));
        button_row3.setEnabled(false);
        button_row3.setFocusable(false);
        button_row4.setText((String) data.get("KORAK4"));
        button_row4.setEnabled(false);
        button_row4.setFocusable(false);
        button_row5.setText((String) data.get("KORAK5"));
        button_row5.setEnabled(false);
        button_row5.setFocusable(false);
        button_row6.setText((String) data.get("KORAK6"));
        button_row6.setEnabled(false);
        button_row6.setFocusable(false);
        button_row7.setText((String) data.get("KORAK7"));
        button_row7.setEnabled(false);
        button_row7.setFocusable(false);

        button_row8.setEnabled(false);
        button_row8.setFocusable(false);



    }




    private void addPointsForKonacno(){

        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row1.getText().toString().equalsIgnoreCase((String) data.get("KORAK1"))){
            poeniUkupno = poeniUkupno + poeniKorak1;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row2.getText().toString().equalsIgnoreCase((String) data.get("KORAK2"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 2;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row3.getText().toString().equalsIgnoreCase((String) data.get("KORAK3"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 4;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row4.getText().toString().equalsIgnoreCase((String) data.get("KORAK4"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 6;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row5.getText().toString().equalsIgnoreCase((String) data.get("KORAK5"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 8;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row6.getText().toString().equalsIgnoreCase((String) data.get("KORAK6"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 10;
        }
        if (button_row8.getText().toString().equalsIgnoreCase((String) data.get("RESENJE")) && button_row7.getText().toString().equalsIgnoreCase((String) data.get("KORAK7"))){
            poeniUkupno = poeniUkupno + poeniKorak1 - 12;
        }


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
    public void onDestroy() {
        super.onDestroy();
        // Cancel the timer if the activity is destroyed
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    }