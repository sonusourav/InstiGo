package com.iitdh.sonusourav.instigo.Council;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.iitdh.sonusourav.instigo.R;
import java.io.Serializable;
import java.util.List;

public class CouncilAdapter extends RecyclerView.Adapter<CouncilAdapter.MyViewHolder> {

  private List<CouncilTeam> dataSet;
  private Context context;

  static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView teamName;
    TextView teamDesc;
    ImageView teamPic;

    MyViewHolder(View itemView) {
      super(itemView);
      this.teamName =  itemView.findViewById(R.id.council_team_name);
      this.teamDesc =  itemView.findViewById(R.id.council_team_desc);
      this.teamPic =  itemView.findViewById(R.id.council_team_image);
    }
  }

  CouncilAdapter(Context context,List<CouncilTeam> data) {
    this.context=context;
    this.dataSet = data;
  }

  @NonNull @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.activity_council_team, parent, false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder holder, final int listPosition) {

    TextView teamName = holder.teamName;
    TextView teamDesc = holder.teamDesc;
    ImageView teamPic=holder.teamPic;

    teamName.setText(dataSet.get(listPosition).getTeamName());
    teamDesc.setText(dataSet.get(listPosition).getTeamDesc());
    if(dataSet.get(listPosition).getTeamUrl()!=null){
      Glide.with(context)
          .load(dataSet.get(listPosition).getTeamUrl())
          .into(teamPic);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        Intent teamIntent=new Intent(context,CouncilTeamDetails.class);
        teamIntent.putExtra("Teams",dataSet.get(listPosition).getTeam());
        context.startActivity(teamIntent);
      }
    });

  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }
}