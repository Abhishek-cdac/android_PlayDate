package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


        String name1 = getIntent().getStringExtra("name");
        String image1 = getIntent().getStringExtra("image");
        String age1 = String.valueOf(getIntent().getIntExtra("age", 0));
        String arr_interest = getIntent().getStringExtra("interestedArray");
        String userId = getIntent().getStringExtra("userId");

        Log.d("Image11----", "onCreate: " + image1);

        Picasso.get().load(image1).fit().centerCrop().into(image);
        name.setText(name1);
        age.setText(age1);
        hobby.setText(arr_interest);

        item_check.setOnClickListener(v -> callAddUserMatchRequestAPI(userId, "Like"));

        item_cross.setOnClickListener(v -> callAddUserMatchRequestAPI(userId, "Unlike"));

        iv_maximise.setOnClickListener(v -> finish());
    }

    private void callAddUserMatchRequestAPI(String userId, String action) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", userId);
        hashMap.put("action", action);

        SessionPref pref = SessionPref.getInstance(this);
        Call<CommonModel> call = service.addUserMatchRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        Log.d("Response", "onResponse1: " + response.body().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("Response", "onResponseJobj: " + jObjError.getString("message"));
//                        clsCommon.showDialogMsgfrag(FullScreenView.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(FullScreenView.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
                Toast.makeText(FullScreenView.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
