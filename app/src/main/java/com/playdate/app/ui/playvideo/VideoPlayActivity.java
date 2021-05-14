//package com.playdate.app.ui.playvideo;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.playdate.app.R;
//import com.playdate.app.data.api.GetDataService;
//import com.playdate.app.data.api.RetrofitClientInstance;
//import com.playdate.app.model.LoginResponse;
//import com.playdate.app.ui.register.interest.InterestActivity;
//import com.playdate.app.ui.register.profile.UploadProfileActivity;
//import com.playdate.app.util.common.CommonClass;
//import com.playdate.app.util.session.SessionPref;
//import com.squareup.picasso.Picasso;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;
//
//public class VideoPlayActivity extends AppCompatActivity {
//    int length;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play_video);
//        VideoView videoView = findViewById(R.id.videoView);
//        ImageView iv_play_pause = findViewById(R.id.iv_play_pause);
//        LinearLayout ll_loader = findViewById(R.id.ll_loader);
//        SessionPref pref = SessionPref.getInstance(this);
//
//        String videopath = pref.getStringVal(SessionPref.LoginUserprofileVideo);
//
//
//        if (videopath.contains("http")) {
//
//        } else {
//            videopath = BASE_URL_IMAGE + videopath;
//        }
//
//        videoView.setVideoPath(videopath);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        ViewGroup.LayoutParams params = videoView.getLayoutParams();
//        params.width = metrics.widthPixels;
//        params.height = metrics.heightPixels;
////        params.leftMargin = 0;
//        videoView.setLayoutParams(params);
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                ll_loader.setVisibility(View.GONE);
//                videoView.start();
//            }
//
//
//        });
//
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                finish();
//            }
//        });
//
//        iv_play_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (videoView.isPlaying()) {
//                    videoView.pause();
//                    length = videoView.getCurrentPosition();
//                    iv_play_pause.setImageResource(R.drawable.play_circle);
//                } else {
//
//                    videoView.seekTo(length);
//                    videoView.start();
//
//                    iv_play_pause.setImageResource(R.drawable.ic_pause);
//
//                }
//
//            }
//        });
//
//
//    }
//}
