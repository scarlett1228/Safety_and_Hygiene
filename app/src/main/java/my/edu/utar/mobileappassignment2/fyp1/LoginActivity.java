package my.edu.utar.mobileappassignment2.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class LoginActivity extends AppCompatActivity {

    private Button loginbtn,forgetbtn,newuserbtn;
    TextInputLayout email,password;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String userEnterEmail,userEnterPassword;
    private Toolbar toolbar;
    int lang_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getLocal();

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //progress dialog
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(R.string.login_please_wait);
        progressDialog.setCanceledOnTouchOutside(false);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginbtn = findViewById(R.id.loginbtn);
        forgetbtn = findViewById(R.id.forgetpasswordbtn);
        newuserbtn = findViewById(R.id.newuserbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEnterEmail = email.getEditText().getText().toString().trim();
                userEnterPassword = password.getEditText().getText().toString().trim();

                if(!validateEmail() | !validatePassword()){
                    return;
                }
                else {
                    progressDialog.setTitle(R.string.loginngin);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(userEnterEmail,userEnterPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //logged in successfully
                                    Log.d("Success", "successUserWithEmail:success");
                                    progressDialog.dismiss();

                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            //failed to login
                            progressDialog.dismiss();
                            Log.w("Fail", "successUserWithEmail:failure");
                            Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        newuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });


    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError(getString(R.string.empty_field));
            return false;
        }
        else if(!val.matches(emailPattern)){
            email.setError(getString(R.string.invalid_email));
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError(getString(R.string.empty_field));
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.login_reset_password);

        //set linear layout
        LinearLayout linearLayout=new LinearLayout(this);
        EditText resetEmail=new EditText(this);
        resetEmail.setHint(R.string.email);
        resetEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        resetEmail.setMinEms(50);

        linearLayout.addView(resetEmail);
        linearLayout.setPadding(10,30,10,30);

        builder.setView(linearLayout);

        //buttons reset
        builder.setPositiveButton(R.string.login_reset_password, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input email
                String email=resetEmail.getText().toString().trim();
                //send link to email to reset password
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, R.string.successfully_reset,
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //button cancel
        builder.setNegativeButton(R.string.login_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //show dialog
        builder.create().show();
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
