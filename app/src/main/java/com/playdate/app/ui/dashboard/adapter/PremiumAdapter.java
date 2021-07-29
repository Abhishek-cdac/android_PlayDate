package com.playdate.app.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.PackageDescription;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PremiumAdapter extends RecyclerView.Adapter<PremiumAdapter.ViewHolder> {
    ArrayList<PackageDescription> lst_packageDescription;
    int version;

    public PremiumAdapter(ArrayList<PackageDescription> lst_packageDescription, int version) {
        this.lst_packageDescription = lst_packageDescription;
        this.version = version;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = null;
        Context mContext = parent.getContext();
        if (version == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_premuim, parent, false);
        } else if (version == 1) {

            view = LayoutInflater.from(mContext).inflate(R.layout.row_premuim1, parent, false);
        } else if (version == 2) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_premuim2, parent, false);
        } else if (version == 3) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_premuim3, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PremiumAdapter.ViewHolder holder, int position) {
        holder.txt_prem.setText(lst_packageDescription.get(position).getPackageDescription());
    }

    @Override
    public int getItemCount() {
        if (lst_packageDescription == null)
            return 0;
        return lst_packageDescription.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_prem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_prem = itemView.findViewById(R.id.txt_prem);

        }
    }
}
