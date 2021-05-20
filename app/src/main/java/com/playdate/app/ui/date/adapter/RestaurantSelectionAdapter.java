package com.playdate.app.ui.date.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.Restaurants;
import com.playdate.app.ui.date.fragments.FragRestaurantSelection;
import com.playdate.app.ui.date.games.FragTimesUp2;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;

import java.util.ArrayList;

public class RestaurantSelectionAdapter extends RecyclerView.Adapter<RestaurantSelectionAdapter.ViewHolder> {
    private ArrayList<Restaurants> list = new ArrayList<>();
    private FragRestaurantSelection frag;
    int selectedposition = -1;

    public RestaurantSelectionAdapter(FragRestaurantSelection frag) {
        this.frag = frag;

        list.add(new Restaurants("", R.drawable.rest1));
        list.add(new Restaurants("", R.drawable.rest2));
        list.add(new Restaurants("", R.drawable.rest4));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest5));
        list.add(new Restaurants("", R.drawable.rest6));
    }

    @NonNull
    @Override
    public RestaurantSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date_restaurant_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantSelectionAdapter.ViewHolder holder, int position) {
        holder.iv_rest.setImageResource(list.get(position).getImage());
        if (selectedposition == position) {
            holder.cl_rest.setBackgroundResource(R.drawable.btn_pink_filled);
            frag.restSelected();

        } else {
            holder.cl_rest.setBackgroundResource(R.drawable.login_button);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_rest;
        ConstraintLayout cl_rest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_rest = itemView.findViewById(R.id.iv_rest);
            cl_rest = itemView.findViewById(R.id.cl_rest);

            cl_rest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedposition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });
        }
    }
}
