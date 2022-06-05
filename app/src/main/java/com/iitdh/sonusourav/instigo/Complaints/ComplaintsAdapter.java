package com.iitdh.sonusourav.instigo.Complaints;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iitdh.sonusourav.instigo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ComplaintsAdapter extends BaseAdapter {


    private Context mcontext;
    private ArrayList<ComplainItemClass> ComplainList;
    Calendar calendar;
    String time;
    String date;

     ComplaintsAdapter(Context context, ArrayList<ComplainItemClass> arrayList) {
        this.mcontext = context;
        this.ComplainList = arrayList;
    }



    public int getCount() {
        return ComplainList.size();
    }

    public Object getItem(int pos) {
        return ComplainList.get(pos);
    }


    public long getItemId(int i) {
        return i;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.status_item, viewGroup, false);
        }

        calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss a",Locale.US);
        final ComplainItemClass complainList = ComplainList.get(i);

        TextView complainType=view.findViewById(R.id.status_complain_type);
        TextView complainDate=view.findViewById(R.id.status_complain_date);
        TextView complainTitle=view.findViewById(R.id.status_complain_title);
        TextView complainStatus=view.findViewById(R.id.status_complain);
        TextView complainDetails=view.findViewById(R.id.status_complain_details);



        date=dateFormat.format(complainList.getComplainTime());
        time=timeFormat.format(complainList.getComplainTime());

        complainDate.setText(date);
        complainType.setText(complainList.getComplainType());
        complainTitle.setText(complainList.getComplainTitle());

        if(complainList.getStatus()==1){
            complainStatus.setText("Resolved");
            complainStatus.setBackgroundColor(mcontext.getResources().getColor(R.color.green));

        }else
            complainStatus.setText("Pending");

        complainDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext,StatusDetails.class);
                intent.putExtra("complain", complainList);
                mcontext.startActivity(intent);
            }
        });

        return view;
    }
}
