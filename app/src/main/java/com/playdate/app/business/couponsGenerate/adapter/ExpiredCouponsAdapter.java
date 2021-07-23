package com.playdate.app.business.couponsGenerate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GetBusinessCouponData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExpiredCouponsAdapter extends RecyclerView.Adapter<ExpiredCouponsAdapter.ViewHolder> {
    private final ArrayList<GetBusinessCouponData> list;
    private final Picasso picasso;

    public ExpiredCouponsAdapter(ArrayList<GetBusinessCouponData> lst) {
        this.list = lst;
        picasso = Picasso.get();
    }


    @NonNull
    @Override
    public ExpiredCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_expired_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpiredCouponsAdapter.ViewHolder holder, int position) {

        picasso.load(list.get(position).getCouponImage())
                .fit()
                .centerCrop()
                .into(holder.iv_image);
        holder.rest_name.setText(list.get(position).getUser().get(0).getFullName());
        holder.discount_desc.setText(list.get(position).getCouponTitle());
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;

        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_image;
        private final TextView rest_name;
        private final TextView discount_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);

        }
    }
}
