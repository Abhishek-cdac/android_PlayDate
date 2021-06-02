package com.playdate.app.ui.anonymous_question.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.anonymous_question.AnoQuesCreateActivity;

import java.util.ArrayList;

public class SmileyAdapter extends RecyclerView.Adapter<SmileyAdapter.ViewHolder> {


    Context mContext;
    ArrayList<Integer> lst;
    AnoQuesCreateActivity ref;

    public SmileyAdapter(ArrayList<Integer> lst, AnoQuesCreateActivity anoQuesCreateActivity) {
        this.lst = lst;
        this.ref = anoQuesCreateActivity;
    }

    @NonNull
    @Override
    public SmileyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_smiley_sel, parent, false);
        mContext = parent.getContext();
        return new SmileyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmileyAdapter.ViewHolder holder, int position) {
        holder.txt_emo.setText(new String(Character.toChars(lst.get(position))));
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_color;
        TextView txt_emo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_color = itemView.findViewById(R.id.ll_color);
            txt_emo = itemView.findViewById(R.id.txt_emo);
            itemView.setOnClickListener(view -> ref.onSmileySelect(getAdapterPosition()));
        }
    }
}
