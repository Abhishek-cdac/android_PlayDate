package com.playdate.app.ui.notification_screen;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.NotificationData;
import com.playdate.app.model.NotificationModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.chat.request.RequestChatFragment;
import com.playdate.app.ui.coupons.FragCouponParent;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
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
import timber.log.Timber;

public class FragNotification extends Fragment {
    private final String extra;

    public FragNotification(String extra) {
        this.extra = extra;
    }

    private RecyclerView rv_notification;
    private CommonClass clsCommon;
    private List<NotificationData> lst_notifications;
    private Onclick itemClick;
    private Bundle bundle;
    private TextView ll_no_notify;
    private ImageView back_anonymous;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);
        rv_notification = view.findViewById(R.id.rv_notification);
        ll_no_notify = view.findViewById(R.id.txt_no_notification);
        ImageView back_anonymous = view.findViewById(R.id.back_anonymous);
        back_anonymous = view.findViewById(R.id.back_anonymous);
        clsCommon = CommonClass.getInstance();
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                if (value == 20) {
                    callFriedRequestStatusAPI(s, "Verified");
                    Timber.e("accept" + s);
                } else if (value == 21) {
                    callFriedRequestStatusAPI(s, "Rejected");
                    Log.e("request_sent_requestID", "reject" + s);
                } else if (value == 24) {
                    callMatchRequestStatusUpdateAPI(s, "Verified");
                } else if (value == 25) {
                    callMatchRequestStatusUpdateAPI(s, "Rejected");
                } else if (value == 30) {
                    callRelationStatusUpdate(s, "Verified");
                } else if (value == 31) {
                    callRelationStatusUpdate(s, "Rejected");
                }
                else  if (value == 32) {
                    Log.e("Accept requestId",""+s);
                    callChatRequestStatusUpdate(s, "Active");
                }
                else  if(value == 33){
                    Log.e("Reject requestId",""+s);

                    callChatRequestStatusUpdate(s, "Reject");
                }

                else if(value == 34){
                    callMatchRequestStatusUpdateAPI(s, "Accepted");
                }  else if(value == 35){
                    callMatchRequestStatusUpdateAPI(s, "Rejected");
                }
            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {
                if (i == 22) {
                    callUpdateNotificationStatusAPI(notifiationId, userId, "read");
                } else if (i == 11) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    NotificationBottomSheet sheet = new NotificationBottomSheet(FragNotification.this);
                    bundle = new Bundle();
                    bundle.putString("notifiationId", notifiationId);
                    bundle.putString("userId", userId);
                    sheet.setArguments(bundle);
                    sheet.show(fragmentManager, "notification bottom sheet");
                }
            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };
        back_anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extra.equals("dashboard")) {
                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
                    ref.Reset();
                } else if (extra.equals("Coupons")) {
                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
                    ref.ReplaceFrag(new FragCouponParent());
                } else {
                    OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
                    ref.ReplaceFrag(new RequestChatFragment());
//                    getActivity().finish();
                }
            }
        });
        callGetNotificationAPI();


        return view;
    }

    private void callRelationStatusUpdate(String relatioRequestId, String status) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String userId = pref.getStringVal(SessionPref.LoginUserID);
        pref.saveStringKeyVal(SessionPref.RelationRequestId, relatioRequestId);
        hashMap.put("userId", userId);
        hashMap.put("requestId", relatioRequestId);
        hashMap.put("status", status);  //Verified,Rejected
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();

        Call<CommonModel> call = service.relationRequestStatusUpdate("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        callGetNotificationAPI();
                        Log.d("relationRequestId....", pref.getStringVal("relationRequestId"));
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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


    private void callChatRequestStatusUpdate(String chatRequestId, String status) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String userId = pref.getStringVal(SessionPref.LoginUserID);
      //  pref.saveStringKeyVal(SessionPref.RelationRequestId, relatioRequestId);
        hashMap.put("userId", userId);
        hashMap.put("requestID", chatRequestId);
        hashMap.put("status", status);  //Active,Rejected
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();

        Call<CommonModel> call = service.chatRequestStatusUpdate("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        callGetNotificationAPI();
                        Log.e("ChatRequestId....", ""+ chatRequestId);
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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
    private void callMatchRequestStatusUpdateAPI(String requestId, String status) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("requestID", requestId);
        hashMap.put("status", status);  //Verified,Rejected
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CommonModel> call = service.matchRequestStatusUpdate("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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

    private void callFriedRequestStatusAPI(String s, String status) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("requestID", s);
        hashMap.put("status", status);

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());


        Call<CommonModel> call = service.friendRequestStatus("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("friendRequestStatus", "" + hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        callGetNotificationAPI();
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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


    private void callUpdateNotificationStatusAPI(String notifiationId, String userId, String action) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("notificationId", notifiationId);
        hashMap.put("status", "1");
        hashMap.put("action", action); //Hardcode
        hashMap.put("userId", userId);//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());


        Call<CommonModel> call = service.updateNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("UpdateNotificationData", "" + hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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

    public void callGetNotificationAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
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
                        lst_notifications = response.body().getData();
                        if (lst_notifications == null) {
                            lst_notifications = new ArrayList<>();
                        }

                        if (lst_notifications.size() == 0) {
                            ll_no_notify.setVisibility(View.VISIBLE);
                            rv_notification.setVisibility(View.GONE);
                        } else {
                            ll_no_notify.setVisibility(View.GONE);
                            rv_notification.setVisibility(View.VISIBLE);

                            Log.e("lst_notifications", "" + lst_notifications.size());
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                            rv_notification.setLayoutManager(manager);
                            FragNotificationTypeAdapter adapter = new FragNotificationTypeAdapter(getActivity(), (ArrayList<NotificationData>) lst_notifications, itemClick);
                            rv_notification.setAdapter(adapter);
                        }


//                        Log.e("lst_notifications", "" + lst_notifications.size());
//                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//                        rv_notification.setLayoutManager(manager);
//                        FragNewNotificationAdapter adapter = new FragNewNotificationAdapter(getActivity(), (ArrayList<NotificationData>) lst_notifications, itemClick);
//                        rv_notification.setAdapter(adapter);

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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
