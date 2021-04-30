package com.playdate.app.ui.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

import java.util.ArrayList;

public class SocialFeedAdapter extends RecyclerView.Adapter<SocialFeedAdapter.ViewHolder> {

    ArrayList<SocialFeed> lst = new ArrayList<>();

    public SocialFeedAdapter() {
        lst.add(new SocialFeed("Maria Gomes123", "", false));
        lst.add(new SocialFeed("John den", "", false));
        lst.add(new SocialFeed("Adreena Helen", "", false));
        lst.add(new SocialFeed("DeanSean", "", false));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
        return new ViewHolder(view);
    }

    int val = 0;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val++;
                if (val == 2) {
                    animateHeart(holder.iv_heart_red);
                }
            }
        });

    }

    public void animateHeart(final ImageView view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(700);
        animation.setFillAfter(true);

        view.startAnimation(animation);
        animation.setFillAfter(true);

    }

    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        CardView card_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            card_image = itemView.findViewById(R.id.card_image);
        }
    }
}
