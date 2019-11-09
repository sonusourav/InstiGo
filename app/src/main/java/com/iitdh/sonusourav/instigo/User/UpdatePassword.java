package com.iitdh.sonusourav.instigo.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePassword extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private EditText newPassword;
    private PreferenceManager updatePref;
    private ProgressBar updateProgressBar;
    private Button updateButton;
    private String TAG=UpdatePassword.class.getSimpleName();
  private RetrofitInterface retrofitInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        CommonFunctions.setUser(this);

        findViewById(R.id.include_update_pass).setVisibility(View.VISIBLE);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

      androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle =
          new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
              R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        updatePassInit();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProgressBar.setVisibility(View.VISIBLE);
                updateButton.setEnabled(false);
              final String newPass = newPassword.getText().toString().trim();

              if (newPass.isEmpty()) {

                updateProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Fill the password",
                    Toast.LENGTH_SHORT).show();
                updateButton.setEnabled(true);
              } else if (newPass.length() < 7) {
                updateProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                    "Password should not contain less than 6 characters .", Toast.LENGTH_SHORT)
                    .show();
                updateButton.setEnabled(true);
              } else {

                Call<ResponseBody> call =
                    retrofitInterface.updatepassword("Bearer " + updatePref.getUserId(), newPass);
                call.enqueue(new Callback<ResponseBody>() {
                  @Override
                  public void onResponse(@NonNull Call<ResponseBody> call, @NonNull
                      Response<ResponseBody> response) {

                    if (response.code() == 200) {
                      Toast.makeText(UpdatePassword.this, "Password successfully Updated",
                          Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(UpdatePassword.this, HomeActivity.class));
                    } else {
                      Toast.makeText(UpdatePassword.this, "Failed to update password",
                          Toast.LENGTH_SHORT).show();
                    }
                  }

                  @Override public void onFailure(@NonNull Call<ResponseBody> call, @NonNull
                      Throwable t) {
                    Log.d(TAG, t.toString());
                  }
                });


                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateButton.setEnabled(true);
    }

    private void updatePassInit(){
        updateButton = findViewById(R.id.btnUpdatePassword);
        newPassword = findViewById(R.id.etNewPassword);
        updatePref = new PreferenceManager(this);
        updateProgressBar=findViewById(R.id.update_progress_bar);

      OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
          .connectTimeout(60, TimeUnit.SECONDS)
          .readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(120, TimeUnit.SECONDS)
          .build();
      retrofitInterface = new Retrofit.Builder()
          .baseUrl(Constants.baseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build()
          .create(RetrofitInterface.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return CommonFunctions.navigationItemSelect(item, this);
    }
}
