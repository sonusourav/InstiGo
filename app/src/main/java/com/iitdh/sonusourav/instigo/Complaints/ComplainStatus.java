package com.iitdh.sonusourav.instigo.Complaints;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;
import com.iitdh.sonusourav.instigo.R;
import com.yalantis.taurus.PullToRefreshView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ComplainStatus extends AppCompatActivity {

    private static final String TAG =ComplainStatus.class.getSimpleName() ;
    private ArrayList<ComplainItemClass> complainStatusList;
    private ComplaintsAdapter statusAdapter;
    private FirebaseAuth statusAuth;
    private FirebaseUser statusUser;
    public static final int REFRESH_DELAY = 4000;
    private PullToRefreshView mPullToRefreshView;
    private ProgressDialog statusProgressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_status);

        ListView listView = findViewById(R.id.status_listview);
        complainStatusList = new ArrayList<>();

        showProgressDialog();

        FirebaseAuth statusAuth = FirebaseAuth.getInstance();
        FirebaseDatabase statusInstance = FirebaseDatabase.getInstance();
        DatabaseReference statusRootRef = statusInstance.getReference("Maintenance");
        DatabaseReference statusRef = statusRootRef.child("Complaints").getRef();
        statusUser=statusAuth.getCurrentUser();


        if(statusUser==null){
            startActivity(new Intent(ComplainStatus.this, LoginActivity.class));
            finish();
        }


        statusAdapter = new ComplaintsAdapter(this, complainStatusList);
        listView.setAdapter(statusAdapter);


        statusRef.orderByChild("complainTime").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    complainStatusList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                        ComplainItemClass complain = snapshot.getValue(ComplainItemClass.class);

                        if(complain!=null){

                            if(Objects.requireNonNull(statusUser.getEmail()).equalsIgnoreCase("170020021@iitdh.ac.in")){
                                complainStatusList.add(complain);

                            }else{
                                if(!complain.isComplainIsPrivate()|| complain.getComplainEmail().equalsIgnoreCase(statusUser.getEmail())){
                                    complainStatusList.add((complain));
                                }
                            }

                        }


                    }
                    Collections.reverse(complainStatusList);
                    statusAdapter.notifyDataSetChanged();
                    hideProgressDialog();
                    mPullToRefreshView.setVisibility(View.VISIBLE);

                }else{
                    hideProgressDialog();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                hideProgressDialog();
            }
        });


        mPullToRefreshView = findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });


    }

    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar statusActionBar = getSupportActionBar();
        assert statusActionBar != null;
        statusActionBar.setHomeButtonEnabled(true);
        statusActionBar.setDisplayHomeAsUpEnabled(true);
        statusActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        statusActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Complaint</font>"));
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

    public void showProgressDialog() {

        if (statusProgressDialog == null) {
            statusProgressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            statusProgressDialog.setMessage("Fetching complaints....");
            statusProgressDialog.setIndeterminate(true);
            statusProgressDialog.setCanceledOnTouchOutside(false);
        }

        statusProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (statusProgressDialog != null && statusProgressDialog.isShowing()) {
            statusProgressDialog.dismiss();
        }
    }

}
