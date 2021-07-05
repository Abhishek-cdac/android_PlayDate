package com.playdate.app.ui.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class ImageViewActivity extends AppCompatActivity {

    Bitmap bitmapImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ImageView imageView = findViewById(R.id.imageView);
//        Log.e("IMageViewActivity", "IMageViewActivity");


        byte[] byteArray = getIntent().getByteArrayExtra("imageLocation");
        Log.d("BYTEARRAY", "onSnap"+byteArray);

        bitmapImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imageView.setImageBitmap(bitmapImage);
    }
}
