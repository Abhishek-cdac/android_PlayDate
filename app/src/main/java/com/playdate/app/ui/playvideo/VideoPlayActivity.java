package com.playdate.app.ui.playvideo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class VideoPlayActivity extends AppCompatActivity {
    int length;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        VideoView videoView = findViewById(R.id.videoView);
        ImageView iv_play_pause = findViewById(R.id.iv_play_pause);
        LinearLayout ll_loader = findViewById(R.id.ll_loader);
        videoView.setVideoPath("https://assets.mixkit.co/videos/preview/mixkit-fashion-model-with-a-cold-and-pale-appearance-39877-large.mp4");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
        videoView.setLayoutParams(params);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ll_loader.setVisibility(View.GONE);
                videoView.start();
            }


        });


        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

        iv_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    length=videoView.getCurrentPosition();
                    iv_play_pause.setImageResource(R.drawable.play_circle);
                }else{

                    videoView.seekTo(length);
                    videoView.start();

                    iv_play_pause.setImageResource(R.drawable.ic_pause);

                }

            }
        });


    }
}
