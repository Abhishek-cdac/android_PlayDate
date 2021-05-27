package com.playdate.app.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.anonymous_question.adapter.SmileyAdapter;

import java.util.ArrayList;

public class ChatSmileyAdapter extends RecyclerView.Adapter<ChatSmileyAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Integer> list;
    FragChatMain ref;

    public ChatSmileyAdapter(ArrayList<Integer> list, FragChatMain ref) {
        this.list = list;
        this.ref = ref;
    }

    @NonNull
    @Override
    public ChatSmileyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_smiley_sel, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatSmileyAdapter.ViewHolder holder, int position) {
        holder.ll_color.setBackground(mContext.getDrawable(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_color;
        CardView card_smiley;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_color = itemView.findViewById(R.id.ll_color);
            card_smiley = itemView.findViewById(R.id.card_smiley);
            card_smiley.getLayoutParams().height = 100;
            card_smiley.getLayoutParams().width = 100;
            card_smiley.setPadding(0,0,0,0);
            ll_color.getLayoutParams().height = 100;
            ll_color.getLayoutParams().width = 100;
            itemView.setOnClickListener(view -> ref.onSmileyChange(getAdapterPosition()));
        }
    }
}
