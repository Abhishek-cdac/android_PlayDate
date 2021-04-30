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
        lst.add(new SocialFeed("Maria Gomes123", "1,555", false, 0));
        lst.add(new SocialFeed("John den", "1,847", false, 0));
        lst.add(new SocialFeed("Adreena Helen", "254", false, 0));
        lst.add(new SocialFeed("DeanSean", "2,486", false, 0));
        lst.add(new SocialFeed("Maria Gomes123", "1,555", false, 0));
        lst.add(new SocialFeed("John den", "1,847", false, 0));
        lst.add(new SocialFeed("Adreena Helen", "254", false, 0));
        lst.add(new SocialFeed("DeanSean", "2,486", false, 0));
        lst.add(new SocialFeed("Maria Gomes123", "1,555", false, 0));
        lst.add(new SocialFeed("John den", "1,847", false, 0));
        lst.add(new SocialFeed("Adreena Helen", "254", false, 0));
        lst.add(new SocialFeed("DeanSean", "2,486", false, 0));


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (lst.get(position).HeartSelected) {
            holder.iv_heart.setImageResource(R.drawable.red_heart);
        } else {
            holder.iv_heart.setImageResource(R.drawable.heart);
        }

        holder.card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lst.get(position).isHeartSelected()) {
                    if (lst.get(position).getTapCount() == 1) {
                        holder.card_image.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                        animateHeart(holder.iv_heart_red);
                        lst.get(position).setHeartSelected(true);
                    } else {
                        lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);

                    }

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

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                animation.setFillAfter(true);
                view.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });

    }

    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        ImageView iv_heart;
        CardView card_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            card_image = itemView.findViewById(R.id.card_image);
        }
    }
}
