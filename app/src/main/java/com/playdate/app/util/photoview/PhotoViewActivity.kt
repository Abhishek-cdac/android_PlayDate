package com.playdate.app.util.photoview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.playdate.app.R
import com.playdate.app.databinding.ActivityPhotoviewBinding
import com.squareup.picasso.Picasso

class PhotoViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPhotoviewBinding =
            DataBindingUtil.setContentView(this@PhotoViewActivity, R.layout.activity_photoview)
        binding.lifecycleOwner = this
        binding.photoViewViewModel = PhotoViewViewModel()
        Picasso.get().load(intent.getStringExtra("data")).into(binding.ivImage)
    }
}