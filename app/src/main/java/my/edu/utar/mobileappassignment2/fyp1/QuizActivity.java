package my.edu.utar.mobileappassignment2.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import my.edu.utar.mobileappassignment2.fyp1.information.InformationActivity;
import my.edu.utar.mobileappassignment2.fyp1.quiz.HomeSafetyQuiz;
import my.edu.utar.mobileappassignment2.fyp1.quiz.HygieneQuiz;
import my.edu.utar.mobileappassignment2.fyp1.quiz.Result;
import my.edu.utar.mobileappassignment2.fyp1.quiz.RoadSafetyQuiz;

public class QuizActivity extends AppCompatActivity {

    Button iv1, iv2,iv3;
    Button btnresult;
    private Toolbar toolbar;
    //Instruction
    Button instructionbtn;
    Dialog InstructionDialog;
    Button closedialog;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv1 = findViewById(R.id.secondhygienebtn);
        iv2 = findViewById(R.id.secondsafetybtn);
        iv3 = findViewById(R.id.secondhomesafetybtn);
        btnresult = findViewById(R.id.resultbutton);

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizActivity.this, HygieneQuiz.class);
                startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizActivity.this, RoadSafetyQuiz.class);
                startActivity(intent);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizActivity.this, HomeSafetyQuiz.class);
                startActivity(intent);
            }
        });

        if(mAuth.getCurrentUser()==null){
            btnresult.setVisibility(View.INVISIBLE);
        }
        btnresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizActivity.this, Result.class);
                startActivity(intent);
            }
        });

        //Instruction
        instructionbtn = findViewById(R.id.teachingbutton);
        InstructionDialog = new Dialog(this);
        InstructionDialog.setContentView(R.layout.instructionquiz);
        closedialog = InstructionDialog.findViewById(R.id.closedialog);
        instructionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialog.show();
            }
        });

        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialog.dismiss();
            }
        });
    }
}