package com.iitdh.sonusourav.instigo.Complaints;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.iitdh.sonusourav.instigo.R;
import java.util.ArrayList;


public class ComplaintsAdapter extends BaseAdapter {


    private Context mcontext;
    private ArrayList<ComplainItemClass> ComplainList;

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

      final ComplainItemClass complaint = ComplainList.get(i);

        TextView complainType=view.findViewById(R.id.status_complain_type);
        TextView complainDate=view.findViewById(R.id.status_complain_date);
        TextView complainTitle=view.findViewById(R.id.status_complain_title);
        TextView complainStatus=view.findViewById(R.id.status_complain);
        TextView complainDetails=view.findViewById(R.id.status_complain_details);

      complainDate.setText(complaint.getComplainDateCreated());
      complainType.setText(complaint.getComplainRelated());
      complainTitle.setText(complaint.getComplainTitle());
      complainStatus.setText(complaint.getComplainStatus() + "");
        complainDetails.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            Intent detailsIntent = new Intent(mcontext, StatusDetails.class);
            detailsIntent.putExtra("complaint", complaint);
            mcontext.startActivity(detailsIntent);
            }
        });

      return view;
    }
}
