package com.playdate.app.business.couponsGenerate.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.FragActiveCoupons;
import com.playdate.app.model.GetBusinessCouponData;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActiveCouponsAdapter extends RecyclerView.Adapter<ActiveCouponsAdapter.ViewHolder> {
    private final ArrayList<GetBusinessCouponData> list;

    private final Picasso picasso;
    String inputPattern = "yyyy-MM-dd";
    String outputPattern = "dd/MM";
    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

    public ActiveCouponsAdapter(ArrayList<GetBusinessCouponData> getBusinessCouponLst, FragActiveCoupons fragActiveCoupons) {
        this.list = getBusinessCouponLst;
        picasso = Picasso.get();
    }

    @NonNull
    @Override
    public ActiveCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_active_coupon, parent, false);
//        Context mcontext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveCouponsAdapter.ViewHolder holder, int position) {

        String[] s = list.get(position).getCouponValidTillDate().split("T");

//        Log.d("*******",list.get(position).getCouponValidTillDate());
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        //readDate = null;
        try {


            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(s[0]);
                str = outputFormat.format(date);
                holder.tv_date.setText(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        picasso.load(list.get(position).getCouponImage())
                .fit()
                .centerCrop()
                .into(holder.iv_image);
        holder.rest_name.setText(list.get(position).getUser().get(0).getFullName());
        holder.discount_desc.setText(list.get(position).getCouponTitle());
        if(list.get(position).getStatus().equals("Active"))
        holder.status.setText("\u2022 LIVE");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_image;
        private final TextView rest_name;
        private final TextView discount_desc;
        private final TextView tv_date;
        private final TextView status;
        private RelativeLayout rl_coupons;

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



