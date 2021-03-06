package com.playdate.app.ui.social.upload_media.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.MatchListUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ViewHolder> {
    private final ArrayList<MatchListUser> lst;

    public ChipsAdapter(ArrayList<MatchListUser> lstUserSuggestions) {
        this.lst = lstUserSuggestions;
    }

    @NonNull
    @NotNull
    @Override
    public ChipsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChipsAdapter.ViewHolder holder, int position) {
        holder.chips.setText(lst.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView chips;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chips = itemView.findViewById(R.id.chip);
        }
    }
}
