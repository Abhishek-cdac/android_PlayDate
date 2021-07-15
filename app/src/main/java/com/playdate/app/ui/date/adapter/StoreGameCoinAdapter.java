package com.playdate.app.ui.date.adapter;

import android.util.Log;
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
import com.playdate.app.model.StoreData;
import com.playdate.app.model.StoreItems;
import com.playdate.app.ui.chat.request.Onclick;

import java.util.ArrayList;
import java.util.List;

public class StoreGameCoinAdapter extends RecyclerView.Adapter<StoreGameCoinAdapter.ViewHolder> {

    ArrayList<StoreData> lst_storeData;
    List<StoreItems> game_items;
    List<StoreItems> date_items;
    List<StoreItems> multiplier_items;
    List<StoreItems> dm_items;
    List<StoreItems> store_items;
    Onclick itemclick;

    public StoreGameCoinAdapter(ArrayList<StoreItems> game_items, Onclick itemclick) {
        this.game_items = game_items;
        this.itemclick = itemclick;

//        for (int i = 0; i < lst_storeData.size(); i++) {
//            store_items = lst_storeData.get(i).getStoreItems();
//
//            switch (lst_storeData.get(i).getStoreType()) {
//                case "Game Coins":
//                    game_items = lst_storeData.get(i).getStoreItems();
//
//                    break;
//                case "Date Coins":
//                    date_items = lst_storeData.get(i).getStoreItems();
//
//
//                    break;
//                case "Multiplier Boosters":
//                    multiplier_items = lst_storeData.get(i).getStoreItems();
//
//
//                    break;
//                case "DM Boosters":
//                    dm_items = lst_storeData.get(i).getStoreItems();
//
//                    break;
//            }
//
//        }
//        Log.d("Multiplier", "StoreGameCoinAdapter: " + multiplier_items.size());

    }

    @NonNull
    @Override
    public StoreGameCoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreGameCoinAdapter.ViewHolder holder, int position) {


        holder.tv_amount.setText(game_items.get(position).getValue());
        holder.tv_total_dollar.setText("$" + game_items.get(position).getAmount());
        if (holder.tv_amount.getText().equals("1")) {
            holder.iv_coins.setImageResource(R.drawable.game_coin);
        } else {
            holder.iv_coins.setImageResource(R.drawable.game_coin3);
        }

    }

    @Override
    public int getItemCount() {
//        int size_lst = 0;
//        for (int i = 0; i < lst_storeData.size(); i++) {
//            switch (lst_storeData.get(i).getStoreType()) {
//                case "Game Coins":
//                    size_lst = game_items.size();
//                    break;
//
//                case "Date Coins":
//                    size_lst = date_items.size();
//                    break;
//
//                case "Multiplier Boosters":
//                    size_lst = multiplier_items.size();
//                    break;
//
//                case "DM Boosters":
//                    size_lst = dm_items.size();
//                    break;
//            }
//        }

        Log.d("SizeStore---", "getItemCount: " + game_items.size());
        return game_items.size();
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
