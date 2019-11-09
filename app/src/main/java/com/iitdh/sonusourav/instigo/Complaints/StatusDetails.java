package com.iitdh.sonusourav.instigo.Complaints;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Utils.Constants;
import com.iitdh.sonusourav.instigo.Utils.PreferenceManager;
import com.iitdh.sonusourav.instigo.Utils.ResponseClass;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusDetails extends AppCompatActivity implements View.OnClickListener {

    private TextView complainId;
    private TextView complainDateCreated;
    private TextView complainUsername;
    private TextView complainEmail;
    private CircleImageView complainPic;
    private TextView complainTitle;
    private TextView complainType;
    private TextView complainDesc;
    private TextView complainHostel;
    private TextView complainHouse;
    private Button complainStatus;
    private TextView commentsHead;
    private TextView complainStatusInfo;
    private TextView complainStatusDate;
    private RelativeLayout complainStatusTrack;
    private ListView complainComments;
    private RelativeLayout complainBtnGrp1;
    private RelativeLayout complainBtnGrp2;
    private RelativeLayout complainBtnGrp3;
    private Button complainClose;
    private Button complainOngoing;
    private Button complainResolved;
    private Button complainApprove;
    private Button complainReject;
    private ProgressDialog statusProgressDialog;
    private FloatingActionButton complainAddComment;
    private CommentsAdapter commentsAdapter;
    private ArrayList<CommentClass> commentList;
    private ArrayList<String> statusDate;
    private PreferenceManager detailsPref;
    private ComplaintsInterface detailsInterface;
    private ComplainItemClass complainDetails;
    ArrayList<String> statusArray = new ArrayList<String>() {{
        add("Rejected");
        add("Open");
        add("Valid");
        add("Forwarded");
        add("Approved");
        add("Ongoing");
        add("Resolved");
        add("Closed");
    }};
    ArrayList<String> statusInfo = new ArrayList<String>() {{
        add("Rejected");
        add("Opened by user");
        add("Validated by hostel secy ");
        add("Forwarded by warden");
        add("Approved by IPS office");
        add("Working on complaint");
        add("Complaint is resolved");
        add("Complaint is closed");
    }};

    private static final String TAG = StatusDetails.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        detailsInit();

        Intent mIntent = getIntent();
        complainDetails = (ComplainItemClass) mIntent.getSerializableExtra("complaint");

        if (complainDetails != null) {
            complainId.setText(complainDetails.getComplainId());
            if (complainDetails.getComplainPicUrl() != null) {
                Glide.with(this)
                    .load(complainDetails.getComplainPicUrl())
                    .into(complainPic);
            }
            complainTitle.setText(complainDetails.getComplainTitle());
            complainUsername.setText(complainDetails.getComplainUsername());
            complainEmail.setText(complainDetails.getComplainEmail());
            complainType.setText(complainDetails.getComplainRelated());
            complainDateCreated.setText(complainDetails.getComplainDateCreated());
            complainHostel.setText("Hostel " + complainDetails.getComplainHostel());
            complainHouse.setText("House No " + complainDetails.getComplainHouseNo());
            statusDate = complainDetails.getStatusDate();
            complainStatusDate.setText(statusDate.get(statusDate.size() - 1));
            Log.d(TAG, "statusDate" + complainDetails.getStatusDate().toString());
            if (!complainDetails.getComplainDesc().isEmpty()) {
                complainDesc.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                complainDesc.setText(complainDetails.getComplainDesc());
            } else {
                complainDesc.setGravity(Gravity.CENTER);
            }
            complainStatus.setText(statusArray.get(complainDetails.getComplainStatus()));
            complainStatusInfo.setText(statusInfo.get(complainDetails.getComplainStatus()));
            if (complainDetails.getComplainStatus() > 0
                && complainDetails.getComplainStatus() < 7) {
                complainAddComment.show();
            }
            complainAddComment.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    addComment(complainDetails.getComplainId());
                }
            });

            if (complainDetails.getComments() != null) {
                if (!complainDetails.getComments().isEmpty()) {
                    commentsHead.setVisibility(View.VISIBLE);
                    commentList.addAll(complainDetails.getComments());
                    commentsAdapter.notifyDataSetChanged();
                }
            }

            if (complainDetails.getComplainStatus() > 0
                && complainDetails.getComplainStatus() < 7
                && complainDetails.getComplainEmail().equals(detailsPref.getPrefEmail())) {
                Log.d(TAG, "user level = 0");
                complainBtnGrp3.setVisibility(View.VISIBLE);
            } else {
                complainBtnGrp3.setVisibility(View.GONE);
                complainAddComment.hide();
            }

            if (detailsPref.getUserLevel() > 0) {

                for (int i = 1; i < 4; i++) {
                    if (detailsPref.getUserLevel() == (i)
                        && complainDetails.getComplainStatus() == i) {
                        complainBtnGrp1.setVisibility(View.VISIBLE);
                    }
                }

                if (detailsPref.getUserLevel() == 4 && (complainDetails.getComplainStatus() == 4
                    || complainDetails.getComplainStatus() == 5)) {
                    complainBtnGrp2.setVisibility(View.VISIBLE);
                    if (complainDetails.getComplainStatus() == 5) {
                        complainResolved.setFocusable(true);
                        complainResolved.setEnabled(true);
                        complainResolved.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                        complainOngoing.setFocusable(false);
                        complainOngoing.setEnabled(false);
                        complainOngoing.setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.disabled_login)));
                    } else {
                        complainResolved.setFocusable(false);
                        complainResolved.setEnabled(false);
                        complainResolved.setBackgroundTintList(ColorStateList.valueOf(
                            getResources().getColor(R.color.disabled_login)));
                        complainOngoing.setFocusable(true);
                        complainOngoing.setEnabled(true);
                        complainOngoing.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                    }
                }

                if (detailsPref.getUserLevel() == 5) {
                    complainBtnGrp1.setVisibility(View.VISIBLE);
                    complainBtnGrp2.setVisibility(View.GONE);
                    complainBtnGrp3.setVisibility(View.VISIBLE);
                }
            }

            complainApprove.setOnClickListener(this);
            complainReject.setOnClickListener(this);
            complainClose.setOnClickListener(this);
            complainOngoing.setOnClickListener(this);
            complainResolved.setOnClickListener(this);

            complainStatusTrack.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent trackIntent = new Intent(StatusDetails.this, TrackOrderActivity.class);
                    trackIntent.putStringArrayListExtra("statusDate",
                        complainDetails.getStatusDate());
                    trackIntent.putStringArrayListExtra("trackStatus",
                        complainDetails.getTrackStatus());
                    startActivity(trackIntent);
                }
            });
        }
    }

    public String goToSend(int level) {
        switch (level) {
            case 0:
                return "final";
            case 1:
                return "tosecy";
            case 2:
                return "warden";
            case 3:
                return "ips";
            case 4:
                return "final";
            default:
                return "tosecy";
        }
    }

    public void notifyUsers(String to, final String status) {
        Log.d(TAG, "status" + status);
        Log.d(TAG, detailsPref.getUserId());
        Log.d(TAG, "to " + to);
        Log.d(TAG, "compId " + complainDetails.getComplainId());

        Call<ResponseClass> notifyComplain =
            detailsInterface.notifyUsers(to, status, complainDetails.getComplainId(),
                "Bearer " + detailsPref.getUserId());
        notifyComplain.enqueue(new Callback<ResponseClass>() {
            @Override
            public void onResponse(@NonNull Call<ResponseClass> call, @NonNull
                Response<ResponseClass> response) {

                if (response.body() != null) {
                    Log.d(TAG, "response not null");
                    Log.d(TAG, response.toString());
                    if (response.body().getMessage().equals("success")) {
                        complainBtnGrp2.setVisibility(View.GONE);
                        if (status.equals("approve")) {
                            Toast.makeText(StatusDetails.this, "Approved", Toast.LENGTH_SHORT)
                                .show();
                            complainBtnGrp1.setVisibility(View.GONE);
                        } else if (status.equals("reject")) {
                            Toast.makeText(StatusDetails.this, "Rejected", Toast.LENGTH_SHORT)
                                .show();
                            complainBtnGrp1.setVisibility(View.GONE);
                            complainAddComment.hide();
                        } else if (status.equals("close")) {
                            Toast.makeText(StatusDetails.this, "Closed", Toast.LENGTH_SHORT)
                                .show();
                            complainBtnGrp3.setVisibility(View.GONE);
                        } else if (status.equals("ongoing")) {
                            Toast.makeText(StatusDetails.this, "Ongoing", Toast.LENGTH_SHORT)
                                .show();
                            complainBtnGrp2.setVisibility(View.GONE);
                        } else if (status.equals("resolved")) {
                            Toast.makeText(StatusDetails.this, "Resolved", Toast.LENGTH_SHORT)
                                .show();
                            complainBtnGrp2.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(StatusDetails.this, "Something went wrong",
                            Toast.LENGTH_SHORT)
                            .show();
                    }
                } else {
                    Log.d(TAG, "responseIsNull");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseClass> call, @NonNull Throwable t) {
                Log.d(TAG, "failure");
                Toast.makeText(StatusDetails.this, "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.toString());
            }
        });
    }

    private void addComment(final String complainId) {

        final Dialog dialog = new Dialog(StatusDetails.this);
        dialog.setContentView(R.layout.add_comment);
        dialog.setTitle(" Add Comment ");
        dialog.setCancelable(true);

        final EditText commentDesc = dialog.findViewById(R.id.comment_desc);
        Button addButton = dialog.findViewById(R.id.course_add_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCancelable(false);

                final String comment = commentDesc.getText().toString().trim();

                if (comment.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please add comment",
                        Toast.LENGTH_SHORT).show();
                    commentDesc.requestFocus();
                    dialog.setCancelable(true);
                    return;
                }

                showProgressDialog();

                dialog.dismiss();

                Call<CommentClass> addComment = detailsInterface.addComments(complainId, comment,
                    "Bearer " + detailsPref.getUserId());
                addComment.enqueue(new Callback<CommentClass>() {
                    @Override
                    public void onResponse(@NonNull Call<CommentClass> call, @NonNull
                        Response<CommentClass> response) {

                        hideProgressDialog();
                        if (response.body() != null) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                    "Comment successfully added", Toast.LENGTH_SHORT).show();
                                commentList.add(response.body());
                                commentsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to add comment",
                                    Toast.LENGTH_SHORT).show();
                                Log.d(TAG, response.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommentClass> call, @NonNull Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(StatusDetails.this,
                            "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, t.toString());
                    }
                });
            }
        });

        dialog.show();
    }

    public void showProgressDialog() {

        if (statusProgressDialog == null) {
            statusProgressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
            statusProgressDialog.setMessage("Posting comments....");
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


    protected void onResume() {
        super.onResume();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar detailsActionBar = getSupportActionBar();
        assert detailsActionBar != null;
        detailsActionBar.setHomeButtonEnabled(true);
        detailsActionBar.setDisplayHomeAsUpEnabled(true);
        detailsActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5cae80")));
        detailsActionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Status</font>"));
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
        complainUsername = findViewById(R.id.details_username);
        complainEmail = findViewById(R.id.details_email);
        complainId = findViewById(R.id.details_id);
        complainType = findViewById(R.id.details_type);
        complainDateCreated = findViewById(R.id.details_date);
        complainTitle = findViewById(R.id.details_title);
        complainHostel = findViewById(R.id.details_hostel);
        complainHouse = findViewById(R.id.details_house);
        complainStatus = findViewById(R.id.details_status_btn);
        complainStatusDate = findViewById(R.id.details_track_date);
        complainStatusInfo = findViewById(R.id.details_track_info);
        complainDesc = findViewById(R.id.details_info);
        commentsHead = findViewById(R.id.details_comments_head);
        complainPic = findViewById(R.id.details_pic);
        complainComments = findViewById(R.id.details_comments);
        complainBtnGrp1 = findViewById(R.id.details_btn_group1);
        complainBtnGrp2 = findViewById(R.id.details_btn_group2);
        complainResolved = findViewById(R.id.details_resolved);
        complainOngoing = findViewById(R.id.details_ongoing);
        complainBtnGrp3 = findViewById(R.id.details_btn_group3);
        complainApprove = findViewById(R.id.details_approve);
        complainReject = findViewById(R.id.details_reject);
        complainClose = findViewById(R.id.details_close);
        complainStatusDate = findViewById(R.id.details_track_date);
        complainAddComment = findViewById(R.id.details_add_comment);
        commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(this, commentList);
        complainComments.setAdapter(commentsAdapter);
        complainApprove = findViewById(R.id.details_approve);
        complainReject = findViewById(R.id.details_reject);
        complainStatusTrack = findViewById(R.id.details_track);
        detailsPref = new PreferenceManager(this);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
        detailsInterface = new Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ComplaintsInterface.class);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_approve:
                Toast.makeText(this, "Button Approved clicked", Toast.LENGTH_SHORT).show();
                notifyUsers(goToSend(detailsPref.getUserLevel()), "approve");
                break;
            case R.id.details_close:
                Toast.makeText(this, "Button Closed clicked", Toast.LENGTH_SHORT).show();
                notifyUsers(goToSend(detailsPref.getUserLevel()), "close");
                break;
            case R.id.details_reject:
                Toast.makeText(this, "Button Rejected clicked", Toast.LENGTH_SHORT).show();
                notifyUsers(goToSend(detailsPref.getUserLevel()), "reject");
                Log.d(TAG, "reject");
                break;
            case R.id.details_ongoing:
                Toast.makeText(this, "Button Ongoing clicked", Toast.LENGTH_SHORT).show();
                notifyUsers(goToSend(detailsPref.getUserLevel()), "ongoing");
                Log.d(TAG, "ongoing");
                break;
            case R.id.details_resolved:
                Toast.makeText(this, "Button Resolved clicked", Toast.LENGTH_SHORT).show();
                notifyUsers(goToSend(detailsPref.getUserLevel()), "resolved");
                Log.d(TAG, "resolved");
                break;
        }
    }
}
