package com.iitdh.sonusourav.instigo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;
import com.iitdh.sonusourav.instigo.Complaints.ComplaintsActivity;
import com.iitdh.sonusourav.instigo.Council.CouncilActivity;
import com.iitdh.sonusourav.instigo.Feedback.Feedback;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.MessActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.CourseActivity;
import com.iitdh.sonusourav.instigo.Settings.SettingsActivity;
import com.iitdh.sonusourav.instigo.User.ProfileActivity;
import com.iitdh.sonusourav.instigo.User.RetrofitInterface;
import com.iitdh.sonusourav.instigo.User.UpdatePassword;
import com.iitdh.sonusourav.instigo.User.UserClass;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonFunctions {

    public static boolean navigationItemSelect(MenuItem item, final Activity activity) {
        int id = item.getItemId();


        switch (id) {

            case R.id.nav_home: {
                activity.startActivity(new Intent().setClass(activity, HomeActivity.class));
                break;

            }

            case R.id.nav_mess: {
                activity.startActivity(new Intent().setClass(activity, MessActivity.class));
                break;

            }

            case R.id.nav_complaint: {
                activity.startActivity(new Intent().setClass(activity, ComplaintsActivity.class));
                break;

            }

            case R.id.nav_council: {

                activity.startActivity(new Intent().setClass(activity, CouncilActivity.class));
                break;

            }

            case R.id.nav_resource: {

              activity.startActivity(new Intent().setClass(activity, CourseActivity.class));
                break;

            }


            case R.id.nav_profile: {
                activity.startActivity(new Intent().setClass(activity, ProfileActivity.class));
                break;

            }
            case R.id.nav_pass: {
                activity.startActivity(new Intent().setClass(activity, UpdatePassword.class));
                break;

            }
            case R.id.nav_logout: {
              final PreferenceManager preferenceManager =
                  new PreferenceManager(activity.getApplicationContext());

              if (preferenceManager.getAuthType().equals("googleAuth")) {
                GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(activity.getResources().getString(R.string.web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);

                googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                    Toast.makeText(activity.getApplicationContext(),
                        "User successfully logged out.", Toast.LENGTH_SHORT).show();
                    preferenceManager.setIsLoggedIn(false, "userId");
                    preferenceManager.setAuthType("authType");
                    preferenceManager.setFcmToken(null);
                    activity.startActivity(new Intent().setClass(activity, LoginActivity.class));
                    activity.finish();
                  }
                }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity.getApplicationContext(),
                        "Cannot Log Out, Please try again.", Toast.LENGTH_SHORT).show();
                  }
                });
              } else {
                Toast.makeText(activity.getApplicationContext(), "User successfully logged out.",
                    Toast.LENGTH_SHORT).show();
                preferenceManager.setLoginCredentials("email", "password", "authType");
                preferenceManager.setIsLoggedIn(false, "userId");
                preferenceManager.setFcmToken(null);
                activity.startActivity(new Intent().setClass(activity, LoginActivity.class));
                activity.finish();
              }

                break;

            }

            case R.id.nav_feedback: {
                activity.startActivity(new Intent().setClass(activity, Feedback.class));
                break;

            }

            case R.id.nav_settings: {
                activity.startActivity(new Intent().setClass(activity, SettingsActivity.class));
                break;

            }
        }

            DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

  public static void setUser(final Activity activity) {


            NavigationView navigationView = activity.findViewById(R.id.nav_view);
            LinearLayout drawerHeader = (LinearLayout) navigationView.getHeaderView(0);

    final Context context = activity.getApplicationContext();

    final TextView emailTextView = drawerHeader.findViewById(R.id.nav_header_email);
    final TextView usernameTextView = drawerHeader.findViewById(R.id.nav_header_username);
    final ImageView userImage = drawerHeader.findViewById(R.id.nav_header_imageView);

    PreferenceManager commonPref = new PreferenceManager(context);

    Log.d("CommonFunctions", commonPref.getUserId());

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build();
    RetrofitInterface retrofitInterface = new Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RetrofitInterface.class);

    Call<UserClass> call = retrofitInterface.getPicNameEmail("Bearer " + commonPref.getUserId());
    call.enqueue(new Callback<UserClass>() {
      @Override
      public void onResponse(@NonNull Call<UserClass> call, @NonNull
          Response<UserClass> response) {

        if (response.body() != null) {

          UserClass userData = new UserClass(response.body().getEmail(), response.body().getName(),
              response.body().getProfilePic());
          emailTextView.setText(userData.getEmail());
          usernameTextView.setText(userData.getName());
          if (userData.getProfilePic() != null) {
            Glide.with(activity.getApplicationContext())
                .load(userData.getProfilePic())
                .into(userImage);
          } else {
            userImage.setImageResource(R.drawable.image_profile_pic);
          }
        } else {
          Toast.makeText(context, "Check you internet connection", Toast.LENGTH_SHORT).show();
        }
      }

      @Override public void onFailure(Call<UserClass> call, Throwable t) {
        Log.d("CommonFunction", t.toString());
      }
    });

    String fcmToken = getFCMToken(context);
  }

  private static String getFCMToken(final Context context) {

    final String[] token = new String[1];
    FirebaseInstanceId.getInstance().getInstanceId()
        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
          @Override
          public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
              Log.w("CommonFunction", "getInstanceId failed", task.getException());
              return;
            }

            token[0] = Objects.requireNonNull(task.getResult()).getToken();
            Log.d("CommonFunction", "value of token " + token[0]);
            final PreferenceManager commonPref = new PreferenceManager(context);

            commonPref.setFcmToken(token[0]);
            Log.d("CommonFunction", "value of inside fcm token " + commonPref.getFcmToken());
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
            RetrofitInterface retrofitInterface = new Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitInterface.class);
            Call<ResponseClass>
                sendToken =
                retrofitInterface.sendFCMToken("Bearer " + commonPref.getUserId(), token[0]);
            sendToken.enqueue(new Callback<ResponseClass>() {
              @Override
              public void onResponse(@NonNull Call<ResponseClass> call, @NonNull
                  retrofit2.Response<ResponseClass> response) {

                if (response.body() != null) {
                  if (response.body().getMessage().equals("success")) {
                    Log.d("CommonFunction", "fcm sent");
                    commonPref.setFcmToken(token[0]);
                  } else {
                    Log.d("CommonFunction", "failed to sent fcm");
                  }
                }
              }

              @Override public void onFailure(@NonNull Call<ResponseClass> call, @NonNull
                  Throwable t) {
                Log.d("CommonFunction", t.toString());
              }
            });
          }
        });
    return token[0];
  }
}






