package com.iitdh.sonusourav.instigo.Account;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.messnotification.AlarmBootReceiver;
import com.iitdh.sonusourav.instigo.Mess.messnotification.BreakfastAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.DinnerAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.LunchAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.TiffinAlarmMaker;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;



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
        FirebaseApp.initializeApp(getApplicationContext());
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

        createNotificationChannel();

        if (splashPrefManager.isLoggedIn()) {
            Log.d(TAG, "Splash:onCreate: isLoggedIn=true");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.fade_in, 0);
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getString(R.string.alarm_notification_channel_id), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
