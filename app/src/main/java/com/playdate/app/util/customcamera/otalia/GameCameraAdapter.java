package com.playdate.app.util.customcamera.otalia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otaliastudios.cameraview.filter.Filters;
import com.playdate.app.R;

import org.jetbrains.annotations.NotNull;

public class GameCameraAdapter extends RecyclerView.Adapter<GameCameraAdapter.ViewHolder> {
    Filters[] allFilters;

    public GameCameraAdapter(@NotNull Filters[] allFilters) {
        this.allFilters = allFilters;
    }

    @NonNull
    @Override
    public GameCameraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_filters, parent, false);
        return new GameCameraAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_filter_name.setText(allFilters[position].toString());
    }


    GameCamera activity;

    public void setOnClick(GameCamera activity) {
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return allFilters.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_filter_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_filter_name = itemView.findViewById(R.id.txt_filter_name);
            itemView.setOnClickListener(view -> activity.filterClickIndex(getAdapterPosition()));
        }
    }
}

