package com.iitdh.sonusourav.instigo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.Complaints.ComplaintsActivity;
import com.iitdh.sonusourav.instigo.Council.CouncilActivity;
import com.iitdh.sonusourav.instigo.Mess.MessActivity;
import com.iitdh.sonusourav.instigo.Resources.ResourceActivity;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    private ImageButton heartRateImageBt;
    private ImageButton bloodPressureImageBt;
    private ImageButton historyImageBt;
    private ImageButton emergencyConImageBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.include_home).setVisibility(View.VISIBLE);

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

        homeInit();

        heartRateImageBt.setOnClickListener(this);
        bloodPressureImageBt.setOnClickListener(this);
        historyImageBt.setOnClickListener(this);
        emergencyConImageBt.setOnClickListener(this);


    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()){
                moveTaskToBack(true);            }
            else{
                Toast.makeText(getBaseContext(), "Press twice to exit", Toast.LENGTH_SHORT).show();
                back_pressed = System.currentTimeMillis();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return CommonFunctions.navigationItemSelect(item, this);

    }

    private void homeInit(){

        heartRateImageBt = findViewById(R.id.imagebt_hr);
        bloodPressureImageBt = findViewById(R.id.imagebt_bp);
        historyImageBt = findViewById(R.id.imagebt_his);
        emergencyConImageBt = findViewById(R.id.image_ec);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imagebt_hr:
                startActivity(new Intent(HomeActivity.this,MessActivity.class));
                break;
            case R.id.imagebt_bp:
                startActivity(new Intent(HomeActivity.this,ComplaintsActivity.class));
                break;

            case R.id.imagebt_his:
                startActivity(new Intent(HomeActivity.this,ResourceActivity.class));
                break;

            case R.id.image_ec:
                startActivity(new Intent(HomeActivity.this,CouncilActivity.class));
                break;

            default:
                    startActivity(new Intent(HomeActivity.this,MessActivity.class));

        }

    }
}
