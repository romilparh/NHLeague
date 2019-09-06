package com.shadybond.nhleague.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shadybond.nhleague.PlayerNationality;
import com.shadybond.nhleague.R;
import com.shadybond.nhleague.TeamPlayers;
import java.util.ArrayList;

public class TeamPlayersRVAdapter extends RecyclerView.Adapter<TeamPlayersRVAdapter.ViewHolder> {
    private static final String TAG = "TeamsPlayerRVAdapter";

    ArrayList<String> playersNameData,playersJerseyNumberData, playersPositionData;
    ArrayList<Integer> id;
    private Context context;

    public TeamPlayersRVAdapter(ArrayList<String> playersNameData, ArrayList<String> playersJerseyNumberData, ArrayList<String> playersPositionData,ArrayList<Integer> id, Context context) {
        this.playersNameData = playersNameData;
        this.playersJerseyNumberData = playersJerseyNumberData;
        this.playersPositionData = playersPositionData;
        this.context = context;
        this.id = id;
        notifyDataSetChanged();
    }

    public void setPlayersData(ArrayList<String> playersNameData, ArrayList<String> playersJerseyNumberData, ArrayList<String> playersPositionData,ArrayList<Integer> id){
        this.playersNameData = playersNameData;
        this.playersJerseyNumberData = playersJerseyNumberData;
        this.playersPositionData = playersPositionData;
        this.id = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_team_players, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return playersNameData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textViewName.setText(this.playersNameData.get(position));
        holder.textViewJerseyNumber.setText(this.playersJerseyNumberData.get(position));
        holder.textViewPosition.setText(this.playersPositionData.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,String.valueOf(id.get(position)),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PlayerNationality.class);
                intent.putExtra("id",id.get(position));
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewJerseyNumber,textViewName,textViewPosition;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJerseyNumber = itemView.findViewById(R.id.textViewJerseyNumber);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPosition = itemView.findViewById(R.id.textViewPosition);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutDataPlayers);
        }
    }

}
