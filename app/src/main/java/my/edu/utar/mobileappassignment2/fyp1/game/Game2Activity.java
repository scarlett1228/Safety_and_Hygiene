package my.edu.utar.mobileappassignment2.fyp1.game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Timer;
import java.util.TimerTask;

import my.edu.utar.mobileappassignment2.fyp1.GamePageActivity;
import my.edu.utar.mobileappassignment2.fyp1.R;
import my.edu.utar.mobileappassignment2.fyp1.SecondPageActivity;

//44.56
//Refer to https://www.youtube.com/watch?v=FBJFuEsf2ts
public class Game2Activity extends AppCompatActivity {

    //Elements
    private TextView scoreLabel,startLabel;
    private ImageView people,mask,sanitizer,virus;

    //Size
    private  int screenHeight, screenWidth;
    private  int frameHeight;
    private  int peopleSize;

    //Position
    private float peopleY;
    private float maskX,maskY;
    private float sanitizerX, sanitizerY;
    private float virusX, virusY;

    //Score
    private  int score = 0;

    //Timer
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //Status
    private boolean action_flg = false; //to check whether user tapped screen(true)
    private boolean start_flg = false; //to check whether game started(true)

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //backpress
    boolean back = false;

    //Gender
    private ImageButton boybtn,girlbtn;
    private Dialog GenderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        scoreLabel = findViewById(R.id.scoreLable);
        startLabel = findViewById(R.id.startlable);
        people = findViewById(R.id.gamepeople);
        mask = findViewById(R.id.gamemask);
        sanitizer = findViewById(R.id.gamesanitizer);
        virus = findViewById(R.id.gamevirus);

        // Screen Size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Initial Position
        mask.setX(-80.0f);
        mask.setY(-80.0f);
        sanitizer.setX(-80.0f);
        sanitizer.setY(-80.0f);
        virus.setX(-80.0f);
        virus.setY(-80.0f);

        scoreLabel.setText(getString(R.string.game2_score)+ score);

        //Choose Gendera
        GenderDialog = new Dialog(this);
        GenderDialog.setContentView(R.layout.gendergame2);
        GenderDialog.setCanceledOnTouchOutside(false);
        boybtn= GenderDialog.findViewById(R.id.boykids);
        girlbtn= GenderDialog.findViewById(R.id.girlkids);
        GenderDialog.show();
        boybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people.setImageResource(R.drawable.boykidswithmask);
                GenderDialog.dismiss();
            }
        });

        girlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people.setImageResource(R.drawable.girlkidswithmask);
                GenderDialog.dismiss();
            }
        });

    }

    public void changePos(){

        hitCheck();

        //Mask
        maskX -= 12;
        if(maskX < 0){
            maskX = screenWidth + 20;
            maskY = (float)Math.floor(Math.random()* (frameHeight - mask.getHeight()));
        }
        mask.setX(maskX);
        mask.setY(maskY);


        //Virus
        virusX -= 16;
        if(virusX < 0){
            virusX = screenWidth + 10;
            virusY = (float)Math.floor(Math.random()* (frameHeight - virus.getHeight()));
        }
        virus.setX(virusX);
        virus.setY(virusY);


        //Sanitizer
        sanitizerX -=20;
        if(sanitizerX < 0){
            sanitizerX = screenWidth + 4000;
            sanitizerY = (float)Math.floor(Math.random()* (frameHeight - sanitizer.getHeight()));
        }
        sanitizer.setX(sanitizerX);
        sanitizer.setY(sanitizerY);


        //People
        if(action_flg){
            //Touching
            peopleY -= 20; // when touch the people willl go up
        } else{
            //Releasing
            peopleY += 20;
        }


        //avoid people out the frame
        if(peopleY < 0) peopleY = 0;
        if(peopleY > frameHeight - peopleSize) peopleY = frameHeight - peopleSize;

        people.setY(peopleY);

        scoreLabel.setText(getString(R.string.game2_score)+ score);
    }

    public void hitCheck(){

        //Mask
        float maskCenterX = maskX + mask.getWidth() / 2.0f;
        float maskCenterY = maskY + mask.getHeight() / 2.0f;

        if(0 <= maskCenterX && maskCenterX <= peopleSize &&
                peopleY <= maskCenterY && maskCenterY <= peopleY + peopleSize){
            maskX = -100.0f;
            score += 10;
        }


        //Sanitizer
        float sanitizerCenterX = sanitizerX + mask.getWidth() / 2.0f;
        float sanitizerCenterY = sanitizerY + mask.getHeight() / 2.0f;

        if(0 <= sanitizerCenterX && sanitizerCenterX <= peopleSize &&
                peopleY <= sanitizerCenterY && sanitizerCenterY <= peopleY + peopleSize){
            sanitizerX = -100.0f;
            score += 30;
        }


        //Virus
        float virusCenterX = virusX + virus.getWidth() / 2.0f;
        float virusCenterY = virusY + virus.getHeight() / 2.0f;

        if(0 <= virusCenterX && virusCenterX <= peopleSize &&
                peopleY <= virusCenterY && virusCenterY <= peopleY + peopleSize){
            //Game Over!!
            ////////////will  try to set with three life
            if(timer != null){
                timer.cancel();
                timer = null;
            }

            //Show ResultActivity
            if (mAuth.getCurrentUser() != null) {
                //store DB
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game2Result");
                dbref.child("score").setValue(Integer.toString(score));
            }

            if(back==false) {
                Intent intent = new Intent(getApplicationContext(), Game2ResultActivity.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }



        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(!start_flg){
            start_flg = true;

            //FrameHeight
            FrameLayout frameLayout = findViewById(R.id.frame);
            frameHeight = frameLayout.getHeight();

            //People
            peopleY = people.getY();
            peopleSize = people.getHeight();

            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return super.onTouchEvent(event);
    }

    public void onBackPressed() {
        back = true;
        timer.cancel();
        timer = null;
        finish();
        super.onBackPressed();

    }
}