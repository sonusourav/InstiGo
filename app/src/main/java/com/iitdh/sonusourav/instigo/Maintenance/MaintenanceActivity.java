package com.iitdh.sonusourav.instigo.Maintenance;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
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
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MaintenanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

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

        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);


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

        setUpImageSlider();

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

    public void setUpImageSlider(){
        UltraViewPager ultraViewPager = (UltraViewPager) findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        PagerAdapter adapter = new SliderAdapter(false,this);
        ultraViewPager.setAdapter(adapter);

        //initialize built-in indicator
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor((ContextCompat.getColor(this, R.color.black)))
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM).setMargin(0,0,0,10);
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();

        //set an infinite loop
        ultraViewPager.setInfiniteLoop(true);
        //enable auto-scroll mode
        ultraViewPager.setAutoScroll(2000);
    }

}
