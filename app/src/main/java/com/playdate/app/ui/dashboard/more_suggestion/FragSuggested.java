package com.playdate.app.ui.dashboard.more_suggestion;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.playdate.app.model.FriendRequest;
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.adapter.SuggestedFriendAdapter;
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

public class FragSuggested extends Fragment {
    RecyclerView recyclerView;
    ArrayList<GetUserSuggestionData> lst_getUserSuggestions;
 FriendRequest firend = new FriendRequest();
    CommonClass clsCommon;
    Onclick itemClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_suggested_list, container, false);
        recyclerView = view.findViewById(R.id.friend_list);
        clsCommon = CommonClass.getInstance();
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {
                if (value == 10) {
                    callAddFriendRequestApi(s);
                }
            }
        };
        callGetUserSuggestionAPI();
        return view;
    }

    private void callAddFriendRequestApi(String s) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", s);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Log.e("CommonModel", "" + pref.getStringVal(SessionPref.LoginUsertoken));

        Call<CommonModel> call = service.addFriendRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("CommonModelData", "" + hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "Request Sent! " + s, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", ""+response.body().getMessage(), "Ok");
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
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetUserSuggestionAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("filter", "");
        hashMap.put("limit", "50");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Log.e("GetUserSuggestionData", "" + pref.getStringVal(SessionPref.LoginUsertoken));

        Call<GetUserSuggestion> call = service.getUserSuggestion("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("GetUserSuggestionData", "" + hashMap);
        call.enqueue(new Callback<GetUserSuggestion>() {
            @Override
            public void onResponse(Call<GetUserSuggestion> call, Response<GetUserSuggestion> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        lst_getUserSuggestions = (ArrayList<GetUserSuggestionData>) response.body().getData();
                        if (lst_getUserSuggestions == null) {
                            lst_getUserSuggestions = new ArrayList<>();
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        SuggestedFriendAdapter adapter = new SuggestedFriendAdapter(lst_getUserSuggestions, itemClick);
                        recyclerView.setAdapter(adapter);
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
            public void onFailure(Call<GetUserSuggestion> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
