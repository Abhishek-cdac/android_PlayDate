package com.playdate.app.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.chat.ChatAdapter;

import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.ALL_PERMISSIONS_RESULT;
import static com.playdate.app.ui.register.profile.UploadProfileActivity.PICK_PHOTO_FOR_AVATAR;

public class SampleActivity extends AppCompatActivity {

    ImageView iv_send;
    ImageView iv_video;
    EditText et_msg;
    RecyclerView rv_chat;
    SampleAdapter adapter;
    ArrayList<SampleModel> arrList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        iv_send = findViewById(R.id.iv_send);
        et_msg = findViewById(R.id.et_chat);
        rv_chat = findViewById(R.id.rv_chat);
        iv_video = findViewById(R.id.iv_video);
        arrList = new ArrayList<>();


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_chat.setLayoutManager(manager);
        adapter = new SampleAdapter(arrList,this);
        rv_chat.setAdapter(adapter);
        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
        });


        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSIONS = {
                        WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions(SampleActivity.this,
                        PERMISSIONS,
                        ALL_PERMISSIONS_RESULT);
                pickImage();
            }
        });
    }


    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {

            if (data.getData() == null) {
                bitmap = (Bitmap) data.getExtras().get("data");
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(SampleActivity.this.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != bitmap) {


                Log.d("BITMAP VALUE", bitmap.toString());
                arrList.add(new SampleModel(bitmap));

//                adapter.addToListImage(bitmap);

//                binding.profileImage.setImageBitmap(bitmap);
//                showChange();
            }


        }
    }
}
