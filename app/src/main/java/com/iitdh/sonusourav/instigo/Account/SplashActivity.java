package com.iitdh.sonusourav.instigo.Account;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.messnotification.AlarmBootReceiver;
import com.iitdh.sonusourav.instigo.Mess.messnotification.BreakfastAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.DinnerAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.LunchAlarmMaker;
import com.iitdh.sonusourav.instigo.Mess.messnotification.TiffinAlarmMaker;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.User.RetrofitInterface;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.ResponseClass;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

  public static String TAG = SplashActivity.class.getSimpleName();
  private Intent splashIntent;
  private PreferenceManager splashPref;
  private String loginUrl;
  private RetrofitInterface splashInterface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_splash);

    if (savedInstanceState != null) {
      onRestoreInstanceState(savedInstanceState);
    }

    splashInit();
    alarmMaker();

    if (splashPref.isLoggedIn()) {
      Log.d(TAG, "loggedIn");

      if (splashPref.getAuthType().equals("emailAuth")) {
        Log.d(TAG, "emailAuth");
        Log.d(TAG, "splash Pref fcm" + splashPref.getFcmToken());
        if (splashPref.getFcmToken() == null) {
          Log.d(TAG, "splash fcm token is null");
          String token = getFCMToken();

          Log.d(TAG, "value of token " + token);
        }
        Log.d(TAG, "val of fcmToken  " + splashPref.getFcmToken());

        loginUser(splashPref.getPrefEmail(), splashPref.getPrefPassword());
        Log.d(TAG, splashPref.getUserLevel() + "");
      } else if (splashPref.getAuthType().equals("googleAuth")) {
        Log.d(TAG, "googleAuth");
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
          Log.d(TAG, "accountNotNull");
          goToActivity(2);
          Log.d(TAG + "level not null", splashPref.getUserLevel() + "");
        } else {
          Log.d(TAG, "accountNull");
          Log.d(TAG + "level null", splashPref.getUserLevel() + "");

          goToActivity(1);
        }
      } else {
        goToActivity(1);
        Log.d(TAG, "else");
      }
    } else {
      if (splashPref.isFirstTimeLaunch()) {
        Log.d(TAG, "firstTimelaunch");
        Log.d(TAG, splashPref.getFcmToken() + "");
        goToActivity(0);
      } else {
        Log.d(TAG, "loggedOut");
        goToActivity(1);
      }
    }

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(splashIntent);
        overridePendingTransition(R.anim.fade_in, 0);
        finish();
      }
    }, 4600);
  }

  private String getFCMToken() {

    final String[] token = new String[1];
    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
          @Override
          public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
              Log.w(TAG, "getInstanceId failed", task.getException());
              return;
            }

            token[0] = Objects.requireNonNull(task.getResult()).getToken();
            Log.d(TAG, "value of token " + token[0]);
            splashPref.setFcmToken(token[0]);
            Log.d(TAG, "value of inside fcm token " + splashPref.getFcmToken());

            Call<ResponseClass>
                call = splashInterface.sendFCMToken("Bearer " + splashPref.getUserId(), token[0]);
            call.enqueue(new Callback<ResponseClass>() {
              @Override
              public void onResponse(@NonNull Call<ResponseClass> call, @NonNull
                  retrofit2.Response<ResponseClass> response) {

                if (response.body() != null) {
                  if (response.body().getMessage().equals("success")) {
                    Toast.makeText(SplashActivity.this, "fcm sent", Toast.LENGTH_SHORT).show();
                  } else {
                    Toast.makeText(SplashActivity.this, "Failed to send fcm", Toast.LENGTH_SHORT)
                        .show();
                  }
                }
              }

              @Override public void onFailure(@NonNull Call<ResponseClass> call, @NonNull
                  Throwable t) {
                Log.d(TAG, t.toString());
              }
            });
          }
        });
    return token[0];
  }

  private void goToActivity(int activity) {

    switch (activity) {
      case 0:
        splashIntent = new Intent(SplashActivity.this, IntroScreen.class);
        break;
      case 1:
        splashIntent = new Intent(SplashActivity.this, LoginActivity.class);
        break;
      case 2:
        splashIntent = new Intent(SplashActivity.this, HomeActivity.class);
        break;
      default:
        splashIntent = new Intent(SplashActivity.this, LoginActivity.class);
        break;
    }
  }

  private void loginUser(String email, String password) {

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("email", email);
      jsonObject.put("password", password);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              if (response.getString("message").equals("success")) {
                String userId = response.getString("userId");
                long passLastUpdated = response.getLong("passLastUpdated");

                splashPref.setUserLevel(response.getInt("level"));
                Log.d(TAG, response.getInt("level") + "");
                splashPref.setIsLoggedIn(true, userId);
                splashPref.setPassLastUpdated(passLastUpdated);
                goToActivity(2);
              } else if (response.get("message").toString().substring(0, 7).equals("failure")) {
                String errorMsg = response.getString("message").substring(8);
                Toast.makeText(SplashActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Else_If_Error: " + response.toString());
                goToActivity(1);
              } else {
                Log.d(TAG, "Bad UploadObject");
              }
            } catch (JSONException e) {
              e.printStackTrace();
              Log.d(TAG, "catchError: " + e.getMessage());
              goToActivity(1);
            }
          }
        },
        new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError volleyError) {
            new VolleyErrorInstances().getErrorType(getApplicationContext(), volleyError);
            Log.d(TAG, "VolleyError: " + volleyError.toString());
            goToActivity(1);
          }
        }) {
      @Override
      public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
      }
    };

    AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
  }

  private void splashInit() {
    splashPref = new PreferenceManager(this);
    ImageView splash = findViewById(R.id.splash_iv);
    loginUrl = Constants.baseUrl + "users/signin";

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build();
    splashInterface = new Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RetrofitInterface.class);
    Glide.with(this)
        .load(R.drawable.gif_splash)
        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
        .into(splash);
  }

  public void alarmMaker() {
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
  }
}
