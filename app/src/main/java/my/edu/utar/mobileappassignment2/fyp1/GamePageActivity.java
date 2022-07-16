package my.edu.utar.mobileappassignment2.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import my.edu.utar.mobileappassignment2.fyp1.game.Game2StartActivity;
import my.edu.utar.mobileappassignment2.fyp1.game.GameActivity;

public class GamePageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Button button1,button2;
    //Instruction
    Dialog InstructionDialogGame1, InstructionDialogGame2;
    Button closedialog1,closedialog2,instructionbtn1, instructionbtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.buttonGame1);
        button2 = findViewById(R.id.buttonGame2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GamePageActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GamePageActivity.this, Game2StartActivity.class);
                startActivity(intent);
            }
        });

        //Instruction Game 1
        instructionbtn1 = findViewById(R.id.instructiongame1);
        InstructionDialogGame1 = new Dialog(this);
        InstructionDialogGame1.setContentView(R.layout.instructiongame1);
        closedialog1 = InstructionDialogGame1.findViewById(R.id.closedialog1);
        instructionbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialogGame1.show();
            }
        });

        closedialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialogGame1.dismiss();
            }
        });

        //Instruction Game 2
        instructionbtn2 = findViewById(R.id.instructiongame2);
        InstructionDialogGame2 = new Dialog(this);
        InstructionDialogGame2.setContentView(R.layout.instructiongame2);
        closedialog2 = InstructionDialogGame2.findViewById(R.id.closedialog2);
        instructionbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialogGame2.show();
            }
        });

        closedialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionDialogGame2.dismiss();
            }
        });


    }
}