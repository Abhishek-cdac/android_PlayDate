package com.playdate.app.business.couponsGenerate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.ActiveCoupons;

import java.util.ArrayList;

public class ExpiredCouponsAdapter extends RecyclerView.Adapter<ExpiredCouponsAdapter.ViewHolder> {
    ArrayList<ActiveCoupons> list = new ArrayList<>();


    public ExpiredCouponsAdapter() {
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "24/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "18/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "16/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "14/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "10/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "08/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "06/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "04/09", R.drawable.rest_1));
        list.add(new ActiveCoupons("Retro Dinner", "Enjoy 15% off on our special meals and drinks", "02/09", R.drawable.rest_1));
    }

    @NonNull
    @Override
    public ExpiredCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_expired_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpiredCouponsAdapter.ViewHolder holder, int position) {
        holder.iv_image.setImageResource(list.get(position).getImage());
        holder.rest_name.setText(list.get(position).getName());
        holder.discount_desc.setText(list.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView rest_name;
        TextView discount_desc;
        TextView tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);

        }
    }
}