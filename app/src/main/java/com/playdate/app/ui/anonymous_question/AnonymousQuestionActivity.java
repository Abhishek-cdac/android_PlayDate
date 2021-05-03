package com.playdate.app.ui.anonymous_question;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class AnonymousQuestionActivity extends AppCompatActivity {

    CommentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_ques);
        TextView text_count = findViewById(R.id.comment_number);
        text_count.setTypeface(Typeface.DEFAULT_BOLD);

        TextView text = findViewById(R.id.anun);
        text.setTypeface(Typeface.DEFAULT_BOLD);

        RecyclerView recyclerView = findViewById(R.id.comments_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);

        int number = adapter.getItemCount();
        Log.d("selected_click", String.valueOf(number));

        if (number == 0) {
            text_count.setText("No Answer");
        } else {
            text_count.setText(number + " Answer");

        }

        findViewById(R.id.more_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragBottomSheet bottomSheet = new FragBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
            }
        });


    }
}
