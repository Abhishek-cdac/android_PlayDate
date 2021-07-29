package com.playdate.app.ui.dashboard.data;

import android.content.Context;

import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.NotificationData;
import com.playdate.app.model.NotificationModel;
import com.playdate.app.ui.dashboard.OnAPIResponce;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CallAPI {
    public void callGetNotificationAPI(Context mContext) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");//Hardcode
        SessionPref pref = SessionPref.getInstance(mContext);
//        Timber.tag("GetUserSuggestionData").e("" + pref.getStringVal(SessionPref.LoginUsertoken));
        OnAPIResponce ref = (OnAPIResponce) mContext;
        Call<NotificationModel> call = service.getNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        Timber.e("" + hashMap);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    try {
                        if (response.body().getStatus() == 1) {
                            List<NotificationData> lst_notifications = response.body().getData();
                            ref.setNotiCount(lst_notifications.size());


                        } else {
                            ref.setNotiCount(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ref.setNotiCount(0);
                    }
                } else {
                    ref.setNotiCount(0);
                }

            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                t.printStackTrace();
                ref.setNotiCount(0);
            }
        });
    }
}
