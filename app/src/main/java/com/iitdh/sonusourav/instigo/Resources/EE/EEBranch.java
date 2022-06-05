package com.iitdh.sonusourav.instigo.Resources.EE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
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

import com.facebook.shimmer.ShimmerFrameLayout;
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


public class EEBranch extends AppCompatActivity {

    private static final String TAG = EEBranch.class.getSimpleName();
    private ArrayList<CourseClass> eeCourseList;
    private EECourseAdapter eeCSCourseAdapter;

    private DatabaseReference eeCourseDocRef;
    private FirebaseUser eeCourseUser;
    private DatabaseReference eeCourseRef;
    private ShimmerFrameLayout mShimmerViewContainer;

    private TextView emptyCourses;
    private FloatingActionButton eeAddCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_listview);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        eeInit();
        updateCourses();

        eeAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCourse();
            }
        });

    }

    private void addCourse() {


        final Dialog dialog = new Dialog(EEBranch.this);
        dialog.setContentView(R.layout.add_course);
        dialog.setTitle(" Add Course ");
        dialog.setCancelable(true);


        final EditText courseNameEt = dialog.findViewById(R.id.course_add_name);
        final EditText courseNoEt = dialog.findViewById(R.id.course_add_no);
        Button addButton = dialog.findViewById(R.id.course_add_btn);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCancelable(false);

                final String name;
                final String no;

                name = courseNameEt.getText().toString().trim();
                no = courseNoEt.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill Course name ", Toast.LENGTH_SHORT).show();
                    courseNameEt.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }
                if (no.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill Course no ", Toast.LENGTH_SHORT).show();
                    courseNoEt.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                mShimmerViewContainer.startShimmer();

                Calendar calendar = Calendar.getInstance();
                final String date = new SimpleDateFormat("dd MMM yy h:mm:ss a", Locale.US).format(calendar.getTime());


                final String username = eeCourseUser.getDisplayName();

                CourseClass newCourse = new CourseClass(name, no, date, username, "EE");


                String userReference = eeCourseRef.push().getKey();
                eeCourseDocRef.child(name).push();

                assert userReference != null;
                eeCourseRef.child(userReference).setValue(newCourse).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add Course", Toast.LENGTH_SHORT).show();
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        CourseClass newCourse = new CourseClass(name, no, date, username, "EE");
                        eeCourseList.add(newCourse);
                        eeCSCourseAdapter.notifyDataSetChanged();

                        if (emptyCourses.getVisibility() == View.VISIBLE) {
                            emptyCourses.setVisibility(View.GONE);
                        }
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                });
                dialog.dismiss();


            }
        });

        dialog.show();

    }


    private void updateCourses() {


        mShimmerViewContainer.startShimmer();
        eeCourseRef.limitToLast(20).orderByChild("dateCreated").addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    eeCourseList.clear();

                    emptyCourses.setVisibility(View.GONE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                        CourseClass courses = snapshot.getValue(CourseClass.class);

                        if (courses != null) {
                            eeCourseList.add(courses);
                        }

                    }
                    Collections.reverse(eeCourseList);
                    eeCSCourseAdapter.notifyDataSetChanged();

                } else {
                    emptyCourses.setVisibility(View.VISIBLE);
                }
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });


    }

    private void eeInit() {

        emptyCourses = findViewById(R.id.branch_course_empty);
        ListView listView = findViewById(R.id.branch_list_view);
        eeAddCourse = findViewById(R.id.course_add_fab);
        eeCourseList = new ArrayList<>();

        FirebaseAuth eeCourseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase csCourseInstance = FirebaseDatabase.getInstance();
        DatabaseReference csCourseRootRef = csCourseInstance.getReference("Resources");
        eeCourseRef = csCourseRootRef.child("Courses").child("EE").getRef();
        eeCourseDocRef = csCourseRootRef.child("CourseDocs").child("EE").getRef();
        eeCourseUser = eeCourseAuth.getCurrentUser();


        if (eeCourseUser == null) {
            startActivity(new Intent(EEBranch.this, LoginActivity.class));
            finish();
        }


        eeCSCourseAdapter = new EECourseAdapter(this, eeCourseList);
        listView.setAdapter(eeCSCourseAdapter);
    }

    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        androidx.appcompat.app.ActionBar FSportsActionBar = getSupportActionBar();
        assert FSportsActionBar != null;
        FSportsActionBar.setHomeButtonEnabled(true);
        FSportsActionBar.setDisplayHomeAsUpEnabled(true);
        FSportsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        FSportsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>EE</font>"));
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
