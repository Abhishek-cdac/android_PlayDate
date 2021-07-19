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
import com.playdate.app.ui.coupons.DialogSelectedRestaurant;
import com.playdate.app.ui.coupons.FragCouponStore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CouponStoreAdapter extends RecyclerView.Adapter<CouponStoreAdapter.ViewHolder> {
    private final ArrayList<GetCouponsData> coupon_list;
    private final Onclick itemClick;
    private final Picasso picasso;
    private FragCouponStore ref;
    private DialogSelectedRestaurant reff;
    private int currentPoints;
    boolean fromDialog;


    public CouponStoreAdapter(ArrayList<GetCouponsData> lst_getCoupons, Onclick itemClick, boolean fromDialog) {
        this.coupon_list = lst_getCoupons;
        this.itemClick = itemClick;
        picasso = Picasso.get();
        this.fromDialog = fromDialog;
    }

    public void setListerner(FragCouponStore ref) {
        this.ref = ref;
    }

    public void setListerner(DialogSelectedRestaurant ref) {
        this.reff = ref;
    }

    Context mContext;

    @NonNull
    @Override
    public CouponStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_coupon_store, parent, false));
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


        holder.rl_coupons.setOnClickListener(v -> {
            if (coupon_list.get(position).getPurchased() != null) {
                if (coupon_list.get(position).getPurchased().size() > 0) {
                    if (fromDialog) {
                        reff.showMsg();
                    } else {
                        ref.showMsg();
                    }
                } else {
                    if (currentPoints >= coupon_list.get(position).getCouponPurchasePoint()) {
                        itemClick.onItemClicks(v, position, 11, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());
                    } else {
                        itemClick.onItemClicks(v, position, 12, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());
//                            ref.showMsgNoBal();
                    }

                }
            } else {
                if (currentPoints >= coupon_list.get(position).getCouponPurchasePoint()) {
                    itemClick.onItemClicks(v, position, 11, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());
                } else {
                    itemClick.onItemClicks(v, position, 12, coupon_list.get(position).getCouponId(), coupon_list.get(position).getCouponCode(), "" + coupon_list.get(position).getCouponPurchasePoint());

//                        ref.showMsgNoBal();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
