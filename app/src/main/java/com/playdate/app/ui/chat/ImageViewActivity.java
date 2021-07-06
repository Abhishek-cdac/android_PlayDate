package com.playdate.app.ui.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;
import com.playdate.app.util.session.SessionPref;

public class ImageViewActivity extends AppCompatActivity {

    private Bitmap bitmapImage;

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

        } catch (Exception ignored) {
        }

        imageView.setImageBitmap(bitmapImage);
    }
}
