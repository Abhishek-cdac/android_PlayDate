package com.playdate.app.ui.coupons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GetCouponsData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.ViewHolder> {
    //    ArrayList<MyCoupons> list = new ArrayList<>();
    List<GetCouponsData> list;
    Picasso picasso;

    public MyCouponAdapter(List<GetCouponsData> lst) {
        this.list = lst;
        picasso = Picasso.get();
    }

    @NonNull
    @Override
    public MyCouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_coupons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCouponAdapter.ViewHolder holder, int position) {
        try {
            holder.rest_name.setText(list.get(position).getCouponTitle());
            picasso.load(list.get(position).getRestaurants().get(0).getImage()).into(holder.iv_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.discount_desc.setText(list.get(position).getCouponDescription());
        String str[]=list.get(position).getCouponValidTillDate().split("T");
        holder.valid.setText(str[0]);
        holder.coupon_code.setText(list.get(position).getCouponCode());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView rest_name, discount_desc, valid, coupon_code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            valid = itemView.findViewById(R.id.discount_valid_date);
            coupon_code = itemView.findViewById(R.id.tv_coupon_code);
        }
    }
}
