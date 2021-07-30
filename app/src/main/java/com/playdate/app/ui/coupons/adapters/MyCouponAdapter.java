package com.playdate.app.ui.coupons.adapters;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.playdate.app.model.MyCoupons;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.ViewHolder> {

    private final List<MyCoupons> list;
    private final Picasso picasso;
    private Context mContext;

    public MyCouponAdapter(List<MyCoupons> lst) {
        this.list = lst;
        picasso = Picasso.get();
    }

    @NonNull
    @Override
    public MyCouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_my_coupons, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCouponAdapter.ViewHolder holder, int position) {
        try {
            holder.rest_name.setText(list.get(position).getCouponTitle());
            picasso.load(list.get(position).getRestaurants().get(0).getImage())
                    .placeholder(R.color.color_grey)

                    .into(holder.iv_image);

            holder.discount_desc.setText(list.get(position).getCouponDescription());
            String[] str = list.get(position).getCouponValidTillDate().split("T");
            holder.valid.setText(str[0]);
            holder.coupon_code.setText(list.get(position).getCouponCode());
            holder.rl_code.setOnClickListener(v -> copyToClipBoard(list.get(position).getCouponCode()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyToClipBoard(String couponCode) {
        if (null != couponCode) {
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("CouponCode", couponCode);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mContext, "Coupon Code Copied", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_image;
        private final RelativeLayout rl_code;
        private final TextView rest_name;
        private final TextView discount_desc;
        private final TextView valid;
        private final TextView coupon_code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_code = itemView.findViewById(R.id.rl_code);
            iv_image = itemView.findViewById(R.id.iv_image);
            rest_name = itemView.findViewById(R.id.rest_name);
            discount_desc = itemView.findViewById(R.id.discount_desc);
            valid = itemView.findViewById(R.id.discount_valid_date);
            coupon_code = itemView.findViewById(R.id.tv_coupon_code);
        }
    }
}
