package com.playdate.app.ui.date.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GameCoin;
import com.playdate.app.model.StoreItems;
import com.playdate.app.ui.chat.request.Onclick;

import java.util.ArrayList;
import java.util.List;

public class StoreMultiplierAdapter extends RecyclerView.Adapter<StoreMultiplierAdapter.ViewHolder> {

    List<StoreItems> multiplier_items;
    Onclick itemclick;

    public StoreMultiplierAdapter(ArrayList<StoreItems> game_items, Onclick itemclick) {
        this.multiplier_items = game_items;
        this.itemclick = itemclick;
    }

    @NonNull
    @Override
    public StoreMultiplierAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_card, parent, false);
        return new StoreMultiplierAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_amount.setText(multiplier_items.get(position).getValue());
        holder.tv_total_dollar.setText("$" + multiplier_items.get(position).getAmount());
        if (holder.tv_amount.getText().equals("1")) {
            holder.iv_coins.setImageResource(R.drawable.multi_coin);
        } else{
            holder.iv_coins.setImageResource(R.drawable.multiplier3);
        }
    }

    @Override
    public int getItemCount() {
        return multiplier_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_coins;
        TextView tv_amount;
        TextView tv_total_dollar;
        ConstraintLayout cl_store;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_coins = itemView.findViewById(R.id.iv_coins);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_total_dollar = itemView.findViewById(R.id.tv_total_dollar);
            cl_store = itemView.findViewById(R.id.cl_store);
        }
    }
}
