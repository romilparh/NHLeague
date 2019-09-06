package com.shadybond.nhleague.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shadybond.nhleague.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerNationalityRVAdapter  extends RecyclerView.Adapter<PlayerNationalityRVAdapter.ViewHolder>  {
    private static final String TAG = "TeamsPlayerRVAdapter";

    ArrayList<String> url,nationalityName;
    private Context context;

    public PlayerNationalityRVAdapter(ArrayList<String> url,ArrayList<String> nationalityName, Context context) {
        this.context = context;
        this.url = url;
        this.nationalityName = nationalityName;
        notifyDataSetChanged();
    }

    public void setNationalityData(ArrayList<String> url,ArrayList<String> nationalityName ){
        this.url = url;
        this.nationalityName = nationalityName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_nationality_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return nationalityName.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context)
                .asBitmap()
                .load(url.get(position))
                .into(holder.circleImageView);
        holder.textViewNationality.setText(this.nationalityName.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNationality;
        CircleImageView circleImageView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            circleImageView = itemView.findViewById(R.id.circularImageViewNationality);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutNationality);
        }
    }
}
