package com.iitdh.sonusourav.instigo.Council;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.iitdh.sonusourav.instigo.R;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.ArrayList;


public class CouncilSportsSecy extends AppCompatActivity
        {

            @Override

            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.council_warden);

        UltraViewPager ultraViewPager = (UltraViewPager) findViewById(R.id.council_view_pager);
                ArrayList<CouncilUserClass> sportsList = new ArrayList<>();
        CouncilUserClass footballSecy=new CouncilUserClass("Adhokshaja V Madhwaraj","Institute Football Secretary",""," 160010032@iitdh.ac.in",R.drawable.adhok);
        CouncilUserClass cricketSecy=new CouncilUserClass("Abhay Sahu","Institute Cricket Secretary",""," 160030020@iitdh.ac.in",R.drawable.abhay);
        CouncilUserClass volleyballSecy=new CouncilUserClass("Ashrith Kumar Peddy","Institute Volleyball Secretary",""," 170010036@iitdh.ac.in",R.drawable.ashrith);
        CouncilUserClass hockeySecy=new CouncilUserClass("Basavaraj H Dindur","Institute Hockey Secretary",""," 170030034@iitdh.ac.in",R.drawable.basavaraj);
        CouncilUserClass badmintonSecy=new CouncilUserClass("Rohan Shrothrium","Institute Badminton Secretary","","170020031@iitdh.ac.in",R.drawable.rohan_shrotium);
        CouncilUserClass athleticsSecy=new CouncilUserClass("Avneet Sehgal","Institute Athletics Secretary",""," 170010029@iitdh.ac.in",R.drawable.avneet);
        CouncilUserClass basketballSecy=new CouncilUserClass("Anudeep Tubati ","Institute Basketball Secretary","","170010039@iitdh.ac.in",R.drawable.anudeep);
        CouncilUserClass ttSecy=new CouncilUserClass("Deepak H R","Institute Table Tennis Secretary","","170010026@iitdh.ac.in",R.drawable.deepak);
        CouncilUserClass boardGamesSecy=new CouncilUserClass("Shubham Suresh Deshpande","Institute Board Games Secretary",""," 160030005@iitdh.ac.in ",R.drawable.shubham);

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
                PagerAdapter adapter = new UltraPagerAdapter(true, this, sportsList);
        ultraViewPager.setAdapter(adapter);

        ultraViewPager.setMultiScreen(0.8f);
        ultraViewPager.setItemRatio(1f);
        ultraViewPager.setRatio(0.75f);
        ultraViewPager.setAutoMeasureHeight(true);
                UltraViewPager.Orientation gravity_indicator = UltraViewPager.Orientation.HORIZONTAL;
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());



    }



    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        androidx.appcompat.app.ActionBar sportsActionBar = getSupportActionBar();
        assert sportsActionBar != null;
        sportsActionBar.setHomeButtonEnabled(true);
        sportsActionBar.setDisplayHomeAsUpEnabled(true);
        sportsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        sportsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Council</font>"));
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
