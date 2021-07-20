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
import com.playdate.app.ui.coupons.DialogSelectedRestaurant;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private ArrayList<Restaurant> lst;
    private Context mContext;

    public RestaurantListAdapter(ArrayList<Restaurant> lst) {
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
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friends, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder holder, int position) {


        try {
            picasso.load(lst.get(position).getImage())

                    .placeholder(R.color.color_grey)
                    .into(holder.profile_image);

            holder.txt_friend_name.setText(lst.get(position).getName());
            holder.profile_image.setOnClickListener(view -> new DialogSelectedRestaurant(mContext, lst.get(position).getName(),
                    lst.get(position).getImage()).show());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (null == lst) {
            return 0;
        }
        return lst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profile_image;
        private final TextView txt_friend_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_friend_name = itemView.findViewById(R.id.txt_friend_name);

        }
    }
}
