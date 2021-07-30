package com.playdate.app.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    private final ArrayList<String> lst;

    public PromotionAdapter(ArrayList<String> lst) {
        this.lst = lst;
    }

    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotion, parent, false);
//        Context mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.ViewHolder holder, int position) {
        holder.tv_promotion1.setText(lst.get(position));
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_promotion1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_promotion1 = itemView.findViewById(R.id.tv_promotion1);
        }
    }
}
