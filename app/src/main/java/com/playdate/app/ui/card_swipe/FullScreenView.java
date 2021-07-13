package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.playdate.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenView extends AppCompatActivity {

    ImageView image, iv_maximise, item_cross, item_check;
    TextView name, age, hobby;
    ImageView message;
    ImageView item_premium;
    ImageView iv_video_play;
    boolean playing = false;
    boolean firsttime = true;
    SimpleExoPlayer absPlayerInternal;
    PlayerView pvMain;
    String name1;
    String image1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_card);


        name = findViewById(R.id.item_name);
        image = findViewById(R.id.item_image);
        item_check = findViewById(R.id.item_check);
        item_cross = findViewById(R.id.item_cross);
        age = findViewById(R.id.item_age);
        hobby = findViewById(R.id.item_hobby);

//        pvMain = findViewById(R.id.ep_video_view);
//        message = findViewById(R.id.item_message);
//        iv_video_play = findViewById(R.id.iv_video_play);
//        item_premium = findViewById(R.id.item_premium);
        iv_maximise = findViewById(R.id.item_fullScreen);


        String name1 = getIntent().getStringExtra("name");
        String image1 = getIntent().getStringExtra("image");
        String age1 = String.valueOf(getIntent().getIntExtra("age", 0));
        String arr_interest = getIntent().getStringExtra("interestedArray");
        StringBuilder ints = new StringBuilder();

        Log.d("Image11----", "onCreate: " + image1);

        Picasso.get().load(image1).fit().centerCrop().into(image);
        name.setText(name1);
        age.setText(age1);
//        for (int i = 0; i < arr_interest.size(); i++) {
//            String str = arr_interest.get(i);
//            String output = str.substring(0, 1).toUpperCase() + str.substring(1);
//            if (ints.length() == 0) {
//
//                ints = new StringBuilder(output);
//            } else {
//                ints.append(" , ").append(output);
//            }
//        }

        hobby.setText(arr_interest);

        iv_maximise.setOnClickListener(v -> finish());
    }
}
