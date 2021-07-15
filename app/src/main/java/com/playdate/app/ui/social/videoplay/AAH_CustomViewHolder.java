package com.playdate.app.ui.social.videoplay;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


public class AAH_CustomViewHolder extends RecyclerView.ViewHolder {
    private final AAH_VideoImage aah_vi;
    private String imageUrl;
    private String videoUrl;
    private boolean isLooping = true;
    private boolean isPaused = false;


    public AAH_CustomViewHolder(View x) {
        super(x);
        aah_vi = x.findViewWithTag("aah_vi");
    }

    public void playVideo() {
        this.aah_vi.getCustomVideoView().setPaused(false);
        this.aah_vi.getCustomVideoView().startVideo();
    }

    public void videoStarted() {
        this.aah_vi.getImageView().setVisibility(View.GONE);
    }

    public void showThumb() {
        this.aah_vi.getImageView().setVisibility(View.VISIBLE);
    }

    public void initVideoView(String url, Activity _act) {
        this.aah_vi.getCustomVideoView().setVisibility(View.VISIBLE);
        Uri uri = Uri.parse(url);
        this.aah_vi.getCustomVideoView().setSource(uri);
        this.aah_vi.getCustomVideoView().setLooping(isLooping);
        this.aah_vi.getCustomVideoView().set_act(_act);
        this.aah_vi.getCustomVideoView().setMyFuncIn(() -> {
            videoStarted();
            return null;
        });


        this.aah_vi.getCustomVideoView().setShowThumb(() -> {
            showThumb();
            return null;
        });
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public void pauseVideo() {
        this.aah_vi.getCustomVideoView().pauseVideo();
        this.aah_vi.getCustomVideoView().setPaused(true);
    }

    public void muteVideo() {
        this.aah_vi.getCustomVideoView().muteVideo();
    }

    public void unmuteVideo() {
        this.aah_vi.getCustomVideoView().unmuteVideo();
    }

    public AAH_VideoImage getAah_vi() {
        return aah_vi;
    }


    public String getVideoUrl() {
        return videoUrl + "";
    }

    public boolean isPlaying() {
        return this.aah_vi.getCustomVideoView().isPlaying();
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isLooping() {
        return isLooping;
    }
}