package com.playdate.app.ui.social.upload_media.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.playdate.app.R;
import com.playdate.app.model.MatchListUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ViewHolder> {
    ArrayList<MatchListUser> lst;

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
        holder.chips.setChipText(lst.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chip chips;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chips = itemView.findViewById(R.id.chips);
        }
    }
}
