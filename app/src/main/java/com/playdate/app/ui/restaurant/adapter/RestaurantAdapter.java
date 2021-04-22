package com.playdate.app.ui.restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    ArrayList<Restaurant> lst = new ArrayList<>();

    public RestaurantAdapter() {
        lst.add(new Restaurant(R.drawable.rest1, false));
        lst.add(new Restaurant(R.drawable.rest2, false));
        lst.add(new Restaurant(R.drawable.rest3, false));
        lst.add(new Restaurant(R.drawable.rest4, false));
        lst.add(new Restaurant(R.drawable.rest5, false));
        lst.add(new Restaurant(R.drawable.rest6, false));
    }

    Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurant, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageview.setImageResource(lst.get(position).image);
        if (lst.get(position).isSelected()) {
            holder.imageview.setBackground(mContext.getDrawable(R.drawable.grid_back_sel));
        } else {
            holder.imageview.setBackground(mContext.getDrawable(R.drawable.grid_back));
        }
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lst.get(getAdapterPosition()).isSelected()){
                        lst.get(getAdapterPosition()).selected = false;
                    }else{
                        lst.get(getAdapterPosition()).selected = true;
                    }

                    notifyDataSetChanged();
                }
            });
        }
    }
}
