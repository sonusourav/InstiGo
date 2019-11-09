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
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;


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

      String food = (m.getDay() + "  " + m.getPart());
      username.setText(m.getUsername());
      title.setText(m.getTitle());
        day.setText(food);
      date.setText(m.getDate());
      desc.setText(m.getDesc());
      ratingBar.setRating(m.getRatings());

      if (m.getUrl() != null) {
        Glide.with(mcontext)
            .load("" + m.getUrl())
            .into(circleImageView);
      } else {
        Glide.with(mcontext)
            .load(R.drawable.image_profile_pic)
            .into(circleImageView);
      }

      ratingBar.setRating(m.getRatings());
        return view;
    }
}
