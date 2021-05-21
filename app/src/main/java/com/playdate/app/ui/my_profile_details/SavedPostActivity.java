package com.playdate.app.ui.my_profile_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.SavedPostData;
import com.playdate.app.model.SavedPostModel;
import com.playdate.app.ui.my_profile_details.adapters.SavedPostAdapter;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SavedPostActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<SavedPostData> savedPostDataList = new ArrayList<>();
    private SavedPostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);
        recyclerView = findViewById(R.id.recycler_photos);

        callGetSavedPOstApi();

    }
    private void callGetSavedPOstApi() {

        SessionPref pref = SessionPref.getInstance(getApplicationContext());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        Log.e("Getsavepost_userId", "" + pref.getStringVal(SessionPref.LoginUserID));

//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(mContext);
//        pd.show();

        Call<SavedPostModel> call = service.getPostSaveGallery("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new retrofit2.Callback<SavedPostModel>() {
            @Override
            public void onResponse(Call<SavedPostModel> call, Response<SavedPostModel> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        mAdapter = new SavedPostAdapter(savedPostDataList);
                       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<SavedPostModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}