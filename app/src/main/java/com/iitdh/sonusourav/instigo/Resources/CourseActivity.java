package com.iitdh.sonusourav.instigo.Resources;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.CommonFunctions;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener{

  private ArrayList<CourseClass> courseList;
  private ResourceInterface retrofitInterface;
  private CourseAdapter adapter;
  private String TAG = CourseActivity.class.getSimpleName();
  private ProgressDialog courseProgressDialog;
  private FloatingActionButton addCourseButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    findViewById(R.id.include_res).setVisibility(View.VISIBLE);

    resourceInit();

    addCourseButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addCourse();
      }
    });


  }

  private void resourceInit(){

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    getSupportActionBar().setHomeButtonEnabled(true);

    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    addCourseButton=findViewById(R.id.courses_add_fab);

    CommonFunctions.setUser(this);

    courseInit();
    showProgressDialog();
    getCourseList();



  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return CommonFunctions.navigationItemSelect(item, this);

  }

  private void addCourse(){

    final Dialog dialog = new Dialog(CourseActivity.this);
    dialog.setContentView(R.layout.add_course);
    dialog.setTitle(" Add Course ");
    dialog.setCancelable(true);


    final EditText courseNameEt =dialog.findViewById(R.id.course_add_name);
    final EditText courseNoEt=dialog.findViewById(R.id.course_add_no);
    final CheckBox cs=dialog.findViewById(R.id.add_check_cs);
    final CheckBox ee=dialog.findViewById(R.id.add_check_ee);
    final CheckBox me=dialog.findViewById(R.id.add_check_me);
    Button addButton=dialog.findViewById(R.id.course_add_btn);



    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        dialog.setCancelable(false);

        final String name;
        final String no;
        ArrayList<String> branch=new ArrayList<>();

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

        if(cs.isChecked()){
          branch.add("1");
        }
        if(ee.isChecked()){
          branch.add("2");
        }
        if(me.isChecked()){
          branch.add("3");
        }
        if(branch.isEmpty()){
          Toast.makeText(getApplicationContext(),"Please select a branch ", Toast.LENGTH_SHORT).show();
          return;
        }

        showProgressDialog();

        dialog.dismiss();

        CourseClass newCourse=new CourseClass(no,name,branch);

        Call<ResponseBody> addCourseCall = retrofitInterface.postCourse(newCourse);
        addCourseCall.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(@NonNull Call<ResponseBody> call, @NonNull
              Response<ResponseBody> response) {

            hideProgressDialog();
            if(response.code()==200){
              Toast.makeText(getApplicationContext(),"Course successfully added",Toast.LENGTH_SHORT).show();
            }else {
              Toast.makeText(getApplicationContext(),"Failed to add course",Toast.LENGTH_SHORT).show();
              Log.d(TAG,response.toString());
            }
          }

          @Override
          public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
            hideProgressDialog();
            Toast.makeText(CourseActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            Log.d(TAG,t.toString());
          }
        });


      }
    });

    dialog.show();

  }

  private void getCourseList(){
    Call<ArrayList<CourseClass>> call = retrofitInterface.getCourses();
    call.enqueue(new Callback<ArrayList<CourseClass>>() {
      @Override
      public void onResponse(@NonNull Call<ArrayList<CourseClass>> call, @NonNull
          Response<ArrayList<CourseClass>> response) {

        hideProgressDialog();
        if(response.body()!=null){

          ArrayList<CourseClass> courses=response.body();
          for (CourseClass course : courses) {
            Log.d(TAG, course.getCourseName());
            courseList.add(course);
            adapter.notifyDataSetChanged();

          }
          Log.d(TAG,response.toString());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ArrayList<CourseClass>> call, @NonNull Throwable t) {
        hideProgressDialog();
        Toast.makeText(CourseActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        Log.d(TAG,t.toString());
        Log.d(TAG,call.toString());
      }
    });
  }


  private void courseInit(){
    RecyclerView recyclerView = findViewById(R.id.resources_recycler_view);
    courseList = new ArrayList<>();
    adapter = new CourseAdapter(this,courseList);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build();
    retrofitInterface = new Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ResourceInterface.class);
  }


  public void showProgressDialog() {

    if (courseProgressDialog == null) {
      courseProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
      courseProgressDialog.setMessage("Fetching courses....");
      courseProgressDialog.setIndeterminate(true);
      courseProgressDialog.setCanceledOnTouchOutside(false);
    }

    courseProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (courseProgressDialog != null && courseProgressDialog.isShowing()) {
      courseProgressDialog.dismiss();
    }
  }

}
