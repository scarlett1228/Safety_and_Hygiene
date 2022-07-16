package my.edu.utar.mobileappassignment2.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import my.edu.utar.mobileappassignment2.fyp1.QuizStatistic.StatisticsActivity;
import my.edu.utar.mobileappassignment2.fyp1.information.InformationActivity;
import my.edu.utar.mobileappassignment2.fyp1.video.Video;


public class SecondPageActivity extends AppCompatActivity {

    ImageButton ivVideo,ivQuiz, ivGame,ivinfo,ivstatistic;
    TextView tvstatistics;
    private Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivVideo = findViewById(R.id.secondvideobtn);
        ivQuiz = findViewById(R.id.secondquizbtn);
        ivGame = findViewById(R.id.secondgamebtn);
        ivinfo = findViewById(R.id.secondinfobtn);


        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondPageActivity.this, Video.class);
                startActivity(intent);
            }
        });

        ivQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondPageActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        ivGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondPageActivity.this, GamePageActivity.class);
                startActivity(intent);
            }
        });

        ivinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondPageActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });

        ivstatistic = findViewById(R.id.secondstatisticbtn);
        tvstatistics = findViewById(R.id.tvsecondstatisticbtn);
        if(mAuth.getCurrentUser()==null){
            ivstatistic.setVisibility(View.INVISIBLE);
            tvstatistics.setVisibility(View.INVISIBLE);
        }
        ivstatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecondPageActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

    }
}