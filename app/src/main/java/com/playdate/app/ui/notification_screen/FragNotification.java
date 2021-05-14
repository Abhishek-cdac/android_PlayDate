package com.playdate.app.ui.notification_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.model.NotificationData;
import com.playdate.app.model.NotificationModel;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragNotification extends Fragment {
    RecyclerView rv_notification;
    CommonClass clsCommon;
    List<NotificationData> lst_notifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);
        rv_notification = view.findViewById(R.id.rv_notification);
        clsCommon = CommonClass.getInstance();

        callGetNotificationAPI();

//        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        rv_notification.setLayoutManager(manager);
//
//        FragNotificationAdapter adapter = new FragNotificationAdapter();
//        rv_notification.setAdapter(adapter);


        return view;
    }
    private void callGetNotificationAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Log.e("GetUserSuggestionData", "" + pref.getStringVal(SessionPref.LoginUsertoken));

        Call<NotificationModel> call = service.getNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("GetUserSuggestionData", "" + hashMap);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        lst_notifications =  response.body().getData();
                        if (lst_notifications == null) {
                            lst_notifications = new ArrayList<>();
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        rv_notification.setLayoutManager(manager);
                        FragNotificationAdapter adapter = new FragNotificationAdapter();
                        rv_notification.setAdapter(adapter);

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
