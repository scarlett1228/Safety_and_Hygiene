package my.edu.utar.mobileappassignment2.fyp1.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import my.edu.utar.mobileappassignment2.fyp1.R;

public class Game2StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_start);
    }

    public void startGame(View view){
        startActivity(new Intent(getApplicationContext(),Game2Activity.class));
    }
}