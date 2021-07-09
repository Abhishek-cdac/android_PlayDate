package com.playdate.app.ui.card_swipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.playdate.app.R;
import com.playdate.app.model.ChatStatusFrom;
import com.playdate.app.model.Interest;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.playvideo.ExoPlayerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class TinderSwipeAdapter extends RecyclerView.Adapter<TinderSwipeAdapter.ViewHolder> {

    private final ArrayList<Interest> lst_interest;
    public List<MatchListUser> tinder_list;
    private Context mContext;
    private String userId;
    private final Onclick itemClick;
    private final Picasso picasso;

    public TinderSwipeAdapter(List<MatchListUser> tinder_list, ArrayList<Interest> lst_interest, Onclick itemClick) {
        this.tinder_list = tinder_list;
        this.lst_interest = lst_interest;
        this.itemClick = itemClick;
        picasso = Picasso.get();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mContext = parent.getContext();
        View view = inflater.inflate(R.layout.tinder_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinderSwipeAdapter.ViewHolder holder, int position) {
        holder.setData(tinder_list.get(position));
    }

    @Override
    public int getItemCount() {
        if (tinder_list == null)
            return 0;

        return tinder_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, iv_maximise, item_cross, item_check;
        TextView name, age, hobby;
        ImageView message;
        ImageView item_premium;
        ImageView iv_video_play;
        boolean playing = false;
        boolean firsttime = true;
        SimpleExoPlayer absPlayerInternal;
        PlayerView pvMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            item_check = itemView.findViewById(R.id.item_check);
            item_cross = itemView.findViewById(R.id.item_cross);
            age = itemView.findViewById(R.id.item_age);
            hobby = itemView.findViewById(R.id.item_hobby);
            image = itemView.findViewById(R.id.item_image);
            pvMain = itemView.findViewById(R.id.ep_video_view);
            message = itemView.findViewById(R.id.item_message);
            iv_video_play = itemView.findViewById(R.id.iv_video_play);
            item_premium = itemView.findViewById(R.id.item_premium);
            iv_maximise = itemView.findViewById(R.id.item_fullScreen);
            item_check.setOnClickListener(v -> itemClick.onItemClicks(v, getAdapterPosition(), 13, userId));
            item_cross.setOnClickListener(v -> itemClick.onItemClicks(v, getAdapterPosition(), 14, userId));
          //  message.setOnClickListener(v -> itemClick.onItemClicks(v, getAdapterPosition(), 15, userId));



        }

        void setData(MatchListUser user) {
            try {
                userId = user.get_id();
                if (user.getProfileVideoPath() == null) {
                    iv_video_play.setVisibility(View.INVISIBLE);
                } else {
                    iv_video_play.setVisibility(View.VISIBLE);
                }
                iv_video_play.setImageResource(R.drawable.play_circle);
                iv_video_play.setOnClickListener(v -> {
                    image.setVisibility(View.GONE);
                    pvMain.setVisibility(View.VISIBLE);
                    iv_maximise.setVisibility(View.VISIBLE);


                    if (firsttime) {
                        firsttime = false;
                        PlayVideo(user.getProfileVideoPath());
                        playing = true;
                        iv_video_play.setImageResource(R.drawable.ic_pause);
                    } else {
                        if (playing) {
                            playing = false;
                            pausePlayer(absPlayerInternal);
                            iv_video_play.setImageResource(R.drawable.play_circle);
                        } else {
                            playing = true;
                            playPlayer(absPlayerInternal);
                            iv_video_play.setImageResource(R.drawable.ic_pause);
                        }
                    }


                });

                picasso.load(user.getProfilePicPath())
                        .fit()
                        .centerCrop()
                        .into(image);
                name.setText(user.getUsername());

                age.setText("" + user.getAge());

                StringBuilder ints = new StringBuilder();
                if (null != lst_interest) {
                    for (int i = 0; i < lst_interest.size(); i++) {
                        for (int j = 0; j < user.getInterested().size(); j++) {
                            if (lst_interest.get(i).get_id().equals(user.getInterested().get(j))) {
                                String str = lst_interest.get(i).getName();
                                String output = str.substring(0, 1).toUpperCase() + str.substring(1);
                                if (ints.length() == 0) {

                                    ints = new StringBuilder(output);
                                } else {
                                    ints.append(" , ").append(output);
                                }

                                break;
                            }
                        }
                    }
                }

                name.setOnClickListener(v -> {
                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) mContext;
                    ref.loadMatchProfile(user.get_id());
                });
                hobby.setText(ints.toString());

                if (user.getPaymentMode().equals("1")) {
                    item_premium.setVisibility(View.VISIBLE);
                } else {
                    item_premium.setVisibility(View.INVISIBLE);
                }


                iv_maximise.setOnClickListener(v -> {
                    Intent mIntent = new Intent(mContext, ExoPlayerActivity.class);
                    String videopath = user.getProfileVideoPath();

                    if (!videopath.contains("http")) {
                        videopath = BASE_URL_IMAGE + videopath;
                    }

                    mIntent.putExtra("video", videopath);
                    mIntent.putExtra("time", absPlayerInternal.getCurrentPosition());
                    mContext.startActivity(mIntent);
                    pvMain.setPlayer(null);
                    absPlayerInternal.release();
                    absPlayerInternal = null;

                    iv_video_play.setImageResource(R.drawable.play_circle);
                    image.setVisibility(View.VISIBLE);
                    pvMain.setVisibility(View.GONE);
                    iv_maximise.setVisibility(View.INVISIBLE);
                });


                if (user.getChatStatusFrom() != null) {
                    if (user.getChatStatusFrom().size() > 0) {
                        Log.e("getChatStatusFrom",""+ user.getChatStatusFrom().get(0).getActiveStatus());
                        if (user.getChatStatusFrom().get(0).getActiveStatus().toLowerCase().equals("pending")) {
                            message.setImageResource(R.drawable.chat_sel);
                        } else {
                            message.setImageResource(R.drawable.chat_black);
                        }
                    }
                }
                message.setOnClickListener(v -> {
                    if (user.getChatStatusFrom() != null) {
                        if (user.getChatStatusFrom().size() == 0) {
                            List<ChatStatusFrom> lst = new ArrayList<>();
                            ChatStatusFrom csf = new ChatStatusFrom();
                            csf.setActiveStatus("Pending");
                            lst.add(csf);
                            user.setChatStatusFrom(lst);
                            itemClick.onItemClicks(v, getAdapterPosition(), 15, userId);
                            notifyDataSetChanged();
                        }
                    }

                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        private void pausePlayer(SimpleExoPlayer player) {
            if (player != null) {
                player.setPlayWhenReady(false);
            }
        }

        private void playPlayer(SimpleExoPlayer player) {
            if (player != null) {
                player.setPlayWhenReady(true);
            }
        }

        void PlayVideo(String videopath) {
            if (null != videopath) {
                TrackSelector trackSelectorDef = new DefaultTrackSelector();
                absPlayerInternal = ExoPlayerFactory.newSimpleInstance(mContext, trackSelectorDef); //creating a player instance

                String userAgent = Util.getUserAgent(mContext, "PlayDate");
                DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(mContext, userAgent);
                Uri uriOfContentUrl = Uri.parse(videopath);
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
                absPlayerInternal.prepare(mediaSource);
                absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline

                pvMain.setPlayer(absPlayerInternal);
                pvMain.hideController();
                pvMain.setControllerAutoShow(false);
                pvMain.setControllerHideOnTouch(true);
                pvMain.setUseController(false);

                absPlayerInternal.addListener(new Player.EventListener() {

                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                        switch (playbackState) {
                            case ExoPlayer.STATE_BUFFERING:
                                break;
                            case ExoPlayer.STATE_ENDED:
                                iv_video_play.setImageResource(R.drawable.play_circle);
                                image.setVisibility(View.VISIBLE);
                                pvMain.setVisibility(View.GONE);
                                iv_maximise.setVisibility(View.INVISIBLE);
                                break;

                        }
                    }


                });
            }
        }
    }


    public List<MatchListUser> getList() {
        return tinder_list;
    }

    public void setList(List<MatchListUser> list) {
        this.tinder_list = list;
    }
}

