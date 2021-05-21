package com.playdate.app.ui.date.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.GameCoin;

import java.util.ArrayList;

public class StoreDateCoinAdpter extends RecyclerView.Adapter<StoreDateCoinAdpter.ViewHolder> {

    ArrayList<GameCoin> game_list = new ArrayList<>();

    public StoreDateCoinAdpter() {
        game_list.add(new GameCoin("1", "$0.99"));
        game_list.add(new GameCoin("3", "$0.99"));
        game_list.add(new GameCoin("10", "$0.99"));
        game_list.add(new GameCoin("20", "$0.99"));
        game_list.add(new GameCoin("50", "$0.99"));
        game_list.add(new GameCoin("100", "$0.99"));


    }

    @NonNull
    @Override
    public StoreDateCoinAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_card, parent, false);
        return new StoreDateCoinAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_amount.setText(game_list.get(position).getAmount());
        if (holder.tv_amount.getText() == "1") {
            holder.iv_coins.setImageResource(R.drawable.date_coin);
        } else {
            holder.iv_coins.setImageResource(R.drawable.datecoin3);
        }
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_coins;
        TextView tv_amount;
        TextView tv_total_dollar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_coins = itemView.findViewById(R.id.iv_coins);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_total_dollar = itemView.findViewById(R.id.tv_total_dollar);
        }
    }
}
