package com.iitdh.sonusourav.instigo.Complaints;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.iitdh.sonusourav.instigo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StatusDetails extends AppCompatActivity {

    private TextView complainDate;
    private TextView complainTime;
    private TextView requesterUsername;
    private TextView requesterEmail;
    private TextView complainInfo;
    private Button complainStatus;
    private TextView complainTitle;
    private TextView complainType;

    private Calendar detailsCalendar;
    SimpleDateFormat detailsDateFormat;
    SimpleDateFormat detailsTimeFormat;
    private ActionBar detailsActionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        detailsInit();

        Intent mIntent = getIntent();
        ComplainItemClass complainDetails = mIntent.getParcelableExtra("complain");

        String date=detailsDateFormat.format(complainDetails.getComplainTime());
        String time=detailsTimeFormat.format(complainDetails.getComplainTime());

        complainTitle.setText(complainDetails.getComplainTitle());
        complainDate.setText(date);
        complainTime.setText(time);
        requesterUsername.setText(complainDetails.getComplainUsername());
        requesterEmail.setText(complainDetails.getComplainEmail());
        complainType.setText(complainDetails.getComplainType());
        if(!complainDetails.getComplainDetails().isEmpty()){
            complainInfo.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);
            complainInfo.setText(complainDetails.getComplainDetails());
        }else
            complainInfo.setGravity(Gravity.CENTER);


        if(complainDetails.getStatus()==1){
            complainStatus.setText("Done");
            complainStatus.setBackgroundColor(Color.GREEN);
            complainStatus.setTextColor(Color.WHITE);
        }

    }


    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        detailsActionBar = getSupportActionBar();
        assert detailsActionBar != null;
        detailsActionBar.setHomeButtonEnabled(true);
        detailsActionBar.setDisplayHomeAsUpEnabled(true);
        detailsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        detailsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Complaint</font>"));
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


    private void detailsInit(){

        complainDate=findViewById(R.id.details_date);
        complainTime=findViewById(R.id.details_time);
        requesterUsername=findViewById(R.id.details_username);
        requesterEmail=findViewById(R.id.details_email);
        complainType=findViewById(R.id.details_type);
        complainInfo=findViewById(R.id.details_info);
        complainStatus=findViewById(R.id.details_status_btn);
        complainTitle=findViewById(R.id.details_title);

        detailsCalendar=Calendar.getInstance();
        detailsDateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        detailsTimeFormat=new SimpleDateFormat("hh:mm:ss a", Locale.US);

    }
}
