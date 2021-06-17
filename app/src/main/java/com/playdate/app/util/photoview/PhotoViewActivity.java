package com.playdate.app.util.photoview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.squareup.picasso.Picasso;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.playdate.app.databinding.ActivityPhotoviewBinding binding = DataBindingUtil.setContentView(PhotoViewActivity.this, R.layout.activity_photoview);
        binding.setLifecycleOwner(this);
        binding.setPhotoViewViewModel(new PhotoViewViewModel());
//        binding.ivImage.setTransitionName(getIntent().getStringExtra("id"));
        Picasso.get().load(getIntent().getStringExtra("data")).into(binding.ivImage);


    }
}
