package com.playdate.app.business.couponsGenerate.adapter;

import android.content.Context;
import android.util.Log;
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
import com.playdate.app.business.couponsGenerate.FragActiveCoupons;
import com.playdate.app.model.ActiveCoupons;
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActiveCouponsAdapter extends RecyclerView.Adapter<ActiveCouponsAdapter.ViewHolder> {
    //  ArrayList<ActiveCoupons> list = new ArrayList<>();
    ArrayList<GetBusinessCouponData> list = new ArrayList<>();
    FragActiveCoupons fragActiveCoupons;
    Context mcontext;

    private final Picasso picasso;

    public ActiveCouponsAdapter(ArrayList<GetBusinessCouponData> getBusinessCouponLst, FragActiveCoupons fragActiveCoupons) {
        this.list = getBusinessCouponLst;
        this.fragActiveCoupons = fragActiveCoupons;
        picasso = Picasso.get();
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
        if (list.get(position).getCouponValidTillDate() != null) {
            String[] s = list.get(position).getCouponValidTillDate().split("T");
            Log.e("getCouponValidTillDate", "" + s[0]);
            holder.tv_date.setText(s[0]);
        }

        picasso.load(list.get(position).getCouponImage()).placeholder(R.drawable.rest_1)
                .fit()
                .centerCrop()
                .into(holder.iv_image);
        //  holder.tv_date.setText(list.get(position).getCouponValidTillDate());
        holder.rest_name.setText(list.get(position).getUser().get(0).getFullName());
        holder.discount_desc.setText(list.get(position).getCouponTitle());
        holder.status.setText(list.get(position).getStatus());
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
        TextView status;
        RelativeLayout rl_coupons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            status = itemView.findViewById(R.id.status);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            tv_date = itemView.findViewById(R.id.tv_date);
            rl_coupons = itemView.findViewById(R.id.rl_coupons);
        }
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

}



