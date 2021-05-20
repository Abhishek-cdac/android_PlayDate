package com.playdate.app.ui.my_profile_details.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.squareup.picasso.Picasso;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_payments, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load("https://sm.pcmag.com/pcmag_in/review/p/paypal/paypal_mb8k.png")
                .into(holder.iv_payment);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_payment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_payment=itemView.findViewById(R.id.iv_payment);
        }
    }
}
