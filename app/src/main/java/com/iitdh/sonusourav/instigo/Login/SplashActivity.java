package com.iitdh.sonusourav.instigo.Login;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Sample.WelcomeActivity;
import com.iitdh.sonusourav.instigo.TestActivity;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash;
    private PreferenceManager splashPrefManager;
    public static String TAG= "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        splashPrefManager = new PreferenceManager(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        splash = findViewById(R.id.splash_iv);

        if (splash != null) {
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.gif_splash)
                    .into(splash)
            ;
        }


        if (splashPrefManager.isLoggedIn()) {
            Log.d(TAG, "Splash:onCreate: isLoggedIn=true");
            String splashEmail = splashPrefManager.getPrefEmail();
            String splashPassword = splashPrefManager.getPrefPassword();


            assert splashEmail != null;
            assert splashPassword != null;

            (firebaseAuth.signInWithEmailAndPassword(splashEmail, splashPassword))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    }
                                }, 4800);


                            } else {
                                Log.e("SharedPreference Error", task.getException().toString());
                                Log.d(TAG, "SharedPreference: isLoggedIn=false");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Intent mainIntent = new Intent(SplashActivity.this, IntroScreen.class);
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    }
                                }, 4800);
                                Toast.makeText(SplashActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(SplashActivity.this, IntroScreen.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, 4800);

            Log.d(TAG, "SharedPreference: isLoggedIn=false");

        }
    }
}
