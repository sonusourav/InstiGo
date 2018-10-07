package com.iitdh.sonusourav.instigo.Maintenance;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iitdh.sonusourav.instigo.Maintenance.slider.FragmentSlider;
import com.iitdh.sonusourav.instigo.Maintenance.slider.SliderIndicator;
import com.iitdh.sonusourav.instigo.Maintenance.slider.SliderPagerAdapter;
import com.iitdh.sonusourav.instigo.Maintenance.slider.SliderView;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.TestActivity;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MaintenanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        CommonFunctions.setUser(this);

        findViewById(R.id.include_maintenance).setVisibility(View.VISIBLE);
        Button registerButton=findViewById(R.id.maintenance_register);
        Button statusButton=findViewById(R.id.maintenance_status);

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

        sliderView = (SliderView) findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);
        setupSlider();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MaintenanceActivity.this, ComplainRegister.class));
            }
        });

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MaintenanceActivity.this, ComplainStatus.class));
            }
        });

    }


    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance(""+R.drawable.image_electrician));
        fragments.add(FragmentSlider.newInstance(""+R.drawable.image_carpenter));
        fragments.add(FragmentSlider.newInstance(""+R.drawable.image_plumber));

        mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(this, mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return CommonFunctions.navigationItemSelect(item, this);
    }


    public void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        color = ResourcesCompat.getColor(getResources(), R.color.cell_title_background, null);
                        v.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

}
