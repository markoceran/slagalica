package com.example.slagalica.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.slagalica.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkockoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkockoFragment extends Fragment {

    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;

    private Button bluePlayer;

    private List<Button> column1Buttons = new ArrayList<>();
    private List<Button> column2Buttons = new ArrayList<>();
    private List<Button> column3Buttons = new ArrayList<>();
    private List<Button> column4Buttons = new ArrayList<>();
    private List<Button> column5Buttons = new ArrayList<>();
    private List<Button> column6Buttons = new ArrayList<>();

    private List<Button> solutionButtons = new ArrayList<>();

    private List<Button> colorButtonsColumn1 = new ArrayList<>();
    private List<Button> colorButtonsColumn2 = new ArrayList<>();
    private List<Button> colorButtonsColumn3 = new ArrayList<>();
    private List<Button> colorButtonsColumn4 = new ArrayList<>();
    private List<Button> colorButtonsColumn5 = new ArrayList<>();
    private List<Button> colorButtonsColumn6 = new ArrayList<>();

    private Button kolona1x1;
    private Button kolona1x2;
    private Button kolona1x3;
    private Button kolona1x4;
    private Button kolona1x5;
    private Button kolona1x6;
    private Button kolona1x7;
    private Button kolona1x8;

    private Button kolona2x1;
    private Button kolona2x2;
    private Button kolona2x3;
    private Button kolona2x4;
    private Button kolona2x5;
    private Button kolona2x6;
    private Button kolona2x7;
    private Button kolona2x8;

    private Button kolona3x1;
    private Button kolona3x2;
    private Button kolona3x3;
    private Button kolona3x4;
    private Button kolona3x5;
    private Button kolona3x6;
    private Button kolona3x7;
    private Button kolona3x8;

    private Button kolona4x1;
    private Button kolona4x2;
    private Button kolona4x3;
    private Button kolona4x4;
    private Button kolona4x5;
    private Button kolona4x6;
    private Button kolona4x7;
    private Button kolona4x8;

    private Button kolona5x1;
    private Button kolona5x2;
    private Button kolona5x3;
    private Button kolona5x4;
    private Button kolona5x5;
    private Button kolona5x6;
    private Button kolona5x7;
    private Button kolona5x8;

    private Button kolona6x1;
    private Button kolona6x2;
    private Button kolona6x3;
    private Button kolona6x4;
    private Button kolona6x5;
    private Button kolona6x6;
    private Button kolona6x7;
    private Button kolona6x8;

    private Button kolona8x1;
    private Button kolona8x2;
    private Button kolona8x3;
    private Button kolona8x4;


    private Button buttonOwl;
    private Button buttonClub;
    private Button buttonSpade;
    private Button buttonHeart;
    private Button buttonDiamond;
    private Button buttonStar;

    private int okClickCounter = 0;

    private Button ok;

    private int brojac = 0;


    public static SkockoFragment newInstance(String someParam) {
        Bundle args = new Bundle();
        args.putString("key", "test");

        SkockoFragment fragment = new SkockoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skocko, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        initializeButtons(view);
        createCombination();

        handler = new Handler();

        // Set the duration of the timer
        long timerDurationMillis = 30000;

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

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform the desired action after the delay
                        // Navigate to another fragment
                        KorakPoKorakFragment korakPoKorakFragment = KorakPoKorakFragment.newInstance("test");
                        getChildFragmentManager().beginTransaction().replace(R.id.skocko_layout, korakPoKorakFragment).commit();
                    }
                }, 5000); // 5 seconds delay
            }
        };

        // Start the timer
        countDownTimer.start();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonClickListener();
    }

    private void setButtonClickListener() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okClickCounter++;

                if (okClickCounter == 1) {

                    //crveno i zuto

                    for (int i = 0; i < column1Buttons.size(); i++) {
                        Button buttonColumn = column1Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn1){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2) ) {

                                    for (Button colorButton:colorButtonsColumn1){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona1x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona1x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona1x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona1x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                                ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (okClickCounter == 2) {

                    //crveno i zuto
                    for (int i = 0; i < column2Buttons.size(); i++) {
                        Button buttonColumn = column2Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn2){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2)) {

                                    for (Button colorButton:colorButtonsColumn2){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona2x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona2x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona2x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona2x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                        ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }else if (okClickCounter == 3) {

                    //crveno i zuto
                    for (int i = 0; i < column3Buttons.size(); i++) {
                        Button buttonColumn = column3Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn3){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2)) {

                                    for (Button colorButton:colorButtonsColumn3){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona3x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona3x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona3x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona3x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                        ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }else if (okClickCounter == 4) {

                    //crveno i zuto
                    for (int i = 0; i < column4Buttons.size(); i++) {
                        Button buttonColumn = column4Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn4){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2)) {

                                    for (Button colorButton:colorButtonsColumn4){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona4x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona4x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona4x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona4x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                        ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }else if (okClickCounter == 5) {

                    //crveno i zuto
                    for (int i = 0; i < column5Buttons.size(); i++) {
                        Button buttonColumn = column5Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn5){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2)) {

                                    for (Button colorButton:colorButtonsColumn5){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona5x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona5x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona5x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona5x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                        ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }else if (okClickCounter == 6) {

                    //crveno i zuto
                    for (int i = 0; i < column6Buttons.size(); i++) {
                        Button buttonColumn = column6Buttons.get(i);
                        Button buttonSolution = solutionButtons.get(i);

                        Drawable drawableColumn = buttonColumn.getCompoundDrawables()[0];
                        Drawable drawableSolution = buttonSolution.getCompoundDrawables()[0];

                        Bitmap bitmapColumn = drawableToBitmap(drawableColumn);
                        Bitmap bitmapSolution = drawableToBitmap(drawableSolution);

                        if (bitmapColumn!=null && bitmapSolution!=null && bitmapColumn.sameAs(bitmapSolution)) {

                            for (Button colorButton:colorButtonsColumn6){

                                Drawable backgroundDrawable = colorButton.getBackground();
                                Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                        desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                        backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())){
                                    colorButton.setBackgroundResource(R.drawable.hit);
                                    break;
                                }
                            }

                        }else {
                            for (Button buttonSolution2:solutionButtons){

                                Drawable drawableColumn2 = buttonColumn.getCompoundDrawables()[0];
                                Drawable drawableSolution2 = buttonSolution2.getCompoundDrawables()[0];

                                Bitmap bitmapColumn2 = drawableToBitmap(drawableColumn2);
                                Bitmap bitmapSolution2 = drawableToBitmap(drawableSolution2);

                                if (bitmapColumn2!=null && bitmapSolution2!=null && bitmapColumn2.sameAs(bitmapSolution2)) {

                                    for (Button colorButton:colorButtonsColumn6){

                                        Drawable backgroundDrawable = colorButton.getBackground();
                                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.field, null);

                                        Drawable row1 = kolona6x1.getCompoundDrawables()[0];
                                        Drawable row2 = kolona6x2.getCompoundDrawables()[0];
                                        Drawable row3 = kolona6x3.getCompoundDrawables()[0];
                                        Drawable row4 = kolona6x4.getCompoundDrawables()[0];

                                        Bitmap bitmapRow1 = drawableToBitmap(row1);
                                        Bitmap bitmapRow2 = drawableToBitmap(row2);
                                        Bitmap bitmapRow3 = drawableToBitmap(row3);
                                        Bitmap bitmapRow4 = drawableToBitmap(row4);

                                        if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null &&
                                                desiredDrawable != null && desiredDrawable.getConstantState() != null &&
                                                backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState()) &&
                                                (!bitmapRow1.sameAs(bitmapRow2) && !bitmapRow1.sameAs(bitmapRow3) && !bitmapRow1.sameAs(bitmapRow4))
                                        ){
                                            colorButton.setBackgroundResource(R.drawable.miss);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                if(okClickCounter == 1){
                    for(Button color:colorButtonsColumn1){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("20");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }
                }else if(okClickCounter == 2){
                    for(Button color:colorButtonsColumn2){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("20");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }
                }else if(okClickCounter == 3){
                    for(Button color:colorButtonsColumn3){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("15");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }
                }else if(okClickCounter == 4){

                    for(Button color:colorButtonsColumn4){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("15");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }
                }else if(okClickCounter == 5){
                    for(Button color:colorButtonsColumn5){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("10");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }

                }else {
                    for(Button color:colorButtonsColumn6){

                        Drawable backgroundDrawable = color.getBackground();
                        Drawable desiredDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hit, null);
                        if(backgroundDrawable.getConstantState().equals(desiredDrawable.getConstantState())) {
                            brojac ++;
                        }

                    }
                    if(brojac == 4){
                        bluePlayer.setText("10");
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }else{
                        brojac = 0;
                    }

                }

            }
        });


        buttonOwl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.owl, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });

        buttonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });

        buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });

        buttonSpade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit_of_spades, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });

        buttonClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.clubs, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });

        buttonDiamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Button button : column1Buttons) {
                    if (button.getCompoundDrawables()[0] == null) {
                        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                        break;
                    }
                }

                if (okClickCounter == 1) {
                    for (Button button : column2Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 2) {
                    for (Button button : column3Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 3) {
                    for (Button button : column4Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 4) {
                    for (Button button : column5Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                            break;
                        }
                    }
                } else if (okClickCounter == 5) {
                    for (Button button : column6Buttons) {
                        if (button.getCompoundDrawables()[0] == null) {
                            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.suit, 0, 0, 0);
                            break;
                        }
                    }
                } else {

                }
            }
        });


    }

    private void initializeButtons(View view) {

        bluePlayer = view.findViewById(R.id.blue_player);
        ok = view.findViewById(R.id.button_ok);

        kolona1x1 = view.findViewById(R.id.column1row1);
        kolona1x2 = view.findViewById(R.id.column1row2);
        kolona1x3 = view.findViewById(R.id.column1row3);
        kolona1x4 = view.findViewById(R.id.column1row4);
        kolona1x5 = view.findViewById(R.id.column1row5);
        kolona1x6 = view.findViewById(R.id.column1row6);
        kolona1x7 = view.findViewById(R.id.column1row7);
        kolona1x8 = view.findViewById(R.id.column1row8);

        kolona2x1 = view.findViewById(R.id.column2row1);
        kolona2x2 = view.findViewById(R.id.column2row2);
        kolona2x3 = view.findViewById(R.id.column2row3);
        kolona2x4 = view.findViewById(R.id.column2row4);
        kolona2x5 = view.findViewById(R.id.column2row5);
        kolona2x6 = view.findViewById(R.id.column2row6);
        kolona2x7 = view.findViewById(R.id.column2row7);
        kolona2x8 = view.findViewById(R.id.column2row8);

        kolona3x1 = view.findViewById(R.id.column3row1);
        kolona3x2 = view.findViewById(R.id.column3row2);
        kolona3x3 = view.findViewById(R.id.column3row3);
        kolona3x4 = view.findViewById(R.id.column3row4);
        kolona3x5 = view.findViewById(R.id.column3row5);
        kolona3x6 = view.findViewById(R.id.column3row6);
        kolona3x7 = view.findViewById(R.id.column3row7);
        kolona3x8 = view.findViewById(R.id.column3row8);

        kolona4x1 = view.findViewById(R.id.column4row1);
        kolona4x2 = view.findViewById(R.id.column4row2);
        kolona4x3 = view.findViewById(R.id.column4row3);
        kolona4x4 = view.findViewById(R.id.column4row4);
        kolona4x5 = view.findViewById(R.id.column4row5);
        kolona4x6 = view.findViewById(R.id.column4row6);
        kolona4x7 = view.findViewById(R.id.column4row7);
        kolona4x8 = view.findViewById(R.id.column4row8);


        kolona5x1 = view.findViewById(R.id.column5row1);
        kolona5x2 = view.findViewById(R.id.column5row2);
        kolona5x3 = view.findViewById(R.id.column5row3);
        kolona5x4 = view.findViewById(R.id.column5row4);
        kolona5x5 = view.findViewById(R.id.column5row5);
        kolona5x6 = view.findViewById(R.id.column5row6);
        kolona5x7 = view.findViewById(R.id.column5row7);
        kolona5x8 = view.findViewById(R.id.column5row8);

        kolona6x1 = view.findViewById(R.id.column6row1);
        kolona6x2 = view.findViewById(R.id.column6row2);
        kolona6x3 = view.findViewById(R.id.column6row3);
        kolona6x4 = view.findViewById(R.id.column6row4);
        kolona6x5 = view.findViewById(R.id.column6row5);
        kolona6x6 = view.findViewById(R.id.column6row6);
        kolona6x7 = view.findViewById(R.id.column6row7);
        kolona6x8 = view.findViewById(R.id.column6row8);


        kolona8x1 = view.findViewById(R.id.column8row1);
        kolona8x2 = view.findViewById(R.id.column8row2);
        kolona8x3 = view.findViewById(R.id.column8row3);
        kolona8x4 = view.findViewById(R.id.column8row4);

        buttonOwl = view.findViewById(R.id.button_owl);
        buttonSpade = view.findViewById(R.id.button_spade);
        buttonDiamond = view.findViewById(R.id.button_diamond);
        buttonStar = view.findViewById(R.id.button_star);
        buttonHeart = view.findViewById(R.id.button_heart);
        buttonClub = view.findViewById(R.id.button_club);

        timerButton = view.findViewById(R.id.stopwatch);

        column1Buttons.add(kolona1x1);
        column1Buttons.add(kolona1x2);
        column1Buttons.add(kolona1x3);
        column1Buttons.add(kolona1x4);
        column2Buttons.add(kolona2x1);
        column2Buttons.add(kolona2x2);
        column2Buttons.add(kolona2x3);
        column2Buttons.add(kolona2x4);
        column3Buttons.add(kolona3x1);
        column3Buttons.add(kolona3x2);
        column3Buttons.add(kolona3x3);
        column3Buttons.add(kolona3x4);
        column4Buttons.add(kolona4x1);
        column4Buttons.add(kolona4x2);
        column4Buttons.add(kolona4x3);
        column4Buttons.add(kolona4x4);
        column5Buttons.add(kolona5x1);
        column5Buttons.add(kolona5x2);
        column5Buttons.add(kolona5x3);
        column5Buttons.add(kolona5x4);
        column6Buttons.add(kolona6x1);
        column6Buttons.add(kolona6x2);
        column6Buttons.add(kolona6x3);
        column6Buttons.add(kolona6x4);

        colorButtonsColumn1.add(kolona1x5);
        colorButtonsColumn1.add(kolona1x6);
        colorButtonsColumn1.add(kolona1x7);
        colorButtonsColumn1.add(kolona1x8);

        colorButtonsColumn2.add(kolona2x5);
        colorButtonsColumn2.add(kolona2x6);
        colorButtonsColumn2.add(kolona2x7);
        colorButtonsColumn2.add(kolona2x8);

        colorButtonsColumn3.add(kolona3x5);
        colorButtonsColumn3.add(kolona3x6);
        colorButtonsColumn3.add(kolona3x7);
        colorButtonsColumn3.add(kolona3x8);

        colorButtonsColumn4.add(kolona4x5);
        colorButtonsColumn4.add(kolona4x6);
        colorButtonsColumn4.add(kolona4x7);
        colorButtonsColumn4.add(kolona4x8);

        colorButtonsColumn5.add(kolona5x5);
        colorButtonsColumn5.add(kolona5x6);
        colorButtonsColumn5.add(kolona5x7);
        colorButtonsColumn5.add(kolona5x8);

        colorButtonsColumn6.add(kolona6x5);
        colorButtonsColumn6.add(kolona6x6);
        colorButtonsColumn6.add(kolona6x7);
        colorButtonsColumn6.add(kolona6x8);


        solutionButtons.add(kolona8x1);
        solutionButtons.add(kolona8x2);
        solutionButtons.add(kolona8x3);
        solutionButtons.add(kolona8x4);
    }

    private void createCombination() {

        // Prepare your set of pictures (replace with your own image references)
        int[] imageReferences = {R.drawable.owl, R.drawable.clubs, R.drawable.suit_of_spades, R.drawable.heart, R.drawable.suit, R.drawable.star};

        // Create a Random object
        Random random = new Random();

        // Iterate over your buttons and assign a random image to each button
        for (Button button : solutionButtons) {
            // Generate a random index within the range of the imageReferences array
            int randomIndex = random.nextInt(imageReferences.length);

            // Get the random image reference
            int imageReference = imageReferences[randomIndex];

            // Set the image as the button's background or image resource
            button.setCompoundDrawablesWithIntrinsicBounds(imageReference, 0, 0, 0);
        }

    }


    // Helper method to convert a Drawable to a Bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {

        if(drawable != null){

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;

        }else{
            return null;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}