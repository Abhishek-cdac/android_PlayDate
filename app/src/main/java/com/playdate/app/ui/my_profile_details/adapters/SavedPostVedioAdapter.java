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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.playdate.app.R;

import com.playdate.app.model.SavedPostData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class SavedPostVedioAdapter  extends AAH_VideosAdapter {


    private Context mContext;

    @Override
    public int getItemCount() {
        return lst.size();
    }

    ArrayList<SavedPostData> lst;

    public SavedPostVedioAdapter(FragmentActivity activity, ArrayList<SavedPostData> lst) {
        this.mContext = activity;
        this.lst = lst;
    }

    public void animateHeart(final ImageView view, View iv) {

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

        new Handler().postDelayed(() -> {
            view.clearAnimation();

            iv.setVisibility(View.GONE);
            notifyDataSetChanged();
        }, 900);


    }


    private Animation prepareAnimation(Animation animation) {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    @Override
    public int getItemViewType(int position) {

        if (lst.get(position).getMediaFullPath().toLowerCase().contains(".mp4")) {
            return 1;
        } else return 0;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        AAH_CustomViewHolder viewHolder = null;
        mContext = parent.getContext();
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_post_list_row, parent, false);
            viewHolder = new SavedPostVedioAdapter.ViewHolderUser(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_vedio_list_row, parent, false);
            viewHolder = new SavedPostVedioAdapter.ViewHolderUserVideo(view);
        }
//        else if (viewType == FragSocialFeed.RESTAURANT) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_1, parent, false);
//            viewHolder = new ViewHolderRestaurant(view);
//        }
//        else if (viewType == FragSocialFeed.ADDS) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_4, parent, false);
//            viewHolder = new ViewHolderAdds(view);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_feed_type_3, parent, false);
//            viewHolder = new ViewHolderAnonymQuestion(view);
//        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            SavedPostVedioAdapter.ViewHolderUser userViewHolder;
            userViewHolder = (SavedPostVedioAdapter.ViewHolderUser) holder;

            userViewHolder.iv_post_image.setOnClickListener(view -> {
//                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
//                    ref.loadProfile();
            });

            if (null != lst.get(position).getMediaFullPath()) {
                if (lst.get(position).getMediaFullPath().contains(".mp4")) {

                    // video

                } else {
                    Picasso.get().load(lst.get(position).getMediaFullPath())
                            .into(userViewHolder.iv_post_image, new ImageLoadedCallback(userViewHolder.animationView) {
                                @Override
                                public void onSuccess() {
                                    if (this.iv_loader != null) {
                                        this.iv_loader.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        } else {
            SavedPostVedioAdapter.ViewHolderUserVideo videoHolder;
            holder.setVideoUrl(lst.get(position).getMediaFullPath());

            holder.setLooping(true); //optional - true by default
            videoHolder = (SavedPostVedioAdapter.ViewHolderUserVideo) holder;
        }
    }


    public class ViewHolderUserVideo extends AAH_CustomViewHolder {

        LottieAnimationView animationView;
        ImageView iv_post_image;
        CardView card_image;
        ImageView iv_mute_unmute;
        ImageView img_playback;

        boolean isMuted=false;
        boolean isPlaying=true;
        public ViewHolderUserVideo(@NonNull View itemView) {
            super(itemView);
            iv_mute_unmute = itemView.findViewById(R.id.iv_mute_unmute);
            img_playback = itemView.findViewById(R.id.img_playback);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            card_image = itemView.findViewById(R.id.card_image);
            animationView = itemView.findViewById(R.id.animationView);


            iv_mute_unmute.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!isMuted) {
                        isMuted=true;
                        muteVideo();
                        iv_mute_unmute.setImageResource(R.drawable.ic_mute);
                    } else {
                        isMuted=false;
                        unmuteVideo();
                        iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
                    }
                }
            });
            img_playback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isPlaying){
                        isPlaying=false;
                        pauseVideo();
                        img_playback.setImageResource(R.drawable.play_circle);

                    }else{
                        isPlaying=true;
                        playVideo();
                        img_playback.setImageResource(R.drawable.ic_pause);
                    }

                }
            });
        }

        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
            if (isMuted) {
                muteVideo();
                iv_mute_unmute.setImageResource(R.drawable.ic_mute);
            } else {
                unmuteVideo();
                iv_mute_unmute.setImageResource(R.drawable.ic_unmute);
            }
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.play_circle);
        }
    }

    public class ViewHolderUser extends AAH_CustomViewHolder {
        LottieAnimationView animationView;
        ImageView iv_post_image;

        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            iv_post_image = itemView.findViewById(R.id.iv_media);


        }
    }


    void notifyAdapter() {
        notifyDataSetChanged();
    }



}

class ImageLoadedCallback implements Callback {
    LottieAnimationView iv_loader;

    public ImageLoadedCallback(LottieAnimationView view) {
        iv_loader = view;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Exception e) {

    }


}
