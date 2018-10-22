package com.iitdh.sonusourav.instigo.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.include_settings).setVisibility(View.VISIBLE);

        getFragmentManager().beginTransaction()
                .replace(R.id.settings_framelayout, new SettingsFragment())
                .commit();

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

        CommonFunctions.setUser(this);


    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
            }
            else{
                Toast.makeText(getBaseContext(), "Press twice to exit", Toast.LENGTH_SHORT).show();
                back_pressed = System.currentTimeMillis();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return CommonFunctions.navigationItemSelect(item, this);
    }


    public static class SettingsFragment extends PreferenceFragment {

        public SettingsFragment() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }
}
