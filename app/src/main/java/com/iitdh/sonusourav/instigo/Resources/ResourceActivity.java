package com.iitdh.sonusourav.instigo.Resources;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.CS.CSBranch;
import com.iitdh.sonusourav.instigo.Resources.Common.CommonBranch;
import com.iitdh.sonusourav.instigo.Resources.EE.EEBranch;
import com.iitdh.sonusourav.instigo.Resources.ME.MEBranch;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.Objects;

public class ResourceActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    private RelativeLayout csLayout;
    private RelativeLayout eeLayout;
    private RelativeLayout mechLayout;
    private RelativeLayout otherLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        findViewById(R.id.include_res).setVisibility(View.VISIBLE);

        resourceInit();

        csLayout.setOnClickListener(this);
        eeLayout.setOnClickListener(this);
        mechLayout.setOnClickListener(this);
        otherLayout.setOnClickListener(this);

    }

    private void resourceInit(){

        NavigationView navigationView = findViewById(R.id.nav_view);
        csLayout=findViewById(R.id.rl_cs);
        eeLayout=findViewById(R.id.rl_ee);
        mechLayout=findViewById(R.id.rl_me);
        otherLayout=findViewById(R.id.rl_others);


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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return CommonFunctions.navigationItemSelect(item, this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.rl_cs):
                startActivity(new Intent(ResourceActivity.this,CSBranch.class));
                break;
            case (R.id.rl_ee):
                startActivity(new Intent(ResourceActivity.this,EEBranch.class));
                break;
            case (R.id.rl_me):
                startActivity(new Intent(ResourceActivity.this,MEBranch.class));
                break;
            case (R.id.rl_others):
                startActivity(new Intent(ResourceActivity.this,CommonBranch.class));
                break;
                default:
                    startActivity(new Intent(ResourceActivity.this,CSBranch.class));
        }
    }
}
