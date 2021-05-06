package com.playdate.app.ui.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rv_chat;
    ChatAdapter adapter;
    ImageView iv_send;
    EditText et_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        et_msg = findViewById(R.id.et_chat);
        iv_send = findViewById(R.id.iv_send);
        rv_chat = findViewById(R.id.rv_chat);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_chat.setLayoutManager(manager);
        adapter = new ChatAdapter();
        rv_chat.setAdapter(adapter);
        rv_chat.addOnScrollListener(new RecyclerView.OnScrollListener() {
        });

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addToList(et_msg);
            }
        });


    }
}
