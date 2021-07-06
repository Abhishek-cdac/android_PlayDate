package com.playdate.app.ui.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;
import com.playdate.app.util.session.SessionPref;

import java.io.FileInputStream;

public class ImageViewActivity extends AppCompatActivity {

    Bitmap bitmapImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ImageView imageView = findViewById(R.id.imageView);

        SessionPref pref = SessionPref.getInstance(ImageViewActivity.this);
        String encodedString = pref.getStringVal("locationImg");
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        } catch (Exception e) {
            e.getMessage();
        }

//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        bitmapImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        imageView.setImageBitmap(bitmapImage);
    }
}
