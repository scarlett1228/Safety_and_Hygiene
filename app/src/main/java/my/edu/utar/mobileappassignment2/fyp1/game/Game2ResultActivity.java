package my.edu.utar.mobileappassignment2.fyp1.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import my.edu.utar.mobileappassignment2.fyp1.GamePageActivity;
import my.edu.utar.mobileappassignment2.fyp1.R;
import my.edu.utar.mobileappassignment2.fyp1.SecondPageActivity;


public class Game2ResultActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView scoreLabel,highScoreLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_result);

        scoreLabel = findViewById(R.id.scoreLable);
        highScoreLabel = findViewById(R.id.highScoreLabel);


        if (mAuth.getCurrentUser() != null) {
            //loadscore();
            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game2Result");
            //get all details
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String game2score = snapshot.child("score").getValue(String.class);
                        int score = Integer.parseInt(game2score);
                        scoreLabel.setText(score + "");

                        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
                        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

                        if(score > highScore){
                            //Update HighScore
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("HIGH_SCORE", score);
                            editor.apply();

                            highScoreLabel.setText(getString(R.string.game2_highest_score) + score);

                        } else {
                            highScoreLabel.setText(getString(R.string.game2_highest_score) + highScore);
                        }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
        }
        else{
            int score = getIntent().getIntExtra("SCORE",0);
            scoreLabel.setText(score + "");

            SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
            int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

            if(score > highScore){
                //Update HighScore
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("HIGH_SCORE", score);
                editor.apply();

                highScoreLabel.setText(getString(R.string.game2_highest_score)+ score);

            } else {
                highScoreLabel.setText(getString(R.string.game2_highest_score) + highScore);
            }
        }

    }

    @Override
    public void onBackPressed() {

    }

    public  void  tryAgain(View view){
        //set flags clear top to let the user directly back to second page after click try again and play
        //.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(new Intent(getApplicationContext(),Game2Activity.class));
    }

    public  void  backsecondpage(View view){
        startActivity(new Intent(getApplicationContext(), GamePageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

}