package com.iitdh.sonusourav.instigo.User;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class EditInfoActivity extends AppCompatActivity {

    private EditText infoName;
    private EditText infoBranch;
    private EditText infoYear;
    private EditText infoMobile;
    private EditText infoDob;
    private Spinner infoGender;
    private Spinner infoHostel;
    private Button infoSaveBtn;
    private ProgressDialog infoProgress;
    ArrayAdapter<String> spinnerArrayAdapter1;
    ArrayAdapter<String> spinnerArrayAdapter2;
    private Calendar myCalendar;
    private  TextWatcher textWatcher;
    private  AdapterView.OnItemSelectedListener spinnerListener;
    private LinearLayout linearLayout;

    private DatabaseReference infoUserRef;
    private FirebaseUser firebaseUser;

    String proName;
    String proBranch;
    String proYear;
    String proMob;
    String proDob;
    String proGender;
    String proHostel;
    Boolean textChanged=false;
    int pos1;
    int pos2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        editInfoInit();

        showProgressDialog();
        fetchUserData();

        infoSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateInput()){

                    showProgressDialog();
                    updateUserData();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        infoDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(EditInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }



    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        infoDob.setText(sdf.format(myCalendar.getTime()));
    }


    private void fetchUserData(){

        infoUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    UserClass userClass=dataSnapshot.getValue(UserClass.class);
                    if(userClass.getName().trim()!=null){
                        infoName.setText(userClass.getName().trim());
                    }else{
                        infoName.setText("");

                    }
                    if(userClass.getBranch().trim()!=null){
                        infoBranch.setText(userClass.getBranch().trim());
                    }else{
                        infoBranch.setText("");

                    }
                    if(userClass.getYear().trim()!=null){
                        infoYear.setText(userClass.getYear().trim());
                    }else{
                        infoYear.setText("");

                    }
                    if(userClass.getPhone().trim()!=null){
                        infoMobile.setText(userClass.getPhone().trim());
                    }else{
                        infoMobile.setText("");

                    }
                    if(userClass.getDob().trim()!=null){
                        infoDob.setText(userClass.getDob().trim());
                    }else{
                        infoDob.setText("");

                    }

                    if(userClass.getGender().trim()!=null){
                        pos1=spinnerArrayAdapter1.getPosition(userClass.getGender());
                        infoGender.setSelection(pos1);

                    }else{
                        infoGender.setSelection(1);

                    }

                    if(userClass.getHostel().trim()!=null){
                        pos2=spinnerArrayAdapter2.getPosition(userClass.getHostel());
                        infoHostel.setSelection(pos2);
                    }else{
                        infoGender.setSelection(1);

                    }

                    hideProgressDialog();


                    infoDob.addTextChangedListener(textWatcher);
                    infoName.addTextChangedListener(textWatcher);
                    infoBranch.addTextChangedListener(textWatcher);
                    infoDob.addTextChangedListener(textWatcher);
                    infoMobile.addTextChangedListener(textWatcher);

                    infoGender.setOnItemSelectedListener(spinnerListener);
                    infoHostel.setOnItemSelectedListener(spinnerListener);
                }else {
                    Toast.makeText(getApplicationContext(),"User data does not exist",Toast.LENGTH_SHORT).show();
                    hideProgressDialog();

                    infoDob.addTextChangedListener(textWatcher);
                    infoName.addTextChangedListener(textWatcher);
                    infoBranch.addTextChangedListener(textWatcher);
                    infoDob.addTextChangedListener(textWatcher);
                    infoMobile.addTextChangedListener(textWatcher);

                    infoGender.setOnItemSelectedListener(spinnerListener);
                    infoHostel.setOnItemSelectedListener(spinnerListener);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"Failed to fetch user data",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void editInfoInit(){

        infoName=findViewById(R.id.pro_edit_name);
        infoBranch=findViewById(R.id.pro_edit_branch);
        infoYear=findViewById(R.id.pro_edit_year);
        infoMobile=findViewById(R.id.pro_edit_mob);
        infoDob=findViewById(R.id.pro_edit_dob);
        infoGender=findViewById(R.id.pro_edit_gender);
        infoHostel=findViewById(R.id.pro_edit_hostel);
        infoSaveBtn=findViewById(R.id.pro_edit_save);
        linearLayout=findViewById(R.id.activity_edit_info_linear_layout);

        myCalendar =Calendar.getInstance();
        infoSaveBtn.setEnabled(false);
        FirebaseAuth infoAuth = FirebaseAuth.getInstance();
        FirebaseUser infoUser = infoAuth.getCurrentUser();
        assert infoUser!=null;
        FirebaseDatabase infoInstance = FirebaseDatabase.getInstance();
        DatabaseReference infoRootRef = infoInstance.getReference().child("Users");
        infoUserRef= infoRootRef.child(encodeUserEmail(Objects.requireNonNull(infoUser.getEmail()))).getRef();

       spinnerArrayAdapter1 = new ArrayAdapter<>(
                this, R.layout.spinner_item1, getResources().getStringArray(R.array.gender)
        );
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_drop_down_item1);
        infoGender.setAdapter(spinnerArrayAdapter1);


       spinnerArrayAdapter2 = new ArrayAdapter<>(
                this, R.layout.spinner_item1, getResources().getStringArray(R.array.hostel)
        );
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_drop_down_item1);
        infoHostel.setAdapter(spinnerArrayAdapter2);


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged","reaching");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if(!s.toString().isEmpty()){
                    Log.d("onTextChanged",s.toString());

                    textChanged=true;
                    infoSaveBtn.setEnabled(true);
                }else if(s.equals("")){
                    textChanged=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged","reaching");
            }
        };



        spinnerListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

               if(parentView==infoGender && position!=pos1){
                   Log.d("onItemSelected","1reaching");

                   textChanged=true;
                   infoSaveBtn.setEnabled(true);
               }
               else if(parentView==infoHostel && position!=pos2){
                   Log.d("onItemSelected","2reaching");

                   textChanged=true;
                   infoSaveBtn.setEnabled(true);
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        };





    }


    private boolean validateInput(){

         proName=infoName.getText().toString().trim();
         proBranch=infoBranch.getText().toString().trim();
         proYear=infoYear.getText().toString().trim();
         proMob=infoMobile.getText().toString().trim();
         proDob=infoDob.getText().toString().trim();
         proGender=infoGender.getSelectedItem().toString().trim();
         proHostel=infoHostel.getSelectedItem().toString().trim();


        if(proName.isEmpty() ){
            infoName.setError("Name is empty");
            infoName.requestFocus();
            return false;
        }

        if(proBranch.isEmpty() ){
            infoBranch.setError("ResourceActivity is empty");
            infoBranch.requestFocus();
            return false;
        }

        if(proYear.isEmpty() ){
            infoYear.setError("Current year is empty");
            infoYear.requestFocus();
            return false;
        }

        if(proMob.isEmpty() ){
            infoMobile.setError("Mobile No is empty");
            infoMobile.requestFocus();
            return false;
        }


        if(proMob.length()!=10 || !(proMob.matches("[0-9]+")) ){
            infoMobile.setError(" Invalid Mobile no");
            infoMobile.requestFocus();
            return false;
        }

        if(proDob.isEmpty() ){

            Toast.makeText(getApplicationContext(),"Please Select your Dob ",Toast.LENGTH_SHORT).show();
            infoDob.requestFocus();
            return false;
        }

        if(proGender.isEmpty() ){

            Toast.makeText(getApplicationContext(),"Please Select your gender ",Toast.LENGTH_SHORT).show();
            infoGender.requestFocus();
            return false;
        }

        if(proHostel.isEmpty() ){
            Toast.makeText(getApplicationContext(),"Please select your hostel",Toast.LENGTH_SHORT).show();
            infoGender.requestFocus();
            return false;
        }

        return true;
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


    private void updateUserData(){

        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("name",proName);
        taskMap.put("branch",proBranch);
        taskMap.put("year",proYear);
        taskMap.put("phone",proMob);
        taskMap.put("dob",proDob);
        taskMap.put("gender",proGender);
        taskMap.put("hostel",proHostel);
        infoUserRef.updateChildren(taskMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(proName)
                        .build();
                firebaseUser.updateProfile(request);
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"Profile successfully updated",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditInfoActivity.this,ProfileActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),"Profile update failed",Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void onResume() {
        super.onResume();



    }

    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar infoActionBar = getSupportActionBar();
        assert infoActionBar != null;
        infoActionBar.setHomeButtonEnabled(true);
        infoActionBar.setDisplayHomeAsUpEnabled(true);
        infoActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        infoActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Update Profile</font>"));
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:

                if(textChanged){
                    backDialogBuilder();
                }else
                    NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;

    }


    public void backDialogBuilder() {

        // setup the alert builder
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        // add the buttons
        builder
                .setMessage("You have unsaved changes. Do you want to discard?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        NavUtils.navigateUpFromSameTask(EditInfoActivity.this);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create and show the alert dialog
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showProgressDialog() {
        if (isNetworkAvailable()){
            if (infoProgress == null) {
                infoProgress = new ProgressDialog(this,R.style.MyAlertDialogStyle);
                infoProgress.setMessage("Updating your profile ....");
                infoProgress.setIndeterminate(true);
                infoProgress.setCanceledOnTouchOutside(false);
            }

            infoProgress.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isNetworkAvailable()){
                        hideProgressDialog();
                        Snackbar.make(linearLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
                    }
                }
            },15000);
        }else {
            Snackbar.make(linearLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }


    }

    public void hideProgressDialog() {
        if (infoProgress != null && infoProgress.isShowing()) {
            infoProgress.dismiss();
        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }

}
