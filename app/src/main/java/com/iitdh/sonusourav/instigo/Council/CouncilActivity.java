package com.iitdh.sonusourav.instigo.Council;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.User.RetrofitInterface;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CouncilActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private ArrayList<CouncilTeam> teamList;
  private RetrofitInterface retrofitInterface;
  private CouncilAdapter adapter;
  private String TAG = CouncilActivity.class.getSimpleName();
  private ProgressDialog councilProgressDialog;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.include_council).setVisibility(View.VISIBLE);

        CommonFunctions.setUser(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        councilInit();
    showProgressDialog();
    getCouncilTeam();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        startActivity(new Intent(CouncilActivity.this, HomeActivity.class));

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return CommonFunctions.navigationItemSelect(item, this);

    }

  private void getCouncilTeam() {
    Call<ArrayList<CouncilTeam>> call = retrofitInterface.getTeams();
    call.enqueue(new Callback<ArrayList<CouncilTeam>>() {
      @Override
      public void onResponse(@NonNull Call<ArrayList<CouncilTeam>> call,
          @NonNull Response<ArrayList<CouncilTeam>> response) {

        hideProgressDialog();
        if (response.body() != null) {

          ArrayList<CouncilTeam> councilTeams = response.body();
          for (CouncilTeam councilTeam : councilTeams) {
            Log.v(TAG, councilTeam.getTeamName());
            teamList.add(councilTeam);
            adapter.notifyDataSetChanged();
          }
          Log.d(TAG, response.toString());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ArrayList<CouncilTeam>> call, @NonNull Throwable t) {
        hideProgressDialog();
        Toast.makeText(CouncilActivity.this, "Something went wrong...Please try later!",
            Toast.LENGTH_SHORT).show();
        Log.d(TAG, t.toString());
        Log.d(TAG, call.toString());
      }
    });
  }


    private void councilInit(){
      RecyclerView recyclerView = findViewById(R.id.council_recycler_view);
      teamList = new ArrayList<>();
      adapter = new CouncilAdapter(this, teamList);

      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(adapter);

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

  public void showProgressDialog() {

    if (councilProgressDialog == null) {
      councilProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      councilProgressDialog.setMessage("Fetching details....");
      councilProgressDialog.setIndeterminate(true);
      councilProgressDialog.setCanceledOnTouchOutside(false);
    }

    councilProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (councilProgressDialog != null && councilProgressDialog.isShowing()) {
      councilProgressDialog.dismiss();
    }
  }
}
