package com.playdate.app.ui.notification_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.ui.dashboard.DashboardActivity;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBottomSheet  extends BottomSheetDialogFragment {

    LinearLayout ll_notification_delete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification_bottom_sheet, container, false);

        Bundle bundle = this.getArguments();
        String notifiationId = bundle.getString("notifiationId");
        String userId = bundle.getString("userId");


        ll_notification_delete = view.findViewById(R.id.ll_notification_delete);
        ll_notification_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            callUpdateNotificationStatusAPI(notifiationId, userId, "delete");

            }
        });

        return view;
    }
    private void callUpdateNotificationStatusAPI(String notifiationId, String userId, String action) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("notificationId", notifiationId);
        hashMap.put("status", "1");
        hashMap.put("action", action);
        hashMap.put("userId", userId);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CommonModel> call = service.updateNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);

        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                     } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
