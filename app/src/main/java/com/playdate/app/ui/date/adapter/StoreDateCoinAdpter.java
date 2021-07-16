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

public class StoreDateCoinAdpter extends RecyclerView.Adapter<StoreDateCoinAdpter.ViewHolder> {
    List<StoreItems> date_items;
    Onclick itemclick;

    public StoreDateCoinAdpter(ArrayList<StoreItems> game_items, Onclick itemclick) {
        this.date_items = game_items;
        this.itemclick = itemclick;

    }

    @NonNull
    @Override
    public StoreDateCoinAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_card, parent, false);
        return new StoreDateCoinAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_amount.setText(date_items.get(position).getValue());
        holder.tv_total_dollar.setText("$" + date_items.get(position).getAmount());
        if (holder.tv_amount.getText().equals("1")) {
            holder.iv_coins.setImageResource(R.drawable.date_coin);
        } else {
            holder.iv_coins.setImageResource(R.drawable.datecoin3);
        }
    }


    @Override
    public int getItemCount() {
        return date_items.size();
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
