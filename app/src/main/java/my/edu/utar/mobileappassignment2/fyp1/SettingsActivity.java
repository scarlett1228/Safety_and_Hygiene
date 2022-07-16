package my.edu.utar.mobileappassignment2.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button logout,startmusic,stopmusic;
    private TextView tvlogout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;

    //Change language
    int lang_selected;
    Button showlandialog;
    TextView tvlanguage;

    //https://www.youtube.com/watch?v=xeYPEswJ5hs
    //https://stackoverflow.com/questions/49423451/how-to-change-the-whole-application-language

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Language
        getLocal();

        //progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(R.string.setting_loading);
        progressDialog.setProgress(100);
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);

        //Music
        startmusic = findViewById(R.id.buttononMusic);
        stopmusic = findViewById(R.id.buttonoffMusic);
        startmusic.setOnClickListener(this);
        stopmusic.setOnClickListener(this);
        if (MusicService.isCheck() == false) {
            startmusic.setEnabled(true);
            stopmusic.setEnabled(false);
        } else {
            stopmusic.setEnabled(true);
            startmusic.setEnabled(false);
        }

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logout = findViewById(R.id.logout);
        tvlogout = findViewById(R.id.textViewlogout);
        if (mAuth.getCurrentUser() == null) {
            logout.setVisibility(View.INVISIBLE);
            tvlogout.setVisibility(View.INVISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage(R.string.sure_to_log_out).setCancelable(false)
                        .setPositiveButton(R.string.setting_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                Toast.makeText(getApplicationContext(), R.string.sign_out_successfully, Toast.LENGTH_SHORT).show();
                                finishAndRemoveTask();
                                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                builder.setNegativeButton(R.string.setting_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        //Change Language
        showlandialog = findViewById(R.id.changelanguagebtn);

            showlandialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String[] Language = {"ENGLISH", "中文", "Bahasa"};
                    Log.d("Clicked", "Clicked");
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                    dialogBuilder.setTitle(R.string.select_language)
                            .setSingleChoiceItems(Language,-1 , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if ( i == 0 ) {
                                        new MyTask().execute();
                                        setLocal("en");
                                        recreate();
                                    }
                                    else if (i == 1) {
                                        new MyTask().execute();
                                        setLocal("zh");
                                        recreate();
                                    }
                                    if(i == 2) {
                                        new MyTask().execute();
                                        setLocal("ms");
                                        recreate();
                                     }
                                    dialogInterface.dismiss();
                                }
                            });
                    dialogBuilder.create().show();
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

    @Override
    protected void onResume() {
        super.onResume();
        String language = getSharedPreferences("LanguagePreference", MODE_PRIVATE).getString("language", "");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    //Music
    @Override
    public void onClick(View v) {
        if(v == startmusic){
            startService(new Intent(this, MusicService.class));
            stopmusic.setEnabled(true);
            startmusic.setEnabled(false);


        }else if(v == stopmusic){
            stopService(new Intent(this,MusicService.class));
            startmusic.setEnabled(true);
            stopmusic.setEnabled(false);

        }
    }

    //show progress bar
    public class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {
            progressDialog.show();

        }
        public Void doInBackground(Void... unused) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}