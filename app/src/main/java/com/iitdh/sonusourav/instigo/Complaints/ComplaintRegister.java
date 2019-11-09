package com.iitdh.sonusourav.instigo.Complaints;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComplaintRegister extends AppCompatActivity
{

    private EditText complainTitle;
    private Spinner houseNo;
    private Spinner hostelName;
    private Spinner complainType;
    private EditText complainDesc;
    private CheckBox complainIsPrivate;
    private CheckBox complainIsPriority;
    private Button submitButton;
    private ProgressDialog complainProgressDialog;

    private String TAG = ComplaintRegister.class.getSimpleName();

    private PreferenceManager complaintPref;
    public ComplaintsInterface complaintsInterface;

    String title,desc,type;
    int house,hostel;
    boolean isPrivate,isPriority=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        Log.d("ComplaintRegister","Reaching");

        if(savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
        }
        initComplain();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkIfNull()){

                    showProgressDialog();

                    ComplainItemClass newComplaint=new ComplainItemClass(complaintPref.getUserName(),title,desc,hostel,house,type,isPriority,isPrivate);

                    Log.d(TAG,hostel+"");
                    Call<ResponseBody> postComplaint = complaintsInterface.postComplaints(newComplaint,"Bearer "+complaintPref.getUserId());
                    postComplaint.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull
                            Response<ResponseBody> response) {

                            hideProgressDialog();
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Complaint successfully added",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ComplaintRegister.this,
                                    ComplaintStatusActivity.class));
                            }else {
                                Toast.makeText(getApplicationContext(), "Failed to add complaints",
                                    Toast.LENGTH_SHORT).show();
                                Log.d(TAG,response.toString());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(ComplaintRegister.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,t.toString());
                        }
                    });


                }


            }
        });

    }


    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Complaint</font>"));
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



    private void initComplain(){

        complainTitle=findViewById(R.id.complain_title);
        houseNo=findViewById(R.id.add_complaint_house);
        hostelName=findViewById(R.id.add_complaint_hostel);
        complainType=findViewById(R.id.add_complaint_type);
        complainDesc=findViewById(R.id.et_complain_desc);
        complainIsPrivate =findViewById(R.id.complain_is_private);
        complainIsPriority=findViewById(R.id.complain_is_priority);
        submitButton=findViewById(R.id.complain_submit);
        complaintPref=new PreferenceManager(getApplicationContext());

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
        complaintsInterface = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ComplaintsInterface.class);
    }


    private boolean checkIfNull(){

         title=complainTitle.getText().toString().trim();
         house=houseNo.getSelectedItemPosition();
         hostel=hostelName.getSelectedItemPosition()-1;
         type=complainType.getSelectedItem().toString();
         desc=complainDesc.getText().toString().trim();
         isPrivate= complainIsPrivate.isChecked();
         isPriority=complainIsPriority.isChecked();

        if (title.equals("") || TextUtils.isEmpty(title) || title.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter the complain title", Toast.LENGTH_SHORT).show();
            return false ;

        } if (houseNo.getSelectedItemPosition()==0) {
            Toast.makeText(getApplicationContext(), "Select your house Number", Toast.LENGTH_SHORT).show();
            return false ;

        } if (hostelName.getSelectedItemPosition()==0) {
            Toast.makeText(getApplicationContext(), "Select your Hostel Number", Toast.LENGTH_SHORT).show();
            return false ;

        } if (complainType.getSelectedItemPosition()==0) {
            Toast.makeText(getApplicationContext(), "Select your Complain type", Toast.LENGTH_SHORT).show();
            return false ;
        }
        if (desc.equals("") || TextUtils.isEmpty(desc) || desc.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter the description", Toast.LENGTH_SHORT).show();
            return false ;

        }

        return true;
    }

    public void showProgressDialog() {

        if (complainProgressDialog == null) {
            complainProgressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            complainProgressDialog.setMessage("Registering your complaint...");
            complainProgressDialog.setIndeterminate(true);
        }

        complainProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (complainProgressDialog != null && complainProgressDialog.isShowing()) {
            complainProgressDialog.dismiss();
        }
    }


}
