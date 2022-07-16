package my.edu.utar.mobileappassignment2.fyp1.video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Vector;

import my.edu.utar.mobileappassignment2.fyp1.R;

public class HomeSafetyVideoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout linearLayout;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    private Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    int lang_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Language
        getLocal();
        setContentView(R.layout.activity_home_safety_video);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        linearLayout = findViewById(R.id.linearlayout);

        //https://www.youtube.com/watch?v=bSMZknDI6bg
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/CqH2QYt6oOc\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/MohSIWrA-G8\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vPFYzCYF9ck\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/r4w7ub5RJeA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5EnPKvUSUEs\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/nAM9Noxp6_Y\" frameborder=\"0\" allowfullscreen></iframe>") );


        if (youtubeVideos != null) {
            VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos, linearLayout, recyclerView);
            recyclerView.setAdapter(videoAdapter);
        }
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