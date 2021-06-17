package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.model.SavedPostData;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.playdate.app.util.photoview.PhotoViewActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SavedPostAdapter extends RecyclerView.Adapter<SavedPostAdapter.ViewHolder> {


    private final FragmentActivity activity;

    @Override
    public int getItemCount() {
        return lst.size();
    }

    private final Picasso picasso;
    private final ArrayList<SavedPostData> lst;

    public SavedPostAdapter(FragmentActivity activity, ArrayList<SavedPostData> lst) {
        this.activity = activity;
        this.lst = lst;
        picasso = Picasso.get();
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_post_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SavedPostAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_post_image;
        private final ImageView play_icon;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_post_image = itemView.findViewById(R.id.iv_media);
            play_icon = itemView.findViewById(R.id.play_icon);
            itemView.setOnClickListener(v -> {
                try {
                    int pos = getAdapterPosition();
                    if (lst.get(pos).getMediaType().toLowerCase().equals("image")) {
                        Intent mIntent = new Intent(activity, PhotoViewActivity.class);
                        mIntent.putExtra("data", lst.get(pos).getMediaFullPath());
                        //                    mIntent.putExtra("id", lst.get(pos).getId());
                        mIntent.putExtra("isVideo", false);
                        //                    ActivityOptionsCompat options = ActivityOptionsCompat.
                        //                            makeSceneTransitionAnimation(activity, iv_post_image, "profile");
                        activity.startActivity(mIntent);
                    } else {
                        Intent mIntent = new Intent(activity, ExoPlayerActivity.class);
                        mIntent.putExtra("video", lst.get(pos).getMediaFullPath());
                        activity.startActivity(mIntent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }

        public void setData(int position) {
            try {
                if (lst.get(position).getMediaType().toLowerCase().equals("image")) {
                    picasso.load(lst.get(position).getMediaFullPath()).into(iv_post_image);
                    play_icon.setVisibility(View.GONE);
                } else {//Video
                    picasso.load(lst.get(position).getMediaThumbName()).into(iv_post_image);
                    play_icon.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

