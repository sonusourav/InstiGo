package com.iitdh.sonusourav.instigo.Resources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.Council.CouncilTeam;
import com.iitdh.sonusourav.instigo.Council.CouncilTeamDetails;
import com.iitdh.sonusourav.instigo.R;
import com.squareup.okhttp.internal.Internal;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

  private List<CourseClass> dataSet;
  private Context context;

  static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView courseCode;
    TextView courseName;
    ImageView csCoursePic;
    ImageView eeCoursePic;
    ImageView meCoursePic;

    MyViewHolder(View itemView) {
      super(itemView);
      this.courseCode =  itemView.findViewById(R.id.course_code);
      this.courseName =  itemView.findViewById(R.id.course_name);
      this.csCoursePic =  itemView.findViewById(R.id.course_cs_pic);
      this.eeCoursePic =  itemView.findViewById(R.id.course_ee_pic);
      this.meCoursePic =  itemView.findViewById(R.id.course_me_pic);

    }
  }

  CourseAdapter(Context context,List<CourseClass> data) {
    this.context=context;
    this.dataSet = data;
  }

  @NonNull @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.course_card_view, parent, false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {

    TextView courseName = holder.courseName;
    TextView courseCode = holder.courseCode;
    ImageView csCoursePic=holder.csCoursePic;
    ImageView eeCoursePic=holder.eeCoursePic;
    ImageView meCoursePic=holder.meCoursePic;

    courseName.setText(dataSet.get(listPosition).getCourseName());
    courseCode.setText(dataSet.get(listPosition).getCourseCode());

    for (String branch :dataSet.get(listPosition).getBranch()) {
     int i=Integer.parseInt(branch);
      switch (i){
        case 1: csCoursePic.setVisibility(View.VISIBLE);
          csCoursePic.setImageResource(R.drawable.icon_cs);
          break;
        case 2: eeCoursePic.setVisibility(View.VISIBLE);
          eeCoursePic.setImageResource(R.drawable.icon_ee);
          break;
        case 3: meCoursePic.setVisibility(View.VISIBLE);
          meCoursePic.setImageResource(R.drawable.icon_mech);
          break;
        default: csCoursePic.setVisibility(View.VISIBLE);
          csCoursePic.setImageResource(R.drawable.icon_other_course);
          break;

      }
    }



    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        Intent courseIntent=new Intent(context,DocumentsActivity.class);
        courseIntent.putExtra("CourseCode",dataSet.get(listPosition).getCourseCode());
        context.startActivity(courseIntent);
      }
    });

  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }
}