package com.iitdh.sonusourav.instigo.Resources.EE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iitdh.sonusourav.instigo.R;
import com.iitdh.sonusourav.instigo.Resources.CourseClass;

import java.util.ArrayList;
import java.util.List;


public class EECourseAdapter extends BaseAdapter {


    private Context mContext;
    private List<CourseClass> courseList;


     EECourseAdapter(Context context, ArrayList<CourseClass> arrayList) {
        this.mContext = context;
        this.courseList = arrayList;
    }



    public int getCount() {
        return courseList.size();
    }

    public Object getItem(int pos) {
        return courseList.get(pos);
    }


    public long getItemId(int i) {
        return i;
    }


    public View getView(final int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.course_ee_cell, viewGroup, false);
        }

        TextView courseNo=view.findViewById(R.id.course_ee_name);
        TextView courseName=view.findViewById(R.id.course_ee_no);

        final CourseClass course = courseList.get(i);

        courseNo.setText(course.getCourseNo());
        courseName.setText(course.getCourseName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent=new Intent(mContext,EEResourceDocs.class);
               intent.putExtra("eeCourseName",course.getCourseName());
               intent.putExtra("eeCourseNo",course.getCourseNo());
               mContext.startActivity(intent);

            }
        });
        return view;
    }
}
