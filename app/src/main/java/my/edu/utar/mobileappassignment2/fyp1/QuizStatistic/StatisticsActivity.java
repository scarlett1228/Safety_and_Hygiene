package my.edu.utar.mobileappassignment2.fyp1.QuizStatistic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import my.edu.utar.mobileappassignment2.fyp1.R;
import my.edu.utar.mobileappassignment2.fyp1.SecondPageActivity;

//Refer https://github.com/learntodroid/BarChartExamples
public class StatisticsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference dbref;
    int[] y = new int[7];
    Button hygienebtn,roadbtn,homebtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hygienebtn = findViewById(R.id.hygienebarbutton);
        roadbtn = findViewById(R.id.roadbarbutton);
        homebtn = findViewById(R.id.homebarbutton);
        if(mAuth.getCurrentUser() != null) {
            hygieneBar();
            roadBar();
            homeBar();
        }

    }

    public void  hygieneBar(){
        dbref= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Hygienehighestpercentage");

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        //compare today highest mark
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0; i<7;i++){
                    if(snapshot.child(sdf.format(cal.getTime())).exists()){
                        String DBpercentage = snapshot.child(sdf.format(cal.getTime())).getValue(String.class);
                        y[i] = Integer.parseInt(DBpercentage);

                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    else{
                        y[i] = 0;
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                }
                Intent intent=new Intent(StatisticsActivity.this, HygieneStatisticActivity.class);
                intent.putExtra("y1",y[0]);
                intent.putExtra("y2",y[1]);
                intent.putExtra("y3",y[2]);
                intent.putExtra("y4",y[3]);
                intent.putExtra("y5",y[4]);
                intent.putExtra("y6",y[5]);
                intent.putExtra("y7",y[6]);
                hygienepassIntent(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }


    public void hygienepassIntent(Intent intent){

        hygienebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void  roadBar(){
        dbref= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("RoadSafetyhighestpercentage");

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        //compare today highest mark
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0; i<7;i++){
                    if(snapshot.child(sdf.format(cal.getTime())).exists()){
                        String DBpercentage = snapshot.child(sdf.format(cal.getTime())).getValue(String.class);
                        y[i] = Integer.parseInt(DBpercentage);

                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    else{
                        y[i] = 0;
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                }
                Intent intent=new Intent(StatisticsActivity.this, RoadStatisticActivity.class);
                intent.putExtra("y1",y[0]);
                intent.putExtra("y2",y[1]);
                intent.putExtra("y3",y[2]);
                intent.putExtra("y4",y[3]);
                intent.putExtra("y5",y[4]);
                intent.putExtra("y6",y[5]);
                intent.putExtra("y7",y[6]);
                roadpassIntent(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    public void roadpassIntent(Intent intent){

        roadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void  homeBar(){
        dbref= FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("HomeSafetyhighestpercentage");

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        //compare today highest mark
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0; i<7;i++){
                    if(snapshot.child(sdf.format(cal.getTime())).exists()){
                        String DBpercentage = snapshot.child(sdf.format(cal.getTime())).getValue(String.class);
                        y[i] = Integer.parseInt(DBpercentage);

                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    else{
                        y[i] = 0;
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                }
                Intent intent=new Intent(StatisticsActivity.this, HomeStatisticActivity.class);
                intent.putExtra("y1",y[0]);
                intent.putExtra("y2",y[1]);
                intent.putExtra("y3",y[2]);
                intent.putExtra("y4",y[3]);
                intent.putExtra("y5",y[4]);
                intent.putExtra("y6",y[5]);
                intent.putExtra("y7",y[6]);
                homepassIntent(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    public void homepassIntent(Intent intent){

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

}