package com.iitdh.sonusourav.instigo.Complaints;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.iitdh.sonusourav.instigo.HomeActivity;
import com.iitdh.sonusourav.instigo.Mess.ViewPagerAdapter;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;

import java.util.Objects;


public class ComplaintStatusActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  //This is our viewPager
  private ViewPager viewPager;

  //Fragments
  FragmentPubComplaintsStatus pubStatus;
  FragmentMyComplaintsStatus myComplaintsStatus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_status);

    //Initializing viewPager
    viewPager = findViewById(R.id.complaint_viewpager);
    viewPager.setOffscreenPageLimit(2);
    setupViewPager(viewPager);

    //Initializing the tabLayout
    //This is our tablayout
    TabLayout tabLayout =  findViewById(R.id.complaint_tablayout);
    tabLayout.setupWithViewPager(viewPager);



    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        viewPager.setCurrentItem(position,false);

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  public boolean onCreateOptionsMenu(Menu menu) {

    androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
    actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Status</font>"));
    return super.onCreateOptionsMenu(menu);

  }

  public boolean onOptionsItemSelected(MenuItem item) {

    super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return true;

  }


  private void setupViewPager(ViewPager viewPager)
  {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    pubStatus =new FragmentPubComplaintsStatus();
    myComplaintsStatus =new FragmentMyComplaintsStatus();
    adapter.addFragment(pubStatus,"ALL COMPLAINTS");
    adapter.addFragment(myComplaintsStatus,"MY COMPLAINTS");
    viewPager.setAdapter(adapter);
  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return CommonFunctions.navigationItemSelect(item, this);
  }

}
