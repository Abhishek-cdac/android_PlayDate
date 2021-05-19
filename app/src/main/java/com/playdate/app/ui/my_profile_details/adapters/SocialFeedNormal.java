package com.playdate.app.ui.my_profile_details.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.social.FragSocialFeed;
import com.playdate.app.ui.social.SocialFeed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SocialFeedNormal extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;

    @Override
    public int getItemCount() {
        return lst.size();
    }

    ArrayList<SocialFeed> lst;

    public SocialFeedNormal(ArrayList<SocialFeed> lst) {
        this.lst = lst;
    }

    public void animateHeart(final ImageView view, SocialFeedNormal.ViewHolderUser holder) {

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(800);
        animation.setFillAfter(true);

        view.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.clearAnimation();
//                Toast.makeText(mContext, "Amimation end", Toast.LENGTH_SHORT).show();
//                animation.setFillAfter(true);
                holder.iv_heart_red.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        }, 900);


    }

    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public int getItemViewType(int position) {

        int finalpos = lst.get(position).getType();
        switch (finalpos) {
            case 0:
                return 0;

//            case FragSocialFeed.RESTAURANT:
//                return FragSocialFeed.RESTAURANT;
//
//            case FragSocialFeed.ANONYMUSQUESTION:
//                return FragSocialFeed.ANONYMUSQUESTION;
//
//            case FragSocialFeed.ADDS:
//                return FragSocialFeed.ADDS;


        }
        return 0;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        mContext = parent.getContext();
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_normal, parent, false);
            viewHolder = new SocialFeedNormal.ViewHolderUser(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            SocialFeedNormal.ViewHolderUser userViewHolder = (SocialFeedNormal.ViewHolderUser) holder;
            userViewHolder.name_friend.setText(lst.get(position).getUserName());

            userViewHolder.name_friend.setOnClickListener(view -> {
                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
                ref.loadProfile();
            });

            userViewHolder.iv_post_image.setOnClickListener(view -> {
//                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
//                    ref.loadProfile();
            });

            Picasso.get().load(lst.get(position).getImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(userViewHolder.iv_post_image);

            Picasso.get().load(lst.get(position).getSmallUserImage())
                    .placeholder(R.drawable.cupertino_activity_indicator)
                    .into(userViewHolder.iv_profile);

//            if (lst.get(position).HeartSelected) {
//                userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
//            } else {
//                userViewHolder.iv_heart.setImageResource(R.drawable.heart);
//            }

//            userViewHolder.iv_heart.setOnClickListener(view -> {
//                if (lst.get(position).HeartSelected) {
//                    userViewHolder.iv_heart.setImageResource(R.drawable.heart);
//                    lst.get(position).setTapCount(0);
//                    lst.get(position).setHeartSelected(false);
//                    notifyDataSetChanged();
//                } else {
//                    userViewHolder.iv_heart.setImageResource(R.drawable.red_heart);
//                }
//            });
            userViewHolder.card_image.setOnClickListener(view -> {

                if (!lst.get(position).isHeartSelected()) {
                    if (lst.get(position).getTapCount() == 1) {
//                        MediaPlayer mp;
//                        mp = MediaPlayer.create(mContext, R.raw.sound);
//                        int m4 = (int) mContext.getResources().getDimension(R.dimen._120sdp);
////                        holder.iv_heart_red.getLayoutParams().height = m4;
////                        holder.iv_heart_red.getLayoutParams().width = m4;
////                        notifyDataSetChanged();
//
//                        if (mp.isPlaying()) {
//                            mp.stop();
//                            mp.release();
//                            mp = MediaPlayer.create(mContext, R.raw.sound);
//                        } mp.start();

                        lst.get(position).setHeartSelected(true);
                        userViewHolder.iv_heart_red.setVisibility(View.VISIBLE);
                        animateHeart(userViewHolder.iv_heart_red, userViewHolder);
                    } else {
                        lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
                        userViewHolder.iv_heart_red.setVisibility(View.GONE);
                    }

                } else {
                    lst.get(position).setTapCount(lst.get(position).getTapCount() + 1);
//                        if (lst.get(position).getTapCount() == 2) {
//                            lst.get(position).setTapCount(0);
//                            lst.get(position).setHeartSelected(false);
//                            userViewHolder.iv_heart.setImageResource(R.drawable.heart);
//                        } else {
//
//                        }
                }
            });

        }
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {
        ImageView iv_heart_red;
        ImageView iv_profile;
        ImageView iv_heart;
        ImageView iv_post_image;
        CardView card_image;
        TextView name_friend;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            iv_heart_red = itemView.findViewById(R.id.iv_heart_red);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            name_friend = itemView.findViewById(R.id.name_friend);
        }
    }


}
