package com.iitdh.sonusourav.instigo.Mess;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitdh.sonusourav.instigo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class FragmentFeedback extends Fragment {

    public FragmentFeedback() {

    }
    ArrayList<FeedbackUserClass> feedbackList;
    MessFeedbackAdapter messFeedbackAdapter;
    private FirebaseDatabase feedbackInstance;
    private DatabaseReference feedbackRootRef;
    private DatabaseReference messFeedbackRef;
    private ProgressDialog csDocProgressDialog;

    private View view;
    private ListView listView;
    private FloatingActionButton fabButton;
    private ProgressBar feedbackProgressbar;

    private String about ;
    private String day;
    private String title;
    private String rating;
    private String desc;


    private String TAG=FragmentFeedback.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_feedback,null);

        showProgressDialog();

        listView = view.findViewById(R.id.mess_feedback_listview);
        feedbackList = new ArrayList<>();
        fabButton = view.findViewById(R.id.mess_feedback_fab);
        feedbackProgressbar=view.findViewById(R.id.feedback_progress_bar);


        FirebaseAuth ecAuth = FirebaseAuth.getInstance();
        feedbackInstance = FirebaseDatabase.getInstance();
        feedbackRootRef = feedbackInstance.getReference("Mess");
        messFeedbackRef = feedbackRootRef.child("Feedback").getRef();
        final FirebaseUser user = ecAuth.getCurrentUser();

        assert user != null;



        messFeedbackAdapter = new MessFeedbackAdapter(getActivity(), feedbackList);
        listView.setAdapter(messFeedbackAdapter);



        messFeedbackRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){
                    feedbackList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                         FeedbackUserClass feedback = snapshot.getValue(FeedbackUserClass.class);

                         if(feedback!=null){
                             feedbackList.add((feedback));
                         }
                    }
                    messFeedbackAdapter.notifyDataSetChanged();
                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                hideProgressDialog();
            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.setContentView(R.layout.add_feedback);
                dialog.setTitle(" Feedback ");

                final Spinner aboutSpinner =dialog.findViewById(R.id.add_feedback_spinner1);
                final Spinner daySpinner =dialog.findViewById(R.id.add_feedback_spinner2);
                final EditText titleEditText =dialog.findViewById(R.id.feedback_title);
                final RatingBar ratingBar=dialog.findViewById(R.id.add_feedback_ratings);
                final EditText descEditText=dialog.findViewById(R.id.add_feedback_desc);
                Button submit=dialog.findViewById(R.id.add_feedback_submit);

                ArrayAdapter<String> aboutAdapter = new ArrayAdapter<String>(
                        getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.feedbackAbout)
                );
                aboutAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
                aboutSpinner.setAdapter(aboutAdapter);

                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(
                        getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.dayOfWeek)
                );
                dayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
                daySpinner.setAdapter(dayAdapter);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        about = aboutSpinner.getSelectedItem().toString();
                        day=daySpinner.getSelectedItem().toString();
                        title=titleEditText.getText().toString();
                        Log.d("Title",title);
                        rating= Float.toString(ratingBar.getRating());
                        desc=descEditText.getText().toString();
                        Log.d("Desc",desc);

                        if(title.isEmpty()){
                            Toast.makeText(getActivity(),"Please fill Feedback title ", Toast.LENGTH_SHORT).show();
                            titleEditText.requestFocus();
                            return;
                        }
                        Calendar calendar=Calendar.getInstance();
                        String date = new SimpleDateFormat("dd MMM yy h:mm a", Locale.US).format(calendar.getTime());
                        String username = user.getDisplayName();
                        Uri photoUri=user.getPhotoUrl();

                        String image;
                        if(photoUri==null){
                            image = "https://drive.google.com/open?id=0BzHSfMqO1EIMdFZSMThJeEF3WUdxT05KTWo2bDFVZkxUbmk4";

                        }else{
                            image=photoUri.toString();
                        }



                        FeedbackUserClass newFeedback=new FeedbackUserClass(username,image,title,day,about,date,rating,desc);
                        String userReference= messFeedbackRef.push().getKey();
                        assert userReference != null;
                        messFeedbackRef.child(userReference).setValue(newFeedback);
                        dialog.dismiss();


                    }
                });

                dialog.show();

            }

        });

        Log.d(TAG, "onCreate: reached");





        return view;
    }

    public void showProgressDialog() {

        if (csDocProgressDialog == null) {
            csDocProgressDialog = new ProgressDialog(getActivity(),R.style.MyAlertDialogStyle);
            csDocProgressDialog.setMessage("Fetching feedbacks....");
            csDocProgressDialog.setIndeterminate(true);
            csDocProgressDialog.setCanceledOnTouchOutside(false);
        }

        csDocProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (csDocProgressDialog != null && csDocProgressDialog.isShowing()) {
            csDocProgressDialog.dismiss();
        }
    }


    public void onResume() {
        super.onResume();

    }

}
