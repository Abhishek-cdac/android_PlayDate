package com.playdate.app.ui.anonymous_question;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class  AnonymousQuestionActivity extends AppCompatActivity implements onCommentDelete {

    CommentAdapter adapter;
    TextView text_count, post_comment;
    EditText edittxt_add_comment;
    Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_ques);
        mIntent = getIntent();
        text_count = findViewById(R.id.comment_number);
        text_count.setTypeface(Typeface.DEFAULT_BOLD);

        post_comment = findViewById(R.id.post_comment);
        edittxt_add_comment = findViewById(R.id.add_comment);

        TextView text = findViewById(R.id.anun);
        text.setTypeface(Typeface.DEFAULT_BOLD);

        RecyclerView recyclerView = findViewById(R.id.comments_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CommentAdapter(this);
        recyclerView.setAdapter(adapter);

        int number = adapter.getItemCount();
        Log.d("selected_click", String.valueOf(number));

        if (number == 0) {
            text_count.setText("No Answer");
        } else {
            text_count.setText(number + " Answer");

        }

        if (mIntent.getBooleanExtra("Anonymous", false)) {
            text.setText(R.string.anonymous);
        } else {
            text.setText(R.string.comments);
        }

        findViewById(R.id.more_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnonymousBottomSheet bottomSheet = new AnonymousBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

//        findViewById(R.id.back_anonymous).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AnonymousQuestionActivity.this, FragSocialFeed.class));
//            }
//        });

        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Edittext.text", edittxt_add_comment.getText().toString());
                adapter.addComment(edittxt_add_comment);
            }
        });
    }

    public void ChangeCount(int number) {
        if (number == 0) {
            text_count.setText("No Answer");
        } else {
            text_count.setText(number + " Answer");

        }
    }
}

interface onCommentDelete {
    void ChangeCount(int listCount);
}
