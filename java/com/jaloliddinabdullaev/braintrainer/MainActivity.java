package com.jaloliddinabdullaev.braintrainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;
    Button nextStep;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    CountDownTimer countDownTimer;
    GridLayout gridLayout;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int minusScore = 0;
    int numberOfQuestions = 0;
    int alert = 0;
    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void playAgain(View view) {

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        alert=0;
        minusScore=0;
        playAgainButton.setVisibility(View.INVISIBLE);
        nextStep.setVisibility(View.INVISIBLE);
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        generateQuestion();


        countDownTimer=new CountDownTimer(30900, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                if (alert==3){
                    resultTextView.setText("Your score: " + Integer.toString(score-10) + "/" + Integer.toString(numberOfQuestions));
                }else if (alert==2){
                    resultTextView.setText("Your score: " + Integer.toString(score-5) + "/" + Integer.toString(numberOfQuestions));
                }else resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));



                if (score >= 17) {
                    nextStep.setVisibility(View.VISIBLE);
                    nextStep.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), SecondChallange.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    nextStep.setVisibility(View.VISIBLE);
                    nextStep.setBackgroundColor(Color.RED);
                    if (alert==2){
                        nextStep.setText("Failed you get " + (score-5) + " point  \n min 17 point to next step");
                    }else if (alert==3){
                        nextStep.setText("Failed you get " + (score-10) + " point  \n min 17 point to next step");
                    }else {
                        nextStep.setText("Failed you get " + score + " point  \n min 17 point to next step");
                    }
                }
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    public void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(30);
        int b = rand.nextInt(20);
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        int incorrectAnswer;
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);
                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    @SuppressLint("SetTextI18n")
    public void chooseAnswer(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            if (minusScore >= 1) {
                minusScore--;
                if (minusScore>=1){
                    minusScore--;
                }
            }
            resultTextView.setText("Correct!");
        } else {
            minusScore++;
            if (minusScore == 3) {
                alert++;
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Warning!!!")
                        .setMessage("You are unwillingly playing or choosing the answer without calculation or miscalculating, " +
                                "but from second time your mark will be decreased by 5 points for each warning!!!")
                        .setNegativeButton("continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                minusScore = 0;
                            }
                        }).show();
            }
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }
    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        sumTextView = findViewById(R.id.sumTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        resultTextView = findViewById(R.id.resultTextView);
        pointsTextView = findViewById(R.id.pointsTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameRelativeLayout = findViewById(R.id.gameRelativeLayout);
        nextStep = findViewById(R.id.nextStep);
        gridLayout=findViewById(R.id.gridLayout);
        gridLayout.setFilterTouchesWhenObscured(false);
        gameRelativeLayout.setClickable(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}