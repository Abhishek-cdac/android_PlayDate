package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.dashboard.OnFriendSelected;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private ArrayList<MatchListUser> lst;
    private DashboardActivity dashboardActivity;

    public FriendAdapter(ArrayList<MatchListUser> lst, DashboardActivity dashboardActivity) {
        this.lst = lst;
        this.dashboardActivity = dashboardActivity;
    }

    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friends, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Picasso.get().load(lst.get(position).getProfilePicPath())
                .placeholder(R.drawable.profile)
                .into(holder.profile_image);

        holder.txt_friend_name.setText(lst.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        TextView txt_friend_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_friend_name = itemView.findViewById(R.id.txt_friend_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OnFriendSelected inf= dashboardActivity;
                    inf.OnSingleFriendSelected(lst.get(getAdapterPosition()).getUserId(),lst.get(getAbsoluteAdapterPosition()).getFriendId());
                }
            });
        }
    }
}
