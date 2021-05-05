package com.playdate.app.ui.chat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rv_chat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        rv_chat = findViewById(R.id.rv_chat);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rv_chat.setLayoutManager(manager);


    }
}
