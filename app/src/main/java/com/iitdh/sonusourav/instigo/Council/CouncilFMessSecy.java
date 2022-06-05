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


public class CouncilFMessSecy extends AppCompatActivity
        {

    private androidx.appcompat.app.ActionBar FMessActionBar;
    private PagerAdapter adapter;
    private UltraViewPager.Orientation gravity_indicator;
    private ArrayList<CouncilUserClass> FMessList;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.council_warden);

        UltraViewPager ultraViewPager = (UltraViewPager) findViewById(R.id.council_view_pager);
        FMessList =new ArrayList<>();
        CouncilUserClass messMem1=new CouncilUserClass("Karan Yogesh Anvekar","Mess Committee","9769719309","180020015@iitdh.ac.in",R.drawable.karan);
        CouncilUserClass messMem2=new CouncilUserClass("Shreyas Mahesh Sathe","Mess Committee","167229258"," 180010033@iitdh.ac.in",R.drawable.shreyas);
        CouncilUserClass messMem3=new CouncilUserClass("Vaishnavi Sameer Patil","Mess Committee","7038411894","180020039@iitdh.ac.in",R.drawable.vishanavi);
        CouncilUserClass messMem4=new CouncilUserClass("Rushikesh Dixit","Mess Committee","9109534276","180030037@iitdh.ac.in",R.drawable.rishikesh);
        FMessList.add(messMem1);
        FMessList.add(messMem2);
        FMessList.add(messMem3);
        FMessList.add(messMem4);

        //main code starts here
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        adapter = new UltraPagerAdapter(true,this, FMessList);
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

        FMessActionBar = getSupportActionBar();
        assert FMessActionBar != null;
        FMessActionBar.setHomeButtonEnabled(true);
        FMessActionBar.setDisplayHomeAsUpEnabled(true);
        FMessActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        FMessActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Council</font>"));
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
