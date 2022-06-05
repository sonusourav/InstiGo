package com.iitdh.sonusourav.instigo.Mess;


import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
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
    private DatabaseReference messFeedbackRef;
    private ShimmerFrameLayout mShimmerViewContainer;

    private String about;
    private String day;
    private String title;
    private String rating;
    private String desc;


    private String TAG = FragmentFeedback.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_feedback, null);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        mShimmerViewContainer.startShimmer();

        ListView listView = view.findViewById(R.id.mess_feedback_listview);
        feedbackList = new ArrayList<>();
        FloatingActionButton fabButton = view.findViewById(R.id.mess_feedback_fab);

        FirebaseAuth ecAuth = FirebaseAuth.getInstance();
        FirebaseDatabase feedbackInstance = FirebaseDatabase.getInstance();
        DatabaseReference feedbackRootRef = feedbackInstance.getReference("Mess");
        messFeedbackRef = feedbackRootRef.child("Feedback").getRef();
        final FirebaseUser user = ecAuth.getCurrentUser();

        assert user != null;

        messFeedbackAdapter = new MessFeedbackAdapter(getActivity(), feedbackList);
        listView.setAdapter(messFeedbackAdapter);

        messFeedbackRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    feedbackList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: reached");
                        FeedbackUserClass feedback = snapshot.getValue(FeedbackUserClass.class);

                        if (feedback != null) {
                            feedbackList.add((feedback));
                        }
                    }
                    messFeedbackAdapter.notifyDataSetChanged();
                    mShimmerViewContainer.setVisibility(View.GONE);
                } else {
                    mShimmerViewContainer.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e(TAG, "Failed to read value.", databaseError.toException());
                mShimmerViewContainer.setVisibility(View.GONE);

            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
                dialog.setContentView(R.layout.add_feedback);
                dialog.setTitle(" Feedback ");

                final Spinner aboutSpinner = dialog.findViewById(R.id.add_feedback_spinner1);
                final Spinner daySpinner = dialog.findViewById(R.id.add_feedback_spinner2);
                final EditText titleEditText = dialog.findViewById(R.id.feedback_title);
                final RatingBar ratingBar = dialog.findViewById(R.id.add_feedback_ratings);
                final EditText descEditText = dialog.findViewById(R.id.add_feedback_desc);
                Button submit = dialog.findViewById(R.id.add_feedback_submit);

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
                        day = daySpinner.getSelectedItem().toString();
                        title = titleEditText.getText().toString();
                        Log.d("Title", title);
                        rating = Float.toString(ratingBar.getRating());
                        desc = descEditText.getText().toString();
                        Log.d("Desc", desc);

                        if (title.isEmpty()) {
                            Toast.makeText(getActivity(), "Please fill Feedback title ", Toast.LENGTH_SHORT).show();
                            titleEditText.requestFocus();
                            return;
                        }
                        Calendar calendar = Calendar.getInstance();
                        String date = new SimpleDateFormat("dd MMM yy h:mm a", Locale.US).format(calendar.getTime());
                        String username = user.getDisplayName();
                        Uri photoUri = user.getPhotoUrl();

                        String image;
                        if (photoUri == null) {
                            image = "https://drive.google.com/open?id=0BzHSfMqO1EIMdFZSMThJeEF3WUdxT05KTWo2bDFVZkxUbmk4";

                        } else {
                            image = photoUri.toString();
                        }


                        FeedbackUserClass newFeedback = new FeedbackUserClass(username, image, title, day, about, date, rating, desc);
                        String userReference = messFeedbackRef.push().getKey();
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


    public void onResume() {
        super.onResume();

    }

}
