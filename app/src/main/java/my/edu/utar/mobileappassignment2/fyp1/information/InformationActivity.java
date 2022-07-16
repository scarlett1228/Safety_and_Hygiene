package my.edu.utar.mobileappassignment2.fyp1.information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import my.edu.utar.mobileappassignment2.fyp1.QuizActivity;
import my.edu.utar.mobileappassignment2.fyp1.R;
import my.edu.utar.mobileappassignment2.fyp1.quiz.HygieneQuiz;

public class InformationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button sopbtn,covidbtn,dicbtn,safetystatistic;
    int lang_selected;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocal();
        setContentView(R.layout.activity_information);



        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sopbtn = findViewById(R.id.sopbtn);
        covidbtn = findViewById(R.id.covidbtn);
        dicbtn = findViewById(R.id.dicbtn);
        safetystatistic = findViewById(R.id.safetystatisticbtn);

        sopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserSOP =
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.mkn.gov.my/web/ms/pelan-pemulihan-negara-fasa-4/"));
                startActivity(browserSOP);
            }
        });

        covidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserCOVID =
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://covid-19.moh.gov.my/terkini"));
                startActivity(browserCOVID);
            }
        });

        safetystatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browsersafety =
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.mot.gov.my/en/land/safety/road-accident-and-facilities"));
                startActivity(browsersafety);
            }
        });


        String l = getSharedPreferences("LanguagePreference", MODE_PRIVATE).getString("language","");
        if (l.equals("en")) {
            lang_selected = 0;
            Intent i = new Intent(InformationActivity.this, DictionaryActivity.class);
            i.putExtra("language", lang_selected);
            passIntent(i);
        } else if (l.equals("zh")) {
            lang_selected = 1;
            Intent i = new Intent(InformationActivity.this, DictionaryActivity.class);
            i.putExtra("language", lang_selected);
            passIntent(i);
        } else if(l.equals("ms")){
            lang_selected = 2;
            Intent i = new Intent(InformationActivity.this, DictionaryActivity.class);
            i.putExtra("language", lang_selected);
            passIntent(i);
        }

    }

    public void passIntent(Intent i){

        dicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
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

    /*@Override
    protected void onResume() {
        super.onResume();
        String language = getSharedPreferences("LanguagePreference", MODE_PRIVATE).getString("language", "");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }*/

}