package com.playdate.app.ui.coupons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.playdate.app.R;
import com.playdate.app.model.GetCouponsData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.FragCouponStore;
import com.playdate.app.util.common.CommonClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CouponStoreAdapter extends RecyclerView.Adapter<CouponStoreAdapter.ViewHolder> {
    private ArrayList<GetCouponsData> coupon_list = new ArrayList<>();
    private final Onclick itemClick;
    private final Picasso picasso;
    private CommonClass clsCommon;
    private FragCouponStore ref;
    private int currentPoints;


    public CouponStoreAdapter(ArrayList<GetCouponsData> lst_getCoupons, Onclick itemClick) {
        this.coupon_list = lst_getCoupons;
        this.itemClick = itemClick;
        picasso = Picasso.get();
        clsCommon = new CommonClass();

//        coupon_list.add(new CouponStore("Tiki Village", "Enjoy 15% OFF on our specials meals and drinks", "100", "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fwww.patnabeats.com%2Fwp-content%2Fuploads%2F2018%2F12%2FLazeez-Gold-3.jpg&imgrefurl=https%3A%2F%2Fwww.patnabeats.com%2Fhave-an-amazing-experience-of-lazeez-cuisines-in-a-gufaa-themed-restaurant-in-patna%2F&tbnid=FJg_OjSYjHeSTM&vet=12ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ..i&docid=w0ba22AtaqsSGM&w=1024&h=678&q=restaurant&ved=2ahUKEwij-uD8yMPwAhVCSisKHclCCDUQMygAegUIARDQAQ"));
    }

    public void setListerner(FragCouponStore ref) {
        this.ref = ref;
    }

    Context mContext;

    @NonNull
    @Override
    public CouponStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_coupon_store, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponStoreAdapter.ViewHolder holder, int position) {

        holder.discount_desc.setText(coupon_list.get(position).getCouponDescription());
        holder.points.setText("" + coupon_list.get(position).getCouponPurchasePoint());
        try {
            holder.rest_name.setText(coupon_list.get(position).getCouponTitle());
            picasso.load(coupon_list.get(position).getRestaurants().get(0).getImage())
                    .placeholder(R.color.color_grey)

                    .into(holder.iv_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (coupon_list.get(position).getPurchased() != null) {
            if (coupon_list.get(position).getPurchased().size() > 0) {
                holder.animationView.setVisibility(View.VISIBLE);
            } else {
                holder.animationView.setVisibility(View.GONE);
            }
        } else {
            holder.animationView.setVisibility(View.GONE);
        }


        holder.rl_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coupon_list.get(position).getPurchased() != null) {
                    if (coupon_list.get(position).getPurchased().size() > 0) {
                        ref.showMsg();
                    } else {
                        if (currentPoints >= coupon_list.get(position).getCouponPurchasePoint()) {
                            itemClick.onItemClicks(v, position, 11, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());
                        } else {
                            ref.showMsgNoBal();
                        }

                    }
                } else {
                    if (currentPoints >= coupon_list.get(position).getCouponPurchasePoint()) {
                        itemClick.onItemClicks(v, position, 11, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());
                    } else {
                        ref.showMsgNoBal();
                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return coupon_list.size();
    }

    public void setCurrentPoints(int points) {
        this.currentPoints = points;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView rest_name, discount_desc, points;
        RelativeLayout rl_coupons;
        LottieAnimationView animationView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            animationView = itemView.findViewById(R.id.animationView);
            rl_coupons = itemView.findViewById(R.id.rl_coupons);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            points = itemView.findViewById(R.id.points);
        }
    }
}
