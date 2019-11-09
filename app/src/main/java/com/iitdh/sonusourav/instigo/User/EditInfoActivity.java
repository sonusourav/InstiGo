package com.iitdh.sonusourav.instigo.User;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.AppSingleton;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.VolleyErrorInstances;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class EditInfoActivity extends AppCompatActivity {

    private EditText infoName, infoBranch, infoYear, infoMobile, infoDob;
    private Spinner infoGender, infoHostel;
    private Button infoSaveBtn;
    private ProgressDialog infoProgress;
    private ArrayAdapter<String> spinnerArrayAdapter1, spinnerArrayAdapter2;
    private Calendar myCalendar;
    private TextWatcher textWatcher;
    private AdapterView.OnItemSelectedListener spinnerListener;
    private UserClass userInfo;
    private String TAG = EditInfoActivity.class.getSimpleName();
    private PreferenceManager editInfoPref;
    private String updateProfileUrl;

    String proName, proBranch, proYear, proMob, proDob, proGender, proHostel;
    Boolean textChanged=false;
    int pos1, pos2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        editInfoInit();
        Intent profileIntent = getIntent();
        userInfo = (UserClass) profileIntent.getSerializableExtra("userInfo");
        updateProfile(userInfo);

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

    private void updateProfile(UserClass userClass) {
        infoName.setText(userClass.getName());
        infoBranch.setText(userClass.getBranch());
        infoYear.setText(userClass.getYear());
        infoMobile.setText(userClass.getPhone());
        infoDob.setText(userClass.getDob());
        if (userClass.getGender().trim().equals("")) {
            infoGender.setSelection(0);
        } else {
            pos1 = spinnerArrayAdapter1.getPosition(userClass.getGender());
            infoGender.setSelection(pos1);
        }
        if (userClass.getHostel().trim().equals("")) {
            infoHostel.setSelection(0);
        } else {
            pos2 = spinnerArrayAdapter2.getPosition(userClass.getHostel());
            infoHostel.setSelection(pos2);
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        infoDob.setText(sdf.format(myCalendar.getTime()));
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
        editInfoPref = new PreferenceManager(this);
        updateProfileUrl = Constants.baseUrl + "users/update/profile";
        Log.d(TAG, editInfoPref.getUserId());

        myCalendar =Calendar.getInstance();
        infoSaveBtn.setEnabled(false);


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

        infoDob.addTextChangedListener(textWatcher);
        infoName.addTextChangedListener(textWatcher);
        infoBranch.addTextChangedListener(textWatcher);
        infoDob.addTextChangedListener(textWatcher);
        infoMobile.addTextChangedListener(textWatcher);

        infoGender.setOnItemSelectedListener(spinnerListener);
        infoHostel.setOnItemSelectedListener(spinnerListener);

    }


    private boolean validateInput(){

        Log.d(TAG, "validate");
         proName=infoName.getText().toString().trim();
         proBranch=infoBranch.getText().toString().trim();
         proYear=infoYear.getText().toString().trim();
         proMob=infoMobile.getText().toString().trim();
         proDob=infoDob.getText().toString().trim();
        proGender = infoGender.getSelectedItem().toString();
        proHostel = infoHostel.getSelectedItemPosition() + "";

        if (proMob.length() > 0 && !(proMob.matches("[0-9]+"))) {
            Log.d(TAG, "mobile");
            infoMobile.setError(" Invalid Mobile no");
            infoMobile.requestFocus();
            return false;
        }

        return true;
    }


    private void updateUserData(){

        Log.d(TAG, "update");
        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("name",proName);
        taskMap.put("branch",proBranch);
        taskMap.put("year",proYear);
        taskMap.put("phone",proMob);
        taskMap.put("dob",proDob);
        taskMap.put("gender",proGender);
        taskMap.put("hostel",proHostel);

        for (String key : taskMap.keySet()) {
            if (Objects.equals(taskMap.get(key), "")) {
                taskMap.put(key, null);
            }
        }

        JSONObject updatedProfile = new JSONObject(taskMap);
        Log.d(TAG, updatedProfile.toString());

        JsonObjectRequest jsonObjReq =
            new JsonObjectRequest(Request.Method.POST, updateProfileUrl, updatedProfile,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("message").equals("success")) {
                                Log.d(TAG, "success");
                                Toast.makeText(EditInfoActivity.this,
                                    "Profile successfully updated", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                                startActivity(
                                    new Intent(EditInfoActivity.this, ProfileActivity.class));
                                EditInfoActivity.this.finish();
                            } else if (response.getString("message")
                                .substring(0, 7)
                                .equals("failure")) {
                                String errorMsg = response.getString("message").substring(8);
                                Toast.makeText(EditInfoActivity.this, errorMsg, Toast.LENGTH_SHORT)
                                    .show();
                                hideProgressDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "catch");
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        new VolleyErrorInstances().getErrorType(getApplicationContext(),
                            volleyError);
                        hideProgressDialog();
                        Log.d(TAG, "VolleyError: " + volleyError.toString());
                    }
                }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + editInfoPref.getUserId());

                    return params;
                }
            };

        AppSingleton.getInstance().addToRequestQueue(jsonObjReq);
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

        if (item.getItemId() == android.R.id.home) {
            if (textChanged) {
                backDialogBuilder();
            } else {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return true;

    }


    public void backDialogBuilder() {

        // setup the alert builder
        androidx.appcompat.app.AlertDialog.Builder builder =
            new androidx.appcompat.app.AlertDialog.Builder(this);

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

        if (infoProgress == null) {
            infoProgress = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            infoProgress.setMessage("Updating your profile ....");
            infoProgress.setIndeterminate(true);
            infoProgress.setCanceledOnTouchOutside(false);
        }

        infoProgress.show();
    }

    public void hideProgressDialog() {
        if (infoProgress != null && infoProgress.isShowing()) {
            infoProgress.dismiss();
        }
    }

}
