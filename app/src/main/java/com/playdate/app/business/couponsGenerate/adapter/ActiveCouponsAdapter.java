package com.playdate.app.business.couponsGenerate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.ActiveCoupons;

import java.util.ArrayList;

public class ActiveCouponsAdapter extends RecyclerView.Adapter<ActiveCouponsAdapter.ViewHolder> {
    ArrayList<ActiveCoupons> list = new ArrayList<>();
    Context mcontext;


    public ActiveCouponsAdapter() {
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
    public ActiveCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_active_coupon, parent, false);
        mcontext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveCouponsAdapter.ViewHolder holder, int position) {
        holder.iv_image.setImageResource(list.get(position).getImage());
        holder.tv_date.setText(list.get(position).getExpiryDate());
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
        RelativeLayout rl_coupons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            tv_date = itemView.findViewById(R.id.tv_date);
            rl_coupons = itemView.findViewById(R.id.rl_coupons);
//            rl_coupons.setOnTouchListener(new OnSwipeTouchListener(mcontext){
//                @Override
//                public void onSwipeLeft() {
//                    super.onSwipeLeft();
//                    Toast.makeText(mcontext, "SWIPE LEFT", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSwipeRight() {
//                    super.onSwipeRight();
//                    Toast.makeText(mcontext, "SWIPE RIGHT", Toast.LENGTH_SHORT).show();
//
//                }
//            });

        }
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

}
