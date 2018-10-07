package com.iitdh.sonusourav.instigo.Mess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessFeedbackAdapter extends BaseAdapter {

    private Context mcontext;
    private ArrayList<FeedbackUserClass> FeedbackList;

    MessFeedbackAdapter(Context context, ArrayList<FeedbackUserClass> arrayList) {
        this.mcontext = context;
        this.FeedbackList = arrayList;
    }


    @Override
    public int getCount() {
        return FeedbackList.size();
    }

    @Override
    public Object getItem(int pos) {
        return FeedbackList.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.item_mess_feedback, viewGroup, false);
        }

        FeedbackUserClass m = FeedbackList.get(i);

        CircleImageView circleImageView=view.findViewById(R.id.feedback_imageView);
        TextView username=view.findViewById(R.id.mess_feedback_username);
        TextView title =view.findViewById(R.id.mess_feedback_title);
        TextView day=view.findViewById(R.id.mess_feedback_day);
        TextView date=view.findViewById(R.id.mess_feedback_date);
        RatingBar ratingBar=view.findViewById(R.id.mess_feedback_rating);
        TextView desc=view.findViewById(R.id.mess_feedback_desc);

        String food=(m.getFeedbackDay() + "  "+ m.getFeedbackPart());
        username.setText(m.getFeedbackUsername());
        title.setText(m.getFeedbackTitle());
        day.setText(food);
        date.setText(m.getFeedbackDate());
        desc.setText(m.getFeedbackDesc());
        ratingBar.setRating(Float.parseFloat(m.getFeedbackRatings()));

        Glide.with(mcontext)
                .load("" +m.getFeedbackUri())
                .into(circleImageView);

        ratingBar.setRating(Float.parseFloat(m.getFeedbackRatings()));
        return view;
    }
}
