package my.edu.utar.mobileappassignment2.fyp1.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import my.edu.utar.mobileappassignment2.fyp1.R;


public class Result extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView resultRv;
    private ArrayList<ResultHelperClass> resultList;
    private Adapter adapter;
    private Toolbar toolbar;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultRv = findViewById(R.id.ResultRV);

        if (mAuth.getCurrentUser() != null) {
            loadAllResult();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quizresultmenu,menu);
        return true;
    }

    //select specific result
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (mAuth.getCurrentUser() != null) {
            switch (id) {
                case R.id.hygienequizresult:
                    dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("HygieneQuizResult");
                    loadResult();
                    break;
                case R.id.roadsafetyquizresult:
                    dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("RoadSafetyQuizResult");
                    loadResult();
                    break;
                case R.id.homesafetyquizresult:
                    dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("HomeSafetyQuizResult");
                    loadResult();
                    break;
                case R.id.allresult:
                    dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("QuizResult");
                    loadResult();
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadResult() {
        resultList = new ArrayList<>();

        //get all details
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                resultList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ResultHelperClass modelResult = ds.getValue(ResultHelperClass.class);

                    if(modelResult.type.equals("Hygiene Quiz")){
                        modelResult.setType(getString(R.string.quiz_hygiene));
                    }
                    else if(modelResult.type.equals("Road Safety Quiz")){
                        modelResult.setType(getString(R.string.quiz_road));
                    }
                    else if(modelResult.type.equals("Home Safety Quiz")){
                        modelResult.setType(getString(R.string.quiz_home));
                    }

                    resultList.add(modelResult);

                }
                //setup adapter
                adapter = new Adapter(Result.this, resultList);
                //set adapter
                resultRv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }

    private void loadAllResult() {
        resultList = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("QuizResult");
        //get all details
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                resultList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ResultHelperClass modelResult = ds.getValue(ResultHelperClass.class);

                    if(modelResult.type.equals("Hygiene Quiz")){
                        modelResult.setType(getString(R.string.quiz_hygiene));
                    }
                    else if(modelResult.type.equals("Road Safety Quiz")){
                        modelResult.setType(getString(R.string.quiz_road));
                    }
                    else if(modelResult.type.equals("Home Safety Quiz")){
                        modelResult.setType(getString(R.string.quiz_home));
                    }

                    resultList.add(modelResult);

                }
                //setup adapter
                adapter = new Adapter(Result.this, resultList);
                //set adapter
                resultRv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }
}