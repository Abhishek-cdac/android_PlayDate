package com.playdate.app.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class ChatSmileyAdapter extends RecyclerView.Adapter<ChatSmileyAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Integer> list;
    ChatMainActivity chatMainActivity;

    public ChatSmileyAdapter(ArrayList<Integer> list, ChatMainActivity chatMainActivity) {
        this.list = list;
        this.chatMainActivity = chatMainActivity;
    }

    @NonNull
    @Override
    public ChatSmileyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_smiley, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatSmileyAdapter.ViewHolder holder, int position) {
        holder.txt_smiley.setText(new String(Character.toChars(list.get(position))));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_smiley;
//        CardView card_smiley;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_smiley = itemView.findViewById(R.id.txt_smiley);
//            card_smiley = itemView.findViewById(R.id.card_smiley);
//            card_smiley.getLayoutParams().height = 100;
//            card_smiley.getLayoutParams().width = 100;
//            card_smiley.setPadding(0, 0, 0, 0);
//            txt_smiley.getLayoutParams().height = 100;
//            txt_smiley.getLayoutParams().width = 100;
            itemView.setOnClickListener(view -> chatMainActivity.onSmileyChange(getAdapterPosition()));
        }
    }
}
