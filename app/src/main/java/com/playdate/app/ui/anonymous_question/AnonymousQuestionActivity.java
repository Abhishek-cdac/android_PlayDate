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

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Comments;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetCommentData;
import com.playdate.app.model.GetCommentModel;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class AnonymousQuestionActivity extends AppCompatActivity implements onCommentDelete, View.OnClickListener {
    ArrayList<GetCommentData> lst_getComment;

    CommentAdapter adapter;
    TextView text_count, txt_post_comment;
    ImageView back_anonymous;
    ImageView more_option;
    Intent mIntent;
    RecyclerView recyclerView;
    EditText add_comment;
    EditText ext_question;
    boolean isForNew = false;
    String postId, anonymous;
    String UserID;
    private Onclick itemClick;
    //    String commentIdAq , userIDAq ;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_ques);
        mIntent = getIntent();
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String id) {

            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {
                if (i == 11) {
                    bundle.putString("postIdAQ", postId);
                    bundle.putString("userIdAQ", userId);
                    bundle.putString("commentIdAQ", commentId);
                }
            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };


        text_count = findViewById(R.id.comment_number);
        add_comment = findViewById(R.id.add_comment);
        ext_question = findViewById(R.id.ext_question);
        more_option = findViewById(R.id.more_option);
        text_count.setTypeface(Typeface.DEFAULT_BOLD);
        back_anonymous = findViewById(R.id.back_anonymous);
        txt_post_comment = findViewById(R.id.txt_post_comment);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            anonymous = extras.getString("Anonymous");
            postId = extras.getString("post_id");
            UserID = extras.getString("user_id");
            bundle.putString("post_id", postId);
            bundle.putString("user_id", UserID);
        }
        TextView text = findViewById(R.id.anun);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        recyclerView = findViewById(R.id.comments_list);
        txt_post_comment.setTextColor(getResources().getColor(R.color.color_grey));


        if (mIntent.getBooleanExtra("new", false)) {
            isForNew = true;
            text.setText(R.string.anonymous);
            text_count.setText("Add an anonymous question and receive responses");
            recyclerView.setVisibility(View.GONE);
            add_comment.setEnabled(true);
            add_comment.setHint("Add a question...!");
        } else {
            callGetCommentApi();
            text.setText(R.string.comments);
        }


        add_comment.addTextChangedListener(new TextWatcher() {

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
                txt_post_comment.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(104, null);
        super.onBackPressed();


    }

    private void callGetCommentApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("postId", postId);
        Log.e("postId", "" + this.postId);
        Call<GetCommentModel> call = service.getPostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<GetCommentModel>() {
            @Override
            public void onResponse(Call<GetCommentModel> call, Response<GetCommentModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getComment = (ArrayList<GetCommentData>) response.body().getData();
                        if (lst_getComment == null) {
                            lst_getComment = new ArrayList<>();
                        }


                        RecyclerView.LayoutManager manager = new LinearLayoutManager(AnonymousQuestionActivity.this, RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        CommentAdapter adapter = new CommentAdapter(getApplicationContext(), lst_getComment, itemClick);
                        recyclerView.setAdapter(adapter);


                        int number = adapter.getItemCount();
                        //  String number = lst_getComment.get(0).get;
                        //   Log.d("selected_click", String.valueOf(number));
                        Log.e("selected_click", "" + number);

                        if (number == 0) {
                            text_count.setText("No Comments");
                        } else {
                            text_count.setText(number + " Comments");
                        }
                    } else {

                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<GetCommentModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
            setResult(104, null);
            finish();
        } else if (id == R.id.txt_post_comment) {

            if (isForNew) {
                Intent mIntent = new Intent(this, AnoQuesCreateActivity.class);
                mIntent.putExtra("question", add_comment.getText().toString());
                startActivityForResult(mIntent, 101);
            } else {
                callAddCommentApi();
            }

        } else if (id == R.id.more_option) {
            showModel();
        }

    }

    private void callAddCommentApi() {
        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("postId", postId);
        hashMap.put("comment", add_comment.getText().toString());

        Log.e("userId", "" + pref.getStringVal(SessionPref.LoginUserID));
        Log.e("postId", "" + this.postId);
        Call<CommonModel> call = service.addPostComment("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        add_comment.setText("");
                        callGetCommentApi();
                    } else {

                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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
        try {
            AnonymousBottomSheet bottomSheet = new AnonymousBottomSheet();
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface onCommentDelete {
    void ChangeCount(int listCount);
}

