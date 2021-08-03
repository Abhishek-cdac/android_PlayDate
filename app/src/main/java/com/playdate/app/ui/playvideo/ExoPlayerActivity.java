package com.playdate.app.ui.playvideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class ExoPlayerActivity extends AppCompatActivity {

    private SimpleExoPlayer absPlayerInternal;
    private PlayerView pvMain;
    private ImageView iv_play_pause;
    private boolean playing = true;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player);
        pvMain = findViewById(R.id.ep_video_view);
        iv_play_pause = findViewById(R.id.iv_play_pause);
        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        absPlayerInternal = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef); //creating a player instance

        String userAgent = Util.getUserAgent(this, this.getString(R.string.app_name));
        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
        Uri uriOfContentUrl = Uri.parse(getIntent().getStringExtra("video"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
        absPlayerInternal.prepare(mediaSource);
        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline

        pvMain.setPlayer(absPlayerInternal); //
        pvMain.hideController();
        pvMain.setControllerAutoShow(false);
        pvMain.setControllerHideOnTouch(true);
        pvMain.setUseController(false);
        iv_play_pause.setOnClickListener(v -> {
            if (playing) {
                playing = false;
                pausePlayer(absPlayerInternal);
                iv_play_pause.setImageResource(R.drawable.play_circle);
            } else {
                playing = true;
                playPlayer(absPlayerInternal);
                iv_play_pause.setImageResource(R.drawable.ic_pause);
            }
        });
        absPlayerInternal.addListener(new Player.EventListener() {

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
//                        ll_loader.setVisibility(View.VISIBLE);
                        break;
                    case ExoPlayer.STATE_ENDED:
                        finish();
                        break;
                    default:
//                        ll_loader.setVisibility(View.GONE);
                        break;
                }
            }


        });

        Intent mImtent = getIntent();
        long seektime = mImtent.getLongExtra("time", 0);
        if (seektime != 0) {
            seekTo(absPlayerInternal, seektime);
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


    private void stopPlayer() {
        pvMain.setPlayer(null);
        absPlayerInternal.release();
        absPlayerInternal = null;
    }

    private void seekTo(SimpleExoPlayer player, long positionInMS) {
        if (player != null) {
            player.seekTo(positionInMS);
        }
    }


    @Override
    protected void onDestroy() {
        stopPlayer();
        super.onDestroy();

    }
}


