package com.example.slagalica.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slagalica.R;
import com.example.slagalica.activities.StartUpActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MojBrojFragment extends Fragment {

    private CountDownTimer countDownTimer;
    private Button timerButton;
    private Handler handler;
    private Button bluePlayer;

    private Button plus;
    private Button minus;
    private Button puta;
    private Button podeljeno;
    private Button otvorenaZagrada;
    private Button zatvorenaZagrada;
    private Button broj1;
    private Button broj2;
    private Button broj3;
    private Button broj4;
    private Button broj5;
    private Button broj6;
    private Button stop;
    private Button potvrdi;
    private Button obrisi;
    private Button textInput;
    private Button resenjeBlue;
    private Button trazenoResenje;

    private String unetiIzraz = "";

    private List<Button> numberButtons = new ArrayList<>();
    private Handler handler2 = new Handler();
    private Runnable spinnerRunnable1;
    private Runnable spinnerRunnable2;
    private Runnable spinnerRunnable3;
    private Runnable spinnerRunnable4;
    private Runnable spinnerRunnable5;
    private Runnable spinnerRunnable6;

    private Runnable spinnerRunnable7;

    private int clickCount = 0;

    private boolean isSpinning = false;
    private int currentNumber;

    private static int poeniUkupno;

    public static MojBrojFragment newInstance(int someParam) {

        poeniUkupno = someParam;
        Bundle args = new Bundle();
        args.putString("key", "test");

        MojBrojFragment fragment = new MojBrojFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moj_broj, container, false);

        /*Bundle bundle = getArguments();
        if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        initializeButtons(view);

        handler = new Handler();

        // Set the duration of the timer
        long timerDurationMillis = 60000;

        countDownTimer = new CountDownTimer(timerDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                long secondsRemaining = millisUntilFinished / 1000;
                timerButton.setText("" + secondsRemaining + "");

                // Check if 5 seconds have elapsed
                //Posle 5 sekundi postavi brojeve
                long elapsedSeconds = (timerDurationMillis - millisUntilFinished) / 1000;
                if (elapsedSeconds >= 5) {
                    handler2.removeCallbacksAndMessages(null);
                }
            }

            @Override
            public void onFinish() {

                // Perform the action when the timer is over

                bluePlayer.setText(String.valueOf(poeniUkupno));

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform the desired action after the delay
                        // Navigate to another fragment
                        startActivity(new Intent(MojBrojFragment.this.getActivity(), StartUpActivity.class));

                    }
                }, 4000); // 4 seconds delay
            }
        };

        // Startuj tajmer
        countDownTimer.start();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonClickListener();
    }


    private void setButtonClickListener() {


        if (!isSpinning) {
            isSpinning = true;
            currentNumber = 0;
            broj1.setText("0"); // Set initial number

            // Define the runnable to update the number periodically
            spinnerRunnable1 = new Runnable() {
                @Override
                public void run() {
                    currentNumber = generateRandomNumber(1, 9); // Generate a random number between 1 and 9
                    broj1.setText(String.valueOf(currentNumber)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable1, 8);

            spinnerRunnable2 = new Runnable() {
                @Override
                public void run() {
                    currentNumber = generateRandomNumber(1, 9); // Generate a random number between 1 and 9
                    broj2.setText(String.valueOf(currentNumber)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable2, 8);

            spinnerRunnable3 = new Runnable() {
                @Override
                public void run() {
                    currentNumber = generateRandomNumber(1, 9); // Generate a random number between 1 and 9
                    broj3.setText(String.valueOf(currentNumber)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable3, 8);

            spinnerRunnable4 = new Runnable() {
                @Override
                public void run() {
                    currentNumber = generateRandomNumber(1, 9); // Generate a random number between 1 and 9
                    broj4.setText(String.valueOf(currentNumber)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable4, 8);

            spinnerRunnable5 = new Runnable() {
                @Override
                public void run() {

                    Random random = new Random();

                    int randomNumber = random.nextInt(3); // Generate a random number between 0 and 2

                    int result;

                    switch (randomNumber) {
                        case 0:
                            result = 10;
                            break;
                        case 1:
                            result = 15;
                            break;
                        case 2:
                            result = 20;
                            break;
                        default:
                            // Handle any other cases here
                            result = 0;
                            break;
                    }

                    broj5.setText(String.valueOf(result)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable5, 8);


            spinnerRunnable6 = new Runnable() {
                @Override
                public void run() {

                    Random random = new Random();

                    int randomNumber = random.nextInt(4); // Generate a random number between 0 and 3

                    int result;

                    switch (randomNumber) {
                        case 0:
                            result = 25;
                            break;
                        case 1:
                            result = 50;
                            break;
                        case 2:
                            result = 75;
                            break;
                        case 3:
                            result = 100;
                            break;
                        default:
                            // Handle any other cases here
                            result = 0;
                            break;
                    }

                    broj6.setText(String.valueOf(result)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable6, 8);

            spinnerRunnable7 = new Runnable() {
                @Override
                public void run() {
                    currentNumber = generateRandomNumber(1, 1000); // Generate a random number between 1 and 9
                    trazenoResenje.setText(String.valueOf(currentNumber)); // Update the button's text

                    // Schedule the next update after a delay
                    handler2.postDelayed(this, 8);
                }
            };

            // Start the spinning animation
            handler2.postDelayed(spinnerRunnable7, 8);

        }

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;  //Increment for click

                if (clickCount == 1) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable7); // Stop the spinning animation
                } else if (clickCount == 2) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable1); // Stop the spinning animation
                } else if (clickCount == 3) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable2); // Stop the spinning animation
                } else if (clickCount == 4) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable3); // Stop the spinning animation
                } else if (clickCount == 5) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable4); // Stop the spinning animation
                } else if (clickCount == 6) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable5); // Stop the spinning animation
                } else if (clickCount == 7) {
                    isSpinning = false;
                    handler2.removeCallbacks(spinnerRunnable6); // Stop the spinning animation
                }


            }
        });

        broj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj1.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj1.setEnabled(false);
                broj1.setFocusable(false);
                broj1.setClickable(false);
                disableAllButtons(broj1);
            }
        });

        broj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj2.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj2.setEnabled(false);
                broj2.setFocusable(false);
                broj2.setClickable(false);
                disableAllButtons(broj2);
            }
        });

        broj3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj3.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj3.setEnabled(false);
                broj3.setFocusable(false);
                broj3.setClickable(false);
                disableAllButtons(broj3);
            }
        });

        broj4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj4.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj4.setEnabled(false);
                broj4.setFocusable(false);
                broj4.setClickable(false);
                disableAllButtons(broj4);
            }
        });

        broj5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj5.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj5.setEnabled(false);
                broj5.setFocusable(false);
                broj5.setClickable(false);
                disableAllButtons(broj5);
            }
        });

        broj6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + broj6.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                broj6.setEnabled(false);
                broj6.setFocusable(false);
                broj6.setClickable(false);
                disableAllButtons(broj6);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + plus.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + minus.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        puta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + puta.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        podeljeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + podeljeno.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        otvorenaZagrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + otvorenaZagrada.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        zatvorenaZagrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unetiIzraz = unetiIzraz + zatvorenaZagrada.getText().toString();
                textInput.setText(unetiIzraz);
                resolve();
                enableAllButtons();
            }
        });

        obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unetiIzraz.length() > 0) {

                    String lastChar = unetiIzraz.substring(unetiIzraz.length() - 1);

                    for (Button button : numberButtons) {

                        if (unetiIzraz.length() >= 3 && button.getText().toString().equals(unetiIzraz.substring(unetiIzraz.length() - 3))) {
                            lastChar = unetiIzraz.substring(unetiIzraz.length() - 3);
                            unetiIzraz = unetiIzraz.substring(0, unetiIzraz.length() - 3);
                            break;
                        } else if (unetiIzraz.length() >= 2 && button.getText().toString().equals(unetiIzraz.substring(unetiIzraz.length() - 2))) {
                            lastChar = unetiIzraz.substring(unetiIzraz.length() - 2);
                            unetiIzraz = unetiIzraz.substring(0, unetiIzraz.length() - 2);
                            break;
                        } else if (unetiIzraz.length() >= 1 && button.getText().toString().equals(unetiIzraz.substring(unetiIzraz.length() - 1))) {

                            lastChar = unetiIzraz.substring(unetiIzraz.length() - 1);
                            unetiIzraz = unetiIzraz.substring(0, unetiIzraz.length() - 1);
                            break;

                        }

                    }


                    if (!lastChar.equals("+") && !lastChar.equals("-") && !lastChar.equals("*") && !lastChar.equals("/") && !lastChar.equals("(") && !lastChar.equals(")")) {

                        enableAllButtons();
                        enableClickListener(view, lastChar);

                    }else{
                        unetiIzraz = unetiIzraz.substring(0, unetiIzraz.length() - 1);
                    }

                    textInput.setText(unetiIzraz);
                    resenjeBlue.setText("");
                    resolve();

                }

            }
        });


        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler2.removeCallbacksAndMessages(null);
                if(resenjeBlue.getText().toString().equals(trazenoResenje.getText().toString())){

                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer.onFinish();
                    }
                    poeniUkupno += 20;
                    bluePlayer.setText(String.valueOf(poeniUkupno));

                }else {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer.onFinish();
                    }
                    bluePlayer.setText(String.valueOf(poeniUkupno));
                }
            }
        });
    }

    private void enableClickListener(View view, String lastChar) {
        for (Button button : numberButtons) {
            if (button.getText().toString().equals(lastChar)) {
                button.setEnabled(true);
                button.setFocusable(true);
                button.setClickable(true);
            }
        }
    }

    private void enableAllButtons() {
        for (Button button : numberButtons) {
            if(button.isEnabled()){
                button.setClickable(true);
            }


        }
    }

    private void disableAllButtons(Button checkButton) {
        for (Button button : numberButtons) {
            if(button == checkButton){
                continue;
            }
            button.setClickable(false);
        }
    }

    private void initializeButtons(View view) {

        plus = view.findViewById(R.id.plus);
        minus = view.findViewById(R.id.minus);
        puta = view.findViewById(R.id.puta);
        podeljeno = view.findViewById(R.id.podeljeno);
        otvorenaZagrada = view.findViewById(R.id.otvorenaZagrada);
        zatvorenaZagrada = view.findViewById(R.id.zatvorenaZagrada);
        stop = view.findViewById(R.id.button_stop);
        potvrdi = view.findViewById(R.id.button_potvrdi);
        broj1 = view.findViewById(R.id.button_number1);
        broj2 = view.findViewById(R.id.button_number2);
        broj3 = view.findViewById(R.id.button_number3);
        broj4 = view.findViewById(R.id.button_number4);
        broj5 = view.findViewById(R.id.button_number5);
        broj6 = view.findViewById(R.id.button_number6);
        obrisi = view.findViewById(R.id.obrisi);
        textInput = view.findViewById(R.id.textInput);
        resenjeBlue = view.findViewById(R.id.button_blue_number);
        trazenoResenje = view.findViewById(R.id.button_wanted_number);
        bluePlayer = view.findViewById(R.id.blue_player);
        bluePlayer.setText(String.valueOf(poeniUkupno));
        timerButton = view.findViewById(R.id.stopwatch);

        numberButtons.add(broj1);
        numberButtons.add(broj2);
        numberButtons.add(broj3);
        numberButtons.add(broj4);
        numberButtons.add(broj5);
        numberButtons.add(broj6);

    }

    private void resolve() {

        try {

            // Create an expression from the string
            Expression expression = new ExpressionBuilder(textInput.getText().toString()).build();
            // Evaluate the expression
            double result = expression.evaluate();
            // Convert the result to an integer if needed
            int intValue = (int) result;
            // Print the result
            resenjeBlue.setText(String.valueOf(intValue));
        } catch (ArithmeticException | IllegalArgumentException e) {
            resenjeBlue.setText("");
        }


    }


    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
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
