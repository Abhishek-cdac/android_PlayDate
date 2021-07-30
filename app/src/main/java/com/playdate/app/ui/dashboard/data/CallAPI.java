package com.playdate.app.ui.dashboard.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.NotificationData;
import com.playdate.app.model.NotificationModel;
import com.playdate.app.ui.dashboard.OnAPIResponse;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallAPI {
    public void callGetNotificationAPI(Context mContext) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");//Hardcode
        SessionPref pref = SessionPref.getInstance(mContext);
//        Timber.tag("GetUserSuggestionData").e("" + pref.getStringVal(SessionPref.LoginUsertoken));
        OnAPIResponse ref = (OnAPIResponse) mContext;
        Call<NotificationModel> call = service.getNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        Timber.e("" + hashMap);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    try {
                        if (response.body().getStatus() == 1) {
                            List<NotificationData> lst_notifications = response.body().getData();
                            ref.setNotificationCount(lst_notifications.size());


                        } else {
                            ref.setNotificationCount(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ref.setNotificationCount(0);
                    }
                } else {
                    ref.setNotificationCount(0);
                }

            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                ref.setNotificationCount(0);
            }
        });
    }
}
