package com.playdate.app.ui.date.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PartnerImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderAllTimeAdapter extends RecyclerView.Adapter<LeaderAllTimeAdapter.ViewHolder> {
    ArrayList<PartnerImage> list;

    public LeaderAllTimeAdapter(ArrayList<PartnerImage> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public LeaderAllTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leaders, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderAllTimeAdapter.ViewHolder holder, int position) {
        holder.tv_leader_points.setText(list.get(position).getPoints());
        holder.tv_leader_username.setText(list.get(position).getName());
        holder.tv_position.setText(list.get(position).getPosition());
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.cupertino_activity_indicator).into(holder.iv_leader_image);
        if (list.get(position).isIncreased()) {
            holder.iv_polygon.setImageResource(R.drawable.polygon1);
        } else {
            holder.iv_polygon.setImageResource(R.drawable.polygon2);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_polygon;
        ImageView iv_leader_image;
        TextView tv_position;
        TextView tv_leader_username;
        TextView tv_leader_points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_polygon = itemView.findViewById(R.id.iv_polygon);
            iv_leader_image = itemView.findViewById(R.id.iv_leader_image);
            tv_position = itemView.findViewById(R.id.tv_position);
            tv_leader_username = itemView.findViewById(R.id.tv_leader_username);
            tv_leader_points = itemView.findViewById(R.id.tv_leader_points);
        }
    }
}
