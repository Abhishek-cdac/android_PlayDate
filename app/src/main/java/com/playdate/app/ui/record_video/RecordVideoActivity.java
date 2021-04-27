package com.playdate.app.ui.record_video;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityRecordVideoBinding;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.ui.restaurant.RestaurantActivity;

public class RecordVideoActivity extends AppCompatActivity {
    RecordVideoViewModel viewModel;
    ActivityRecordVideoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RecordVideoViewModel();
        binding = DataBindingUtil.setContentView(RecordVideoActivity.this, R.layout.activity_record_video);
        binding.setLifecycleOwner(this);
        binding.setRecordViewModel(viewModel);


        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        startActivityForResult(intent, 1);


        viewModel.OnNextClick().observe(RecordVideoActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(RecordVideoActivity.this, DashboardActivity.class));

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            binding.videoView1.setVideoURI(data.getData());
            binding.videoView1.start();
//            builder.setView(videoView).show();
        } else {
            Toast.makeText(this, "Can't record video", Toast.LENGTH_SHORT).show();
        }
    }
}