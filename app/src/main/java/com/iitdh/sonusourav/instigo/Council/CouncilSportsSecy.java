package com.iitdh.sonusourav.instigo.Council;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.iitdh.sonusourav.instigo.R;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;


public class CouncilSportsSecy extends AppCompatActivity
        {

    private android.support.v7.app.ActionBar SportsActionBar;
    private PagerAdapter adapter;
    private UltraViewPager.Orientation gravity_indicator;
    private ArrayList<CouncilUserClass> sportsList;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.council_warden);

        UltraViewPager ultraViewPager = (UltraViewPager) findViewById(R.id.council_view_pager);
        sportsList =new ArrayList<>();
        CouncilUserClass footballSecy=new CouncilUserClass("Adhokshaja V Madhwaraj","Institute Football Secretary",""," 160010032@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass cricketSecy=new CouncilUserClass("Abhay Sahu","Institute Cricket Secretary",""," 160030020@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass volleyballSecy=new CouncilUserClass("Ashrith Kumar Peddy","Institute Volleyball Secretary",""," 170010036@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass hockeySecy=new CouncilUserClass("Basavaraj H Dindur","Institute Hockey Secretary",""," 170030034@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass badmintonSecy=new CouncilUserClass("Rohan Shrothrium","Institute Badminton Secretary","","170020031@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass athleticsSecy=new CouncilUserClass("Avneet Sehgal","Institute Athletics Secretary",""," 170010029@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass basketballSecy=new CouncilUserClass("Anudeep Tubati ","Institute Basketball Secretary","","170010039@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass ttSecy=new CouncilUserClass("Deepak H R","Institute Table Tennis Secretary","","170010026@iitdh.ac.in",R.drawable.ritik);
        CouncilUserClass boardGamesSecy=new CouncilUserClass("Shubham Suresh Deshpande","Institute Board Games Secretary",""," 160030005@iitdh.ac.in ",R.drawable.ritik);

        sportsList.add(footballSecy);
        sportsList.add(cricketSecy);
        sportsList.add(volleyballSecy);
        sportsList.add(hockeySecy);
        sportsList.add(badmintonSecy);
        sportsList.add(athleticsSecy);
        sportsList.add(basketballSecy);
        sportsList.add(ttSecy);
        sportsList.add(boardGamesSecy);

        //main code starts here
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new UltraPagerAdapter(true,this, sportsList);
        ultraViewPager.setAdapter(adapter);

        ultraViewPager.setMultiScreen(0.8f);
        ultraViewPager.setItemRatio(1f);
        ultraViewPager.setRatio(0.75f);
        ultraViewPager.setAutoMeasureHeight(true);
        gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());



    }



    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        SportsActionBar = getSupportActionBar();
        assert SportsActionBar != null;
        SportsActionBar.setHomeButtonEnabled(true);
        SportsActionBar.setDisplayHomeAsUpEnabled(true);
        SportsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        SportsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Complaint</font>"));
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


}
