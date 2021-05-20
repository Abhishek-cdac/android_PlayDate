package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.SavedPostData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedPostAdapter extends RecyclerView.Adapter<SavedPostAdapter.MyViewHolder> {
    Context mContext;
    private List<SavedPostData> savedPostDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_media;
        CardView card_grid;
        public MyViewHolder(View view) {
            super(view);
            iv_media = view.findViewById(R.id.iv_media);
            card_grid = itemView.findViewById(R.id.card_grid);
        }
    }


    public SavedPostAdapter(List<SavedPostData> savedPostDataList) {
        this.savedPostDataList = savedPostDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_post_list_row, parent, false);
        mContext = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SavedPostData savedPostData = savedPostDataList.get(position);

        Picasso.get().load(savedPostData.getMediaFullPath())
                .placeholder(R.drawable.cupertino_activity_indicator)
                .into(holder.iv_media);

    }

    @Override
    public int getItemCount() {
        Log.e("savedPostDataList", ""+savedPostDataList.size());
        return savedPostDataList.size();
    }
}