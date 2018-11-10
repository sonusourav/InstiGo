package com.iitdh.sonusourav.instigo.Account;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.MessActivity;
import com.iitdh.sonusourav.instigo.Mess.messnotification.AlarmBootReceiver;
import com.iitdh.sonusourav.instigo.Mess.messnotification.BreakfastAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.DinnerAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.LunchAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.TiffinAlarmMaker;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;

import java.util.Objects;



public class SplashActivity extends AppCompatActivity {

    public static String TAG= SplashActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        PreferenceManager splashPrefManager = new PreferenceManager(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        android.preference.PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        BreakfastAlarmMaker.makeAlarm(getApplicationContext());
        LunchAlarmMaker.makeAlarm(getApplicationContext());
        TiffinAlarmMaker.makeAlarm(getApplicationContext());
        DinnerAlarmMaker.makeAlarm(getApplicationContext());
        ComponentName receiver = new ComponentName(getApplicationContext(), AlarmBootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        ImageView splash = findViewById(R.id.splash_iv);


            Glide.with(this)
                    .load(R.drawable.gif_splash)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .into(splash);


                if (splashPrefManager.isLoggedIn()) {
                    Log.d(TAG, "Splash:onCreate: isLoggedIn=true");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, 4600);


                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Intent mainIntent = new Intent(SplashActivity.this, IntroScreen.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, 4600);

                    Log.d(TAG, "SharedPreference: isLoggedIn=false");

                    }


    }
}
