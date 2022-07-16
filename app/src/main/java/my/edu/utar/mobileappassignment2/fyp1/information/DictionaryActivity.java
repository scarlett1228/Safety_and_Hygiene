package my.edu.utar.mobileappassignment2.fyp1.information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import my.edu.utar.mobileappassignment2.fyp1.R;

public class DictionaryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button leftbtn,rightbtn,soundbtn;
    private ImageView imageView;
    private TextView tvname,tvdescription;
    int count = 0;
    MediaPlayer mediaPlayer;
    int lang_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Integer[] images = {
                R.drawable.ic_image101,
                R.drawable.ic_image102,
                R.drawable.ic_image103,
                R.drawable.ic_image104,
                R.drawable.ic_image105,
                R.drawable.ic_image106,
                R.drawable.medicalmask,
                R.drawable.testkit,
                R.drawable.sanitizer
        };

        String[] tv ={
                getString(R.string.dictionary_traffic_light),
                getString(R.string.dictionary_caution_sign),
                getString(R.string.dictionary_speed),
                getString(R.string.dictionary_no_entry),
                getString(R.string.dictionary_turn_left),
                getString(R.string.dictionary_turn_right),
                getString(R.string.dictionary_medical_mask),
                getString(R.string.dictionary_test_kit),
                getString(R.string.dictionary_sanitizer)
        };

        String[] tvdesc ={};

        Bundle exBundle= getIntent().getExtras();
        lang_selected = exBundle.getInt("language");
        Integer[] sounds;
        if(lang_selected == 1){
            sounds = new Integer[]{
                    R.raw.trafficlightzh,
                    R.raw.cautionsignzh,
                    R.raw.speedzh,
                    R.raw.noentryzh,
                    R.raw.turnleftzh,
                    R.raw.turnrightzh,
                    R.raw.medicalmaskzh,
                    R.raw.testkitzh,
                    R.raw.sanitizerzh
            };
        }
        else if(lang_selected == 2){
            sounds = new Integer[]{
                    R.raw.trafficlightms,
                    R.raw.cautionsignms,
                    R.raw.speedms,
                    R.raw.noentryms,
                    R.raw.turnleftms,
                    R.raw.turnrightms,
                    R.raw.medicalmaskms,
                    R.raw.testkitms,
                    R.raw.sanitizerms
            };
        }
        else{
            sounds = new Integer[]{
                    R.raw.trafficlight,
                    R.raw.cautionsign,
                    R.raw.speed,
                    R.raw.noentry,
                    R.raw.turnleft,
                    R.raw.turnright,
                    R.raw.medicalmask,
                    R.raw.testkit,
                    R.raw.sanitizer
            };
        }



        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leftbtn = findViewById(R.id.leftbtn);
        rightbtn = findViewById(R.id.rightbtn);
        imageView = findViewById(R.id.imageViewDIC);
        tvname = findViewById(R.id.name);
        tvdescription = findViewById(R.id.description);
        soundbtn = findViewById(R.id.soundbtn);

        imageView.setImageResource(images[count]);
        tvname.setText(tv[count]);

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(count < 0){
                    count = 0;
                    imageView.setImageResource(images[count]);
                    tvname.setText(tv[count]);
                }
                else if(count<images.length-1){
                    imageView.setImageResource(images[count]);
                    tvname.setText(tv[count]);
                }
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count > images.length-1){
                    count = 8;
                    imageView.setImageResource(images[count]);
                    tvname.setText(tv[count]);
                }
                else if(count<=images.length-1){
                    imageView.setImageResource(images[count]);
                    tvname.setText(tv[count]);
                }
            }
        });

        soundbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(DictionaryActivity.this, sounds[count]);
                mediaPlayer.setVolume(1, 1);
                mediaPlayer.start();
            }
        });

    }




}