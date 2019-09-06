package com.shadybond.nhleague.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shadybond.nhleague.R;
import com.shadybond.nhleague.TeamPlayers;
import com.shadybond.nhleague.models.TeamsModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MainActivityRecyclerViewAdapter";

    private ArrayList<TeamsModel> teamsData;
    private Context context;

    public MainActivityRecyclerViewAdapter(ArrayList <TeamsModel> teamsData, Context context) {
        this.teamsData = teamsData;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setTeamsData(ArrayList<TeamsModel> teamsData){
        this.teamsData = teamsData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_main_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return teamsData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context)
                .asBitmap()
                .load(teamsData.get(position).returnUrlTeamImage())
                .into(holder.circleImageView);

        holder.textView.setText(teamsData.get(position).returnTeamName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,String.valueOf(teamsData.get(position).returnTeamId()),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), TeamPlayers.class);
                intent.putExtra("id",teamsData.get(position).returnTeamId());
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView textView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circularImageViewMain);
            textView = itemView.findViewById(R.id.textViewDataMain);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutDataMain);
        }
    }

}
