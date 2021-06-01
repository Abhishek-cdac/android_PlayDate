package com.playdate.app.ui.anonymous_question;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.model.MatchListUser;
import com.playdate.app.ui.anonymous_question.adapter.ColorAdapter;
import com.playdate.app.ui.anonymous_question.adapter.SmileyAdapter;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoQuesCreateActivity extends AppCompatActivity implements OnColorCodeSelect, View.OnClickListener {
    LinearLayout ll_ques;
    LinearLayout ll_smily;
    Intent mIntent;
    EditText add_comment;
    RecyclerView rec_view_colors;
    TextView txt_ques;
    TextView emojitxt;
    TextView txt_smiley;
    TextView txt_post_comment;
    ImageView back_anonymous;
    ImageView more_option;
    ArrayList<String> lst = new ArrayList<>();
    ArrayList<Integer> lstSmiley = new ArrayList<>();
    private ArrayList<MatchListUser> lstUserSuggestions = new ArrayList<>();


    Integer[] intEmoji = {
            0x1F600, 0x1F603, 0x1F604, 0x1F601, 0x1F606, 0x1F605, 0x1F923, 0x1F602, 0x1F61A, 0x1F619,
            0x1F642, 0x1F643, 0x1F609, 0x1F60A, 0x1F607, 0x1F60B, 0x1F60D, 0x1F929, 0x1F618, 0x1F617,
            0x1F61C, 0x1F92A, 0x1F61D, 0x1F911, 0x1F917, 0x1F92B, 0x1F914, 0x1F910, 0x1F928, 0x1F610,
    };
    String ques;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ano_ques);
        rec_view_colors = findViewById(R.id.rec_view_colors);
        add_comment = findViewById(R.id.add_comment);
        txt_ques = findViewById(R.id.txt_ques);
        add_comment.setEnabled(false);
        txt_post_comment = findViewById(R.id.txt_post_comments);
        back_anonymous = findViewById(R.id.back_anonymous);
        more_option = findViewById(R.id.more_option);
        ll_ques = findViewById(R.id.ll_ques);
        ll_smily = findViewById(R.id.ll_smily);
        txt_smiley = findViewById(R.id.txt_smiley);
        mIntent = getIntent();
        ques = mIntent.getStringExtra("question");

        txt_ques.setText(ques);

        int originalUnicode = 0x1F601;
        //textView.setText(getEmoticon(originalUnicode));
        Log.e("", "" + getEmoticon(originalUnicode));
        CreateList();

//        CreateSmilyList();
        getEmoticon();
        OnColorChange(0);
        ColorAdapter adapter = new ColorAdapter(lst, this);
        rec_view_colors.setLayoutManager(new GridLayoutManager(this, 4));
        rec_view_colors.setAdapter(adapter);

        back_anonymous.setOnClickListener(this);
        more_option.setOnClickListener(this);
        txt_post_comment.setOnClickListener(this);
    }

    private void getEmoticon() {

        for (int i = 0; i <= intEmoji.length; i++) {
//            String emoji = new String(Character.toChars(i));
            try {
                lstSmiley.add(intEmoji[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }


    }


    public String getEmoticon(int originalUnicode) {
        return new String(Character.toChars(originalUnicode));
    }


    public void CreateList() {

        lst.add("#1D1375");
        lst.add("#C50AF2");
        lst.add("#65FF00");
        lst.add("#000000");
        lst.add("#E6FF00");
        lst.add("#3AC7D1");
        lst.add("#D1763A");
        lst.add("#3A3AD1");
        lst.add("#989798");
        lst.add("#117106");
        lst.add("#D13A3A");


    }

    String HexColor = "";

    @Override
    public void OnColorChange(int index) {
        ll_ques.setBackgroundColor(Color.parseColor(lst.get(index)));
        HexColor = lst.get(index);
        SmileyAdapter adapter = new SmileyAdapter(lstSmiley, this);
        rec_view_colors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rec_view_colors.setAdapter(adapter);

    }

    int selectedSmileyIndex = -1;

    @Override
    public void onSmileySelect(int index) {
        selectedSmileyIndex = index;
        txt_smiley.setText(new String(Character.toChars(lstSmiley.get(index))));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_anonymous) {
            finish();
        } else if (id == R.id.more_option) {
            showModel();
        } else if (id == R.id.txt_post_comments) {
            // postQues();
            callAPIFeedPost();

        }
    }

    private void postQues() {
        setResult(100, null);
        finish();
    }

    private void showModel() {
        try {
            AnonymousBottomSheet bottomSheet = new AnonymousBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callAPIFeedPost() {
    SessionPref pref = SessionPref.getInstance(this);
        String Location = pref.getStringVal("LastCity");
        if (Location.isEmpty()) {
            Location = "India";
        }
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("location", Location);
        hashMap.put("postType", "Question");
        hashMap.put("tag", ques);
        hashMap.put("colorCode", HexColor);
        hashMap.put("emojiCode", "" + lstSmiley.get(selectedSmileyIndex));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        Call<LoginResponse> call = service.addPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                      // postQues();
                    } else {
//                        clsCommon.showDialogMsg(BioActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        clsCommon.showDialogMsg(BioActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
//                    } catch (Exception e) {
//                        Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
//                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


interface OnColorCodeSelect {
    void OnColorChange(int index);

    void onSmileySelect(int index);
}