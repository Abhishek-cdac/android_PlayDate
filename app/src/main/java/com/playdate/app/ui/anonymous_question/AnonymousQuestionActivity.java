package com.playdate.app.ui.anonymous_question;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;

public class AnonymousQuestionActivity extends AppCompatActivity implements onCommentDelete, View.OnClickListener {

    CommentAdapter adapter;
    TextView text_count, txt_post_comment;
    ImageView back_anonymous;
    ImageView more_option;
    Intent mIntent;
    RecyclerView recyclerView;
    EditText add_comment;
    EditText ext_question;
    boolean isForNew = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_ques);
        mIntent = getIntent();
        text_count = findViewById(R.id.comment_number);
        add_comment = findViewById(R.id.add_comment);
        ext_question = findViewById(R.id.ext_question);
        more_option = findViewById(R.id.more_option);
        text_count.setTypeface(Typeface.DEFAULT_BOLD);
        back_anonymous = findViewById(R.id.back_anonymous);
        txt_post_comment = findViewById(R.id.txt_post_comment);

        TextView text = findViewById(R.id.anun);
        text.setTypeface(Typeface.DEFAULT_BOLD);

        recyclerView = findViewById(R.id.comments_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new CommentAdapter(this);
        recyclerView.setAdapter(adapter);
        txt_post_comment.setTextColor(getResources().getColor(R.color.color_grey));
        int number = adapter.getItemCount();
        Log.d("selected_click", String.valueOf(number));

        if (number == 0) {
            text_count.setText("No Answer");
        } else {
            text_count.setText(number + " Answer");

        }

        if (mIntent.getBooleanExtra("new", false)) {
            isForNew = true;
            text.setText(R.string.anonymous);
            text_count.setText("Add an anonymous question and receive responces");
            recyclerView.setVisibility(View.GONE);
            add_comment.setEnabled(false);
            add_comment.setHint("Add a question...!");
        } else {
            text.setText(R.string.comments);
        }

        more_option.setOnClickListener(this);
        back_anonymous.setOnClickListener(this);
        txt_post_comment.setOnClickListener(this);

        ext_question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    txt_post_comment.setEnabled(true);
                    txt_post_comment.setTextColor(getResources().getColor(R.color.white));
                } else {
                    txt_post_comment.setEnabled(false);
                    txt_post_comment.setTextColor(getResources().getColor(R.color.color_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_anonymous) {
            finish();
        } else if (id == R.id.txt_post_comment) {

            if (isForNew) {
                Intent mIntent = new Intent(this, AnoQuesCreateActivity.class);
                mIntent.putExtra("question", ext_question.getText().toString());
                startActivityForResult(mIntent, 101);
            } else {

            }

        } else if (id == R.id.more_option) {
            showModel();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 100) {
                finish();
            }
        }
    }

    private void showModel() {
        AnonymousBottomSheet bottomSheet = new AnonymousBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
    }
}

interface onCommentDelete {
    void ChangeCount(int listCount);
}
