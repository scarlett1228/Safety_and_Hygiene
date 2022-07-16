package my.edu.utar.mobileappassignment2.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button logresbtn;
    ImageButton info;
    TextView tvemail;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    public Intent alarmIntent;
    public PendingIntent pendingIntent;
    public AlarmManager manager;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocal();

        //first run pop out
        checkFirstRun();

        //Music
        if(MusicService.isRunning() == false){
            startService(new Intent(this, MusicService.class));
        }


        //Notification
        createNotificationChannel();

        //Alarm
      boolean isFirstAlarm = getSharedPreferences("SHAREPREFERENCE", MODE_PRIVATE).getBoolean("isFirstAlarm", true);
      if (isFirstAlarm){
          //https://developer.android.com/training/scheduling/alarms
          getAlarm();

         //int interval = 60000; // 60000 = 1 min need to change 15 minute = 900000/10*60*15

          manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                  SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                  AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
          //AlarmManager.INTERVAL_FIFTEEN_MINUTES

          getSharedPreferences("SHAREPREFERENCE", MODE_PRIVATE)
                  .edit()
                  .putBoolean("isFirstAlarm", false)
                  .apply();
      }


         logresbtn = findViewById(R.id.logresbtn);
         info = findViewById(R.id.FYI);
         tvemail = findViewById(R.id.tvemail);

        //if login, no log reg button
      if (mAuth.getCurrentUser() != null) {
          logresbtn.setVisibility(View.INVISIBLE);
          String email= mAuth.getCurrentUser().getEmail();
          tvemail.setText(email);

      }

      logresbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act3 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(act3);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.main_for_your_info);

                //buttons reset
                builder.setPositiveButton(R.string.main_ok, new DialogInterface.OnClickListener() {
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


    public void launchsettings(View v){
        //launch settings
        Intent act1 = new Intent(this,SettingsActivity.class);
        startActivity(act1);
    }

    public void launchstart(View v) {
        //launch secondpage
        if (mAuth.getCurrentUser() != null) {
            Intent act2 = new Intent(MainActivity.this, SecondPageActivity.class);
            act2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(act2);
        }
        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(R.string.main_start_without_log_in);
            builder.setMessage(R.string.main_alert_no_log_in);

            //buttons reset
            builder.setPositiveButton(R.string.main_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent act2 = new Intent(MainActivity.this, SecondPageActivity.class);
                    act2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(act2);
                }
            });

            //button cancel
            builder.setNegativeButton(R.string.main_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //show dialog
            builder.create().show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences("SHAREPREFERENCE", MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstAlarm", true)
                .apply();
        getAlarm();
        manager.cancel(pendingIntent);
    }


    //exit when click backpress
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.main_confirm_exit);
        builder.setMessage(R.string.main_confirm_exit_message);

        //buttons yes
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSharedPreferences("SHAREPREFERENCE", MODE_PRIVATE)
                        .edit()
                        .putBoolean("isFirstAlarm", true)
                        .apply();
                getAlarm();
                manager.cancel(pendingIntent);
                finishAffinity();
                System.exit(0);
            }
        });

        //button cancel
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //show dialog
        builder.create().show();
        getSharedPreferences("SHAREPREFERENCE", MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstAlarm", true)
                .apply();
        getAlarm();
        manager.cancel(pendingIntent);

    }

    //first run pop out
    //https://stackoverflow.com/questions/7562786/android-first-run-popup-dialog
    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(R.string.main_alert_internet);
            builder.setMessage(R.string.main_alert_internet_message);

            //buttons reset
            builder.setPositiveButton(R.string.main_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            //show dialog
            builder.create().show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }

    }



    //Notification for rest
    //https://www.youtube.com/watch?v=nl-dheVpt8o&t=100s
    public void createNotificationChannel(){
        CharSequence name = getString(R.string.notification_name);
        String description = getString(R.string.notification_desc);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("Notification", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void getAlarm(){
        alarmIntent = new Intent(this, MyPeriodicNotification.class);
        alarmIntent.putExtra("name", getString(R.string.notification_name));
        alarmIntent.putExtra("message", getString(R.string.notification_message));
        pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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

}