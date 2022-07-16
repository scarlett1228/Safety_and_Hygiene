package my.edu.utar.mobileappassignment2.fyp1.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import my.edu.utar.mobileappassignment2.fyp1.QuizActivity;
import my.edu.utar.mobileappassignment2.fyp1.R;

public class RoadSafetyQuiz extends AppCompatActivity {

    TextView tv;
    Button submit;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;
    Dialog myDialog;
    Button restart,ok;
    ImageButton hintsbutton;
    TextView tvquesnum;
    private TextView crtpop,wrgpop,fnlpop;
    private Toolbar toolbar;
    String crt,wrg,fnl;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //countdown
    //set 30 sec countdown
    private TextView countdown;
    private static final long COUNTDOWN_IN_MILLIS =30000;
    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    //www.educationquizzes.com/ks1/personal-social-and-health-education/road-safety/
    //www.educationquizzes.com/ks2/personal-social-and-health-education/road-safety/


    int count=0;
    public static int marks = 0, correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_safety_quiz);

        String questions[] = {
                getResources().getString(R.string.road_quiz_question_1), //1st
                getResources().getString(R.string.road_quiz_question_2),
                getResources().getString(R.string.road_quiz_question_3), //2nd
                getResources().getString(R.string.road_quiz_question_4),
                getResources().getString(R.string.road_quiz_question_5), //1st
                getResources().getString(R.string.road_quiz_question_6), //1st
                getResources().getString(R.string.road_quiz_question_7), //1st
                getResources().getString(R.string.road_quiz_question_8),
                getResources().getString(R.string.road_quiz_question_9),
                getResources().getString(R.string.road_quiz_question_10) //2nd

        };

        String answers[] = {getResources().getString(R.string.road_quiz_answer_1),getResources().getString(R.string.road_quiz_answer_2),getResources().getString(R.string.road_quiz_answer_3),getResources().getString(R.string.road_quiz_answer_4),
                getResources().getString(R.string.road_quiz_answer_5),getResources().getString(R.string.road_quiz_answer_6),
                getResources().getString(R.string.road_quiz_answer_7),getResources().getString(R.string.road_quiz_answer_8),
                getResources().getString(R.string.road_quiz_answer_9),getResources().getString(R.string.road_quiz_answer_10)};

        String option[] ={
                getResources().getString(R.string.road_quiz_option_1a), getResources().getString(R.string.road_quiz_option_1b),getResources().getString(R.string.road_quiz_answer_1), getResources().getString(R.string.road_quiz_option_1c),
                getResources().getString(R.string.road_quiz_option_2a),getResources().getString(R.string.road_quiz_option_2b),getResources().getString(R.string.road_quiz_option_2c),getResources().getString(R.string.road_quiz_answer_2),
                getResources().getString(R.string.road_quiz_answer_3), getResources().getString(R.string.road_quiz_option_3a),getResources().getString(R.string.road_quiz_option_3b),getResources().getString(R.string.road_quiz_option_3c),
                getResources().getString(R.string.road_quiz_option_4a),getResources().getString(R.string.road_quiz_answer_4),getResources().getString(R.string.road_quiz_option_4b),getResources().getString(R.string.road_quiz_option_4c),
                getResources().getString(R.string.road_quiz_option_5a),getResources().getString(R.string.road_quiz_option_5b),getResources().getString(R.string.road_quiz_answer_5),getResources().getString(R.string.road_quiz_option_5c),
                getResources().getString(R.string.road_quiz_option_6a), getResources().getString(R.string.road_quiz_option_6b), getResources().getString(R.string.road_quiz_option_6c), getResources().getString(R.string.road_quiz_answer_6),
                getResources().getString(R.string.road_quiz_option_7a),getResources().getString(R.string.road_quiz_option_7b),getResources().getString(R.string.road_quiz_answer_7),getResources().getString(R.string.road_quiz_option_7c),
                getResources().getString(R.string.road_quiz_answer_8),getResources().getString(R.string.road_quiz_option_8a), getResources().getString(R.string.road_quiz_option_8b), getResources().getString(R.string.road_quiz_option_8c),
                getResources().getString(R.string.road_quiz_option_9a), getResources().getString(R.string.road_quiz_option_9b),getResources().getString(R.string.road_quiz_option_9c), getResources().getString(R.string.road_quiz_answer_9),
                getResources().getString(R.string.road_quiz_option_10a),getResources().getString(R.string.road_quiz_option_10b),getResources().getString(R.string.road_quiz_answer_10), getResources().getString(R.string.road_quiz_option_10c)
        };

        String hints[] = {
                getResources().getString(R.string.road_quiz_hints_1), //1
                getResources().getString(R.string.road_quiz_hints_2), //2
                getResources().getString(R.string.road_quiz_hints_3), //3
                getResources().getString(R.string.road_quiz_hints_4), //4
                getResources().getString(R.string.road_quiz_hints_5), //5
                getResources().getString(R.string.road_quiz_hints_6), //6
                getResources().getString(R.string.road_quiz_hints_7), //7
                getResources().getString(R.string.road_quiz_hints_8), //8
                getResources().getString(R.string.road_quiz_hints_9), //9
                getResources().getString(R.string.road_quiz_hints_10)//10
        };

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.result_popup);
        crtpop = myDialog.findViewById(R.id.correctpop);
        wrgpop =  myDialog.findViewById(R.id.wrongpop);
        fnlpop=  myDialog.findViewById(R.id.scorepop);

        final TextView score = (TextView)findViewById(R.id.textView4);

        submit=(Button)findViewById(R.id.button14);
        tv=(TextView) findViewById(R.id.que);
        rg=(RadioGroup)findViewById(R.id.rgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton5);
        rb2=(RadioButton)findViewById(R.id.radioButton6);
        rb3=(RadioButton)findViewById(R.id.radioButton7);
        rb4=(RadioButton)findViewById(R.id.radioButton8);
        tv.setText(questions[count]);
        rb1.setText(option[0]);
        rb2.setText(option[1]);
        rb3.setText(option[2]);
        rb4.setText(option[3]);

        //countdown
        countdown = findViewById(R.id.countdown);
        textColorDefaultCd = countdown.getTextColors();

        //hints
        hintsbutton = findViewById(R.id.hints);

        //question number
        tvquesnum = findViewById(R.id.quenum);


        //countdown
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

        //hints
        if(count<questions.length) {
            hints(hints[count]);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //countdown
                countDownTimer.cancel();

                if(rg.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "skip", Toast.LENGTH_SHORT).show();
                    //countdown
                    countDownTimer.cancel();
                    //return;
                }
                else {
                    RadioButton userans = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                    String ansText = userans.getText().toString();
                    if (ansText.equals(answers[count])) {
                        correct++;
                        //countdown
                        if (timeLeftInMillis == 0) {
                            correct--;
                        }
                        countDownTimer.cancel();

                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    } else {
                        wrong++;
                        //countdown
                        if (timeLeftInMillis == 0) {
                            wrong--;
                        }
                        countDownTimer.cancel();

                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                count++;


                if (score != null)
                    score.setText(""+correct);

                if(count<questions.length)
                {
                    //countdown
                    timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                    startCountDown();
                    //hints
                    hints(hints[count]);
                    tv.setText(questions[count]);
                    rb1.setText(option[count*4]);
                    rb2.setText(option[count*4 +1]);
                    rb3.setText(option[count*4 +2]);
                    rb4.setText(option[count*4 +3]);

                    int ct = count +1;
                    String qn = String.valueOf(ct);
                    tvquesnum.setText(qn + "/10");
                }
                else
                {
                    marks=correct;
                    int percentage = (int)correct*10;
                    String crt = String.valueOf(correct);
                    String wrg = String.valueOf(wrong);
                    String fnl = String.valueOf(percentage);

                    if (mAuth.getCurrentUser() != null) {
                        //store DB
                        //store road DB
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("RoadSafetyQuizResult");
                        //store all
                        DatabaseReference dbrefall = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("QuizResult");
                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        String currentDate = DateFormat.format("dd-MM-yyyy",calendar.getTime()).toString();
                        //store the highest mark
                        DatabaseReference dbrefpercentage = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("RoadSafetyhighestpercentage").child(currentDate);

                        final String timestamp = "" + System.currentTimeMillis();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("type", "Road Safety Quiz");
                        hashMap.put("correct", "" + crt);
                        hashMap.put("wrong", "" + wrg);
                        hashMap.put("final", "" + percentage);
                        hashMap.put("timestamp", "" + getDateTime(timestamp));
                        dbref.push().setValue(hashMap);
                        dbrefall.push().setValue(hashMap);

                        dbrefpercentage.setValue(Integer.toString(percentage));

                        comparehighestmark(percentage,currentDate);
                    }

                    //show result
                    crtpop.setText(crt);
                    wrgpop.setText(wrg);
                    fnlpop.setText(fnl);

                    restart =  myDialog.findViewById(R.id.restartpop);
                    ok =  myDialog.findViewById(R.id.okpop);

                    correct = 0;
                    wrong = 0;
                    marks = 0;
                    restart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                            Intent in = new Intent(RoadSafetyQuiz.this, RoadSafetyQuiz.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                        }
                    });

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                            Intent in = new Intent(RoadSafetyQuiz.this, QuizActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                        }
                    });
                    myDialog.show();
                }
                rg.clearCheck();
            }
        });


    }


    //countdown
    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();

            }
        }.start();
    }

    private  void updateCountDownText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countdown.setText(timeFormatted);

        if(timeLeftInMillis < 10000){
            countdown.setTextColor(Color.RED);
        }
        else{
            countdown.setTextColor(textColorDefaultCd);
        }
    }

    public void comparehighestmark(int p, String currentDate){
        DatabaseReference dbrefpercentagecal = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("RoadSafetyhighestpercentage");
        //compare today highest mark
        //Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        //String currentDate = DateFormat.format("dd-MM-yyyy",calendar.getTime()).toString();
        dbrefpercentagecal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(currentDate)!= null){
                    String DBpercentage = snapshot.child(currentDate).getValue(String.class);
                    int curpercentage = Integer.parseInt(DBpercentage);

                    if(p>curpercentage){
                        dbrefpercentagecal.child(currentDate).setValue(Integer.toString(p));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    //change timestamp to current date and time format
    private String getDateTime(String timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        //dd=day, MM=month, yyyy=year, hh=hour, mm=minute, ss=second.
        String date = DateFormat.format("dd-MM-yyyy hh:mm aa",calendar).toString();
        return date;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    public void hints(String h){
        hintsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(RoadSafetyQuiz.this);
                builder.setTitle(R.string.quiz_hints);
                builder.setMessage(h);

                //buttons reset
                builder.setPositiveButton(R.string.quiz_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //show dialog
                builder.create().show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        correct = 0;
        wrong = 0;
        Intent i = new Intent(RoadSafetyQuiz.this,QuizActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        super.onBackPressed();
    }

}