package my.edu.utar.mobileappassignment2.fyp1.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Locale;

import my.edu.utar.mobileappassignment2.fyp1.MainActivity;
import my.edu.utar.mobileappassignment2.fyp1.MusicService;
import my.edu.utar.mobileappassignment2.fyp1.R;
import my.edu.utar.mobileappassignment2.fyp1.SettingsActivity;


public class Video extends AppCompatActivity {

    Button btn1,btn2,btn3;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Language
        getLocal();
        setContentView(R.layout.activity_video);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        //Remind user turn off music before go in video page
        AlertDialog.Builder builder=new AlertDialog.Builder(Video.this);
        builder.setTitle(R.string.video_reminder);
        builder.setMessage(R.string.video_reminder_message);
        //direct to setting
        builder.setPositiveButton(R.string.video_go_setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Video.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        //close alert box
        builder.setNegativeButton(R.string.video_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //if turn on music, pop alert
        if(MusicService.isCheck() == true){
            //show dialog
            builder.create().show();
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Video.this, HygieneVideoActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Video.this, RoadSafetyVideoActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Video.this, HomeSafetyVideoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setLocal(String lang){
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        getSharedPreferences("LanguagePreference", MODE_PRIVATE).edit().putString("language", lang).apply();
    }

    public void getLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "");
        setLocal(language);
    }
}