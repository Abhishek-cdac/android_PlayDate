package com.playdate.app.ui.notification_screen;

import android.os.Bundle;
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
import com.playdate.app.business.generate_coupons.FragCouponParentBusiness;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragNotification extends Fragment {

    private String extra;

    public FragNotification(String extra) {
        this.extra = extra;
    }

    public FragNotification() {
    }

    private RecyclerView rv_notification;
    private CommonClass clsCommon;
    private List<NotificationData> lst_notifications;
    private Onclick itemClick;
    private Bundle bundle;
    private TextView ll_no_notify;
    private int PageNumber = 1;
    private FragNotificationTypeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification, container, false);


        rv_notification = view.findViewById(R.id.rv_notification);
        ll_no_notify = view.findViewById(R.id.txt_no_notification);
        ImageView back_anonymous = view.findViewById(R.id.back_anonymous);
        clsCommon = CommonClass.getInstance();
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                if (value == 20) {
                    callFriedRequestStatusAPI(s, "Verified");
                } else if (value == 21) {
                    callFriedRequestStatusAPI(s, "Rejected");
                } else if (value == 24) {
                    callMatchRequestStatusUpdateAPI(s, "Verified");
                } else if (value == 25) {
                    callMatchRequestStatusUpdateAPI(s, "Rejected");
                } else if (value == 30) {
                    callRelationStatusUpdate(s, "Verified");
                } else if (value == 31) {
                    callRelationStatusUpdate(s, "Rejected");
                } else if (value == 32) {
                    callChatRequestStatusUpdate(s, "Active");
                } else if (value == 33) {
                    callChatRequestStatusUpdate(s, "Reject");
                } else if (value == 34) {
                    callMatchRequestStatusUpdateAPI(s, "Accepted");
                } else if (value == 35) {
                    callMatchRequestStatusUpdateAPI(s, "Rejected");
                }
            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {
                if (i == 22) {
                    callUpdateNotificationStatusAPI(notifiationId, userId, "read");
                } else if (i == 11) {
                    try {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        NotificationBottomSheet sheet = new NotificationBottomSheet(FragNotification.this);
                        bundle = new Bundle();
                        bundle.putString("notifiationId", notifiationId);
                        bundle.putString("userId", userId);
                        sheet.setArguments(bundle);
                        sheet.show(fragmentManager, "notification bottom sheet");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };

        back_anonymous.setOnClickListener(v -> {
            OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
            if (null != ref) {
                switch (extra) {
                    case "dashboard":
                        ref.Reset();
                        break;
                    case "Coupons":
                        ref.ReplaceFrag(new FragCouponParent());
                        break;
                    case "chat":
                        ref.ReplaceFrag(new RequestChatFragment());
                        break;
                    default:
                        ref.ReplaceFrag(new FragCouponParentBusiness());
                        break;
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
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        PageNumber = 1;
                        callGetNotificationAPI();
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        PageNumber = 1;
                        callGetNotificationAPI();
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        PageNumber = 1;
                        callGetNotificationAPI();
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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

                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
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


    public void loadMore() {
        callGetNotificationAPI();
    }

    public void callGetNotificationAPI() {

        if (PageNumber == -1) {
            return;
        }

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "12");
        hashMap.put("pageNo", "" + PageNumber);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<NotificationModel> call = service.getNotification("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                pd.cancel();

                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            List<NotificationData> lst = response.body().getData();
                            if (lst_notifications == null) {
                                lst_notifications = new ArrayList<>();
                            }

                            if (PageNumber == 1) {
                                lst_notifications.clear();
                                lst_notifications.addAll(lst);
                                NotificationData data = new NotificationData();
                                data.setPatternID("ViewMore");
                                lst_notifications.add(data);
                                if (lst_notifications.size() == 0) {
                                    ll_no_notify.setVisibility(View.VISIBLE);
                                    rv_notification.setVisibility(View.GONE);
                                } else {
                                    ll_no_notify.setVisibility(View.GONE);
                                    rv_notification.setVisibility(View.VISIBLE);

                                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                    rv_notification.setLayoutManager(manager);
                                    adapter = new FragNotificationTypeAdapter(getActivity(), (ArrayList<NotificationData>) lst_notifications, itemClick, FragNotification.this);
                                    rv_notification.setAdapter(adapter);
                                }
                            } else {
                                if (lst_notifications.get(lst_notifications.size() - 1).getPatternID().equals("ViewMore")) {
                                    lst_notifications.remove(lst_notifications.size() - 1);
                                }
                                if (lst.isEmpty()) {
                                    PageNumber = -1;
                                    adapter.showLoadmore = false;
                                    adapter.notifyDataSetChanged();
                                    return;
                                }
                                lst_notifications.addAll(lst);
                                NotificationData data = new NotificationData();
                                data.setPatternID("ViewMore");
                                lst_notifications.add(data);
                                adapter.notifyDataSetChanged();
                            }
                            PageNumber = PageNumber + 1;


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        PageNumber = -1;
                        adapter.showLoadmore = false;
                    }


                } else {
                    PageNumber = -1;
                    adapter.showLoadmore = false;
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                PageNumber = -1;
                adapter.showLoadmore = false;
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
