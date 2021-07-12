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


//    public FullScreenView(String name, String image) {
//        this.image1 = image;
//        this.name1 = name;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.full_screen_card, container, false);
//        name = view.findViewById(R.id.item_name);
//        image = view.findViewById(R.id.item_image);
//        item_check = view.findViewById(R.id.item_check);
//        item_cross = view.findViewById(R.id.item_cross);
//        age = view.findViewById(R.id.item_age);
//        hobby = view.findViewById(R.id.item_hobby);
//
//        Picasso.get().load(image1).fit().centerCrop().into(image);
//        name.setText(name1);
//
//
//        return view;
//
//    }

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

//        item_check.setOnClickListener(v -> itemClick.onItemClicks(v, getAdapterPosition(), 13, userId));
//        item_cross.setOnClickListener(v -> itemClick.onItemClicks(v, getAdapterPosition(), 14, userId));


        String name1 = getIntent().getStringExtra("name");
        String image1 = getIntent().getStringExtra("image");
        String age1 = String.valueOf(getIntent().getIntExtra("age",0));
        Log.d("Image11----", "onCreate: " + image1);

        Picasso.get().load(image1).fit().centerCrop().into(image);
        name.setText(name1);
        age.setText(age1);

        iv_maximise.setOnClickListener(v -> finish());
    }
}
