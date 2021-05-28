package com.playdate.app.ui.anonymous_question;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnoQuesCreateActivity extends AppCompatActivity implements OnColorCodeSelect, View.OnClickListener {
    RelativeLayout ll_ques;
    LinearLayout ll_smily;
    Intent mIntent;
    EditText add_comment;
    RecyclerView rec_view_colors;
    TextView txt_ques;
    TextView txt_post_comment;
    ImageView back_anonymous;
    ImageView more_option;
    ArrayList<Integer> lst = new ArrayList<>();
    ArrayList<Integer> lstSmiley = new ArrayList<Integer>();
    private ArrayList<MatchListUser> lstUserSuggestions = new ArrayList<>();


    Integer[] intEmoji= {
            0x1F600, 0x1F603, 0x1F604, 0x1F601, 0x1F606, 0x1F605, 0x1F923, 0x1F602, 0x1F61A, 0x1F619,
            0x1F642, 0x1F643, 0x1F609, 0x1F60A, 0x1F607, 0x1F60B, 0x1F60D, 0x1F929, 0x1F618, 0x1F617,
            0x1F61C, 0x1F92A, 0x1F61D, 0x1F911, 0x1F917, 0x1F92B, 0x1F914, 0x1F910, 0x1F928, 0x1F610,
            0x1F611, 0x1F636, 0x1F60F, 0x1F644, 0x1F62C, 0x1F925, 0x1F60C, 0x1F62A, 0x1F634, 0x1F637,
            0x1F927, 0x1F92C, 0x1F608, 0x1F47B, 0x1F635, 0x1F92F, 0x1F920, 0x1F649, 0x1F64A, 0x1F60E,
            0x1F913, 0x1F9D0, 0x1F615, 0x1F641, 0x1F62F, 0x1F632, 0x1F633, 0x1F97A, 0x1F626, 0x1F627,
            0x1F622, 0x1F62D, 0x1F631, 0x1F616, 0x1F61E, 0x1F613, 0x1F629, 0x1F62B, 0x1F971, 0x1F624
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ano_ques);
        rec_view_colors = findViewById(R.id.rec_view_colors);
        add_comment = findViewById(R.id.add_comment);
        txt_ques = findViewById(R.id.txt_ques);
        txt_post_comment = findViewById(R.id.txt_post_comment);
        back_anonymous = findViewById(R.id.back_anonymous);
        more_option = findViewById(R.id.more_option);
        ll_ques = findViewById(R.id.ll_ques);
        ll_smily = findViewById(R.id.ll_smily);
        mIntent = getIntent();
        txt_ques.setText(mIntent.getStringExtra("question"));
        CreateList();

        CreateSmilyList();
        OnColorChange(0);
        ColorAdapter adapter = new ColorAdapter(lst, this);
        rec_view_colors.setLayoutManager(new GridLayoutManager(this, 4));
        rec_view_colors.setAdapter(adapter);

        back_anonymous.setOnClickListener(this);
        more_option.setOnClickListener(this);
        txt_post_comment.setOnClickListener(this);
    }


    public void CreateList() {

        lst.add(R.color.color_violet);
        lst.add(R.color.color_violet1);
        lst.add(R.color.color_green_fresh);
        lst.add(R.color.black);
        lst.add(R.color.color_yellow);
        lst.add(R.color.color_blue_ligth);
        lst.add(R.color.color_brown);
        lst.add(R.color.color_blue);
        lst.add(R.color.color_grey);
        lst.add(R.color.color_green_dark);
        lst.add(R.color.color_red);


    }

    public void CreateSmilyList() {

        lstSmiley.add(R.drawable.face1);
        lstSmiley.add(R.drawable.face2);
        lstSmiley.add(R.drawable.face3);
        lstSmiley.add(R.drawable.face4);
        lstSmiley.add(R.drawable.face5);
        lstSmiley.add(R.drawable.face6);
        lstSmiley.add(R.drawable.face7);
        lstSmiley.add(R.drawable.face8);
        lstSmiley.add(R.drawable.face9);
        lstSmiley.add(R.drawable.face10);
        lstSmiley.add(R.drawable.face11);
        lstSmiley.add(R.drawable.face12);
        lstSmiley.add(R.drawable.face13);
        lstSmiley.add(R.drawable.face14);
        lstSmiley.add(R.drawable.face15);
        lstSmiley.add(R.drawable.face16);
        lstSmiley.add(R.drawable.face17);
        lstSmiley.add(R.drawable.face18);
        lstSmiley.add(R.drawable.face19);
        lstSmiley.add(R.drawable.face20);
        lstSmiley.add(R.drawable.face21);
        lstSmiley.add(R.drawable.face22);
        lstSmiley.add(R.drawable.face23);
        lstSmiley.add(R.drawable.face24);
        lstSmiley.add(R.drawable.face25);
        lstSmiley.add(R.drawable.face26);
        lstSmiley.add(R.drawable.face27);
        lstSmiley.add(R.drawable.face28);
        lstSmiley.add(R.drawable.face29);


    }

    @Override
    public void OnColorChange(int index) {
        // ll_ques.setBackground(getDrawable(Integer.parseInt(lst.get(index))));
        ll_ques.setBackground(getDrawable(lst.get(index)));
        SmileyAdapter adapter = new SmileyAdapter(lstSmiley, this);
        rec_view_colors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rec_view_colors.setAdapter(adapter);

    }

    @Override
    public void onSmileySelect(int index) {
        ll_smily.setBackground(getDrawable(lstSmiley.get(index)));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_anonymous) {
            finish();
        } else if (id == R.id.more_option) {
            showModel();
        } else if (id == R.id.txt_post_comment) {
            // postQues();
            if (add_comment.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your question here", Toast.LENGTH_SHORT).show();
            } else {
                callAPIFeedPost();
            }

        }
    }

    private void postQues() {
        setResult(100, null);
        finish();
    }

    private void showModel() {
        AnonymousBottomSheet bottomSheet = new AnonymousBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
    }

    private void callAPIFeedPost() {


        String tagFriends = "";
        if (null != lstUserSuggestions) {
            for (int i = 0; i < lstUserSuggestions.size(); i++) {
                if (lstUserSuggestions.get(i).isSelected()) {
                    if (tagFriends.isEmpty()) {
                        tagFriends = lstUserSuggestions.get(i).get_id();
                    } else {
                        tagFriends = tagFriends + "," + lstUserSuggestions.get(i).get_id();
                    }
                }
            }
        }

        SessionPref pref = SessionPref.getInstance(this);
        //  String Location = edt_location.getText().toString();
        String Location = "";
        if (Location.isEmpty()) {
            Location = "India";// Hardcode
        }
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("location", Location);
        hashMap.put("mediaId", "60b0993b291a0503100168db");// Hardcode
        hashMap.put("postType", "Question");
      //hashMap.put("tagFriend", "harshita");
        hashMap.put("tag", add_comment.getText().toString());
        hashMap.put("colorCode", "");
        hashMap.put("emojiCode", "128514");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        Call<LoginResponse> call = service.addPostFeed("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                       // DashboardActivity.bitmap = null;
                        finish();
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