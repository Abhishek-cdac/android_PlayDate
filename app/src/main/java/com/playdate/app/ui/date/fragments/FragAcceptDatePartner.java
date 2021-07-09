package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.CreateDateGetMyPartnerReqData;
import com.playdate.app.model.CreateDateGetMyPartnerReqModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.adapter.RequestDateAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragAcceptDatePartner extends Fragment {
    private CommonClass clsCommon;
    private RecyclerView recycler_view;
//    private Button tv_reject_date, tv_accept_date;
//    private TextView tv_username;
    private Onclick itemClick;
    private TextView tv_placeholder;


    private ArrayList<CreateDateGetMyPartnerReqData> lst_getReqPartnerDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_accept_date_partner, container, false);
        clsCommon = CommonClass.getInstance();
        recycler_view = view.findViewById(R.id.recycler_view);
        tv_placeholder = view.findViewById(R.id.tv_placeholder);
        ImageView iv_play_date_logo = view.findViewById(R.id.iv_play_date_logo);

        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                if (value == 12) {
                    callCreateDateStatusUpdateRequestPartnerApi("Accepted", s);
                } else if (value == 13) {
                    callCreateDateStatusUpdateRequestPartnerApi("Rejected", s);
                }
            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };

        callCreateDateGetMyPartnerReqApi();

        iv_play_date_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void callCreateDateStatusUpdateRequestPartnerApi(String status, String requestId) {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("requestId", requestId);
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("status", status);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<CommonModel> call = service.createDateStatusUpdateRequestPartner("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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

    private void callCreateDateGetMyPartnerReqApi() {

        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<CreateDateGetMyPartnerReqModel> call = service.createDateGetMyPartnerRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CreateDateGetMyPartnerReqModel>() {
            @Override
            public void onResponse(Call<CreateDateGetMyPartnerReqModel> call, Response<CreateDateGetMyPartnerReqModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getReqPartnerDetail = (ArrayList<CreateDateGetMyPartnerReqData>) response.body().getData();
                        if (lst_getReqPartnerDetail == null) {
                            lst_getReqPartnerDetail = new ArrayList<>();
                        }

                        if (lst_getReqPartnerDetail.size() == 0) {
                            tv_placeholder.setVisibility(View.VISIBLE);
                            recycler_view.setVisibility(View.GONE);
                        } else {
                            tv_placeholder.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);

                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            recycler_view.setLayoutManager(manager);

                            RequestDateAdapter adapter = new RequestDateAdapter(lst_getReqPartnerDetail, itemClick);
                            recycler_view.setAdapter(adapter);

                        }


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
            public void onFailure(Call<CreateDateGetMyPartnerReqModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
