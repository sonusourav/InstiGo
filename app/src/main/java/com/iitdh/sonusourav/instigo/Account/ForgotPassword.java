package com.iitdh.sonusourav.instigo.Account;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;

public class ForgotPassword extends AppCompatActivity {

    private EditText passwordEmail;
    private FirebaseAuth firebaseAuth;
    private TextView backToLogin;
    private PreferenceManager forgotPref;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forgot_password);

        forgotPref=new PreferenceManager(this);

        if (bundle != null) {
            onRestoreInstanceState(bundle);
        }


        Button resetPassword = findViewById(R.id.button_reset);
        passwordEmail=findViewById(R.id.forgot_email);
        backToLogin=findViewById(R.id.back_to_login);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = passwordEmail.getText().toString().trim();

                if(forgotPref.isPassUpdated()){
                    if(userEmail.equals("")){
                        Toast.makeText(ForgotPassword.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                    }else {
                        firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String errormsg=e.getLocalizedMessage();
                                Toast.makeText(ForgotPassword.this, errormsg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }else{
                    Toast.makeText(ForgotPassword.this, "Password does not exist", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));

                }

            }
        });


        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
            }
        });

    }
}
