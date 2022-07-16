package my.edu.utar.mobileappassignment2.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

//https://www.youtube.com/watch?v=wa8OrQ_e76M
public class RegisterActivity extends AppCompatActivity {

    TextInputLayout regEmail, regPassword;
    Button regBtn, HaveAccBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference dbref;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbref = FirebaseDatabase.getInstance().getReference("Users");

        getLocal();

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(R.string.login_please_wait);
        progressDialog.setCanceledOnTouchOutside(false);

        //Set all view to variables
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.signupbtn);
        HaveAccBtn = findViewById(R.id.haveaccount);




        HaveAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError(getString(R.string.empty_field));
            return false;
        }
        else if(!val.matches(emailPattern)){
            regEmail.setError(getString(R.string.invalid_email));
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }



    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z0-9._-])" +     //any letter
                //"(?=.*[@#$%^&+=])" +   //at least 1 special character
                "(?=\\S+$)" +          //no white space
                ".{6,}" +             //at least 6 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError(getString(R.string.empty_field));
            return false;
        } else if(!val.matches(passwordVal)){
            regPassword.setError(getString(R.string.password_set_error));
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    //Save data in Firebase with Button Click
    public void registerUser(View view) {

        //if anyone false it will return
        if(!validatePassword() |  !validateEmail() ){
            return;
        }
        else{
            progressDialog.setTitle(R.string.loginngin);
            progressDialog.show();
            //Get  all value
            String email = regEmail.getEditText().getText().toString();
            String password = regPassword.getEditText().getText().toString();

            //https://stackoverflow.com/questions/40093781/check-if-given-email-exists
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if(user != null) {
                                    final String uid = user.getUid();
                                    Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();

                                    //put value inside Real Time DB
                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("UID",""+ uid);
                                    hashMap.put("email",""+email);
                                    dbref.child(uid).setValue(hashMap);
                                }
                                progressDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                //progressDialog.dismiss();
                                Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, R.string.register_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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