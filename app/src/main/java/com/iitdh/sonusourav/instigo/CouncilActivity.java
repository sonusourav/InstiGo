package com.iitdh.sonusourav.instigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.Council.CouncilCulturalSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilEmergency;
import com.iitdh.sonusourav.instigo.Council.CouncilFHosMainSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilFHostelSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilFMessSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilFSportsSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilGenSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilSportsSecy;
import com.iitdh.sonusourav.instigo.Council.CouncilWarden;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.Objects;


public class CouncilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private LinearLayout wardens;
    private LinearLayout genSecy;
    private LinearLayout sportsSecy;
    private LinearLayout culSecy;
    private LinearLayout FrHostelSecy;
    private LinearLayout FrHosMainSecy;
    private LinearLayout FrMessSecy;
    private LinearLayout FrSportsSecy;
    private LinearLayout EmergencyContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.include_council).setVisibility(View.VISIBLE);
        wardens=findViewById(R.id.wardens);
        genSecy=findViewById(R.id.gen_secy);
        sportsSecy=findViewById(R.id.sports_secy);
        culSecy=findViewById(R.id.cul_secy);
        FrHostelSecy=findViewById(R.id.fr_hostel_secy);
        FrHosMainSecy=findViewById(R.id.fr_hm_secy);
        FrMessSecy=findViewById(R.id.fr_mess_secy);
        FrSportsSecy=findViewById(R.id.frs_secy);
        EmergencyContacts=findViewById(R.id.emergency_cont);

        CommonFunctions.setUser(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        wardens.setOnClickListener(this);
        genSecy.setOnClickListener(this);
        sportsSecy.setOnClickListener(this);
        culSecy.setOnClickListener(this);
        FrHosMainSecy.setOnClickListener(this);
        FrHostelSecy.setOnClickListener(this);
        FrMessSecy.setOnClickListener(this);
        FrSportsSecy.setOnClickListener(this);
        EmergencyContacts.setOnClickListener(this);


    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.wardens:
                startActivity(new Intent(CouncilActivity.this,CouncilWarden.class));
                break;
            case R.id.gen_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilGenSecy.class));
                break;
            case R.id.sports_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilSportsSecy.class));
                break;
            case R.id.cul_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilCulturalSecy.class));
                break;
            case R.id.fr_hm_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilFHosMainSecy.class));
                break;
            case R.id.fr_hostel_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilFHostelSecy.class));
                break;
            case R.id.frs_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilFSportsSecy.class));
                break;
            case R.id.fr_mess_secy:
                startActivity(new Intent(CouncilActivity.this,CouncilFMessSecy.class));
                break;
            case R.id.emergency_cont:
                startActivity(new Intent(CouncilActivity.this,CouncilEmergency.class));
                break;
           default:
               startActivity(new Intent(CouncilActivity.this,CouncilWarden.class));
               break;
        }
    }
}
