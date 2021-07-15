package com.playdate.app.ui.card_swipe;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullScreenView extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_card);

        TextView name = findViewById(R.id.item_name);
        ImageView image = findViewById(R.id.item_image);
        ImageView item_check = findViewById(R.id.item_check);
        ImageView item_cross = findViewById(R.id.item_cross);
        TextView age = findViewById(R.id.item_age);
        TextView hobby = findViewById(R.id.item_hobby);

        ImageView iv_maximise = findViewById(R.id.item_fullScreen);


        String name1 = getIntent().getStringExtra("name");
        String image1 = getIntent().getStringExtra("image");
        String age1 = String.valueOf(getIntent().getIntExtra("age", 0));
        String arr_interest = getIntent().getStringExtra("interestedArray");
        String userId = getIntent().getStringExtra("userId");


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
