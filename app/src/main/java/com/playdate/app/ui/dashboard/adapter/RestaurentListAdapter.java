package com.playdate.app.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.model.RestaurentData;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.dashboard.OnFriendSelected;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurentListAdapter extends RecyclerView.Adapter<RestaurentListAdapter.ViewHolder> {

    private ArrayList<Restaurant> lst;

    public RestaurentListAdapter(ArrayList<Restaurant> lst) {
        this.lst = lst;
        picasso = Picasso.get();
    }

    public void updateList(ArrayList<Restaurant> lst) {
        this.lst = lst;
        notifyDataSetChanged();
    }

    private final Picasso picasso;

    @NonNull
    @Override
    public RestaurentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friends, parent, false);

        return new RestaurentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurentListAdapter.ViewHolder holder, int position) {


        picasso.load(lst.get(position).getImage())
                // .placeholder(R.drawable.profile)
                .into(holder.profile_image);

        holder.txt_friend_name.setText(lst.get(position).getName());
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
            //      itemView.setOnClickListener(v -> ((OnFriendSelected) dashboardActivity).OnSingleFriendSelected(lst.get(getAdapterPosition()).getId(), lst.get(getAbsoluteAdapterPosition()).get()));
        }
    }
}
