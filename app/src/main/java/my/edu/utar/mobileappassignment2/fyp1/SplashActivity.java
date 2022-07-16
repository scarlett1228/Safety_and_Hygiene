package my.edu.utar.mobileappassignment2.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    Animation ivanim,tvanim;
    GifImageView gifimage;
    TextView tv;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    int lang_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //each time open app alert user
        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstRun", true)
                .apply();

        //Language
        getLocal();

        //Animation
        ivanim = AnimationUtils.loadAnimation(this,R.anim.gifanim);
        tvanim = AnimationUtils.loadAnimation(this,R.anim.splashtvanim);
        //find View
        //https://www.pinterest.com/pin/775956210791670422/    gif source link
        gifimage = findViewById(R.id.gifImageView);
        tv = findViewById(R.id.splashtv);
        //set Animation
        gifimage.setAnimation(ivanim);
        tv.setAnimation(tvanim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },SPLASH_SCREEN);

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