package com.iitdh.sonusourav.instigo.Resources.CS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.Account.LoginActivity;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.CourseClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;


public class CSBranch  extends AppCompatActivity {

    private ProgressDialog csBranchProgressDialog;
    private static final String TAG =CSBranch.class.getSimpleName() ;
    private ArrayList<CourseClass> csCourseList;
    private CSCourseAdapter csCSCourseAdapter;

    private DatabaseReference csCourseDocRef;
    private FirebaseUser csCourseUser;
    private DatabaseReference csCourseRef;


    private TextView emptyCourses;
    private FloatingActionButton csAddCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_listview);

        csInit();
        updateCourses();

        csAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCourse();
            }
        });

    }

    private void addCourse(){


                final Dialog dialog = new Dialog(CSBranch.this);
                dialog.setContentView(R.layout.add_course);
                dialog.setTitle(" Add Course ");
                dialog.setCancelable(true);


                final EditText courseNameEt =dialog.findViewById(R.id.course_add_name);
                final EditText courseNoEt=dialog.findViewById(R.id.course_add_no);
                Button addButton=dialog.findViewById(R.id.course_add_btn);



                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.setCancelable(false);

                        final String name;
                        final String no;

                        name=courseNameEt.getText().toString().trim();
                        no=courseNoEt.getText().toString().trim();

                        if(name.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please fill Course name ", Toast.LENGTH_SHORT).show();
                            courseNameEt.requestFocus();
                            dialog.setCancelable(true);
                            return;
                        }
                        if(no.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please fill Course no ", Toast.LENGTH_SHORT).show();
                            courseNoEt.requestFocus();
                            dialog.setCancelable(true);
                            return;
                        }

                        showProgressDialog();

                        Calendar calendar=Calendar.getInstance();
                        final String date = new SimpleDateFormat("dd MMM yy h:mm:ss a", Locale.US).format(calendar.getTime());


                        final String username=csCourseUser.getDisplayName();

                        CourseClass newCourse=new CourseClass(name,no,date,username,"CSE");


                        String userReference= csCourseRef.push().getKey();
                         csCourseDocRef.child(name).push();

                        assert userReference != null;
                        csCourseRef.child(userReference).setValue(newCourse).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed to add Course",Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                CourseClass newCourse=new CourseClass(name,no,date,username,"CSE");
                                csCourseList.add(newCourse);
                                csCSCourseAdapter.notifyDataSetChanged();

                                if(emptyCourses.getVisibility()==View.VISIBLE){
                                    emptyCourses.setVisibility(View.GONE);
                                }
                                hideProgressDialog();
                            }
                        });
                        dialog.dismiss();


                    }
                });

                dialog.show();

            }




    private void updateCourses(){


        showProgressDialog();
        csCourseRef.limitToLast(20).orderByChild("dateCreated").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    csCourseList.clear();

                    emptyCourses.setVisibility(View.GONE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                        CourseClass courses = snapshot.getValue(CourseClass.class);

                        if(courses!=null){
                            csCourseList.add(courses);
                        }

                    }
                    Collections.reverse(csCourseList);
                    csCSCourseAdapter.notifyDataSetChanged();

                }
                hideProgressDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                hideProgressDialog();
            }
        });


    }

    private void csInit(){

        emptyCourses=findViewById(R.id.branch_course_empty);
        ListView listView = findViewById(R.id.branch_list_view);
        csAddCourse =findViewById(R.id.course_add_fab);
        csCourseList = new ArrayList<>();

        FirebaseAuth csCourseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase csCourseInstance = FirebaseDatabase.getInstance();
        DatabaseReference csCourseRootRef = csCourseInstance.getReference("Resources");
        csCourseRef= csCourseRootRef.child("Courses").child("CSE").getRef();
        csCourseDocRef= csCourseRootRef.child("CourseDocs").child("CSE").getRef();
        csCourseUser = csCourseAuth.getCurrentUser();


        if(csCourseUser ==null){
            startActivity(new Intent(CSBranch.this, LoginActivity.class));
            finish();
        }


        csCSCourseAdapter = new CSCourseAdapter(this, csCourseList);
        listView.setAdapter(csCSCourseAdapter);
    }

    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        android.support.v7.app.ActionBar FSportsActionBar = getSupportActionBar();
        assert FSportsActionBar != null;
        FSportsActionBar.setHomeButtonEnabled(true);
        FSportsActionBar.setDisplayHomeAsUpEnabled(true);
        FSportsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        FSportsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>CSe</font>"));
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

        if (csBranchProgressDialog == null) {
            csBranchProgressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            csBranchProgressDialog.setMessage("Updating courses....");
            csBranchProgressDialog.setIndeterminate(true);
            csBranchProgressDialog.setCanceledOnTouchOutside(false);
        }

        csBranchProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (csBranchProgressDialog != null && csBranchProgressDialog.isShowing()) {
            csBranchProgressDialog.dismiss();
        }
    }

}
