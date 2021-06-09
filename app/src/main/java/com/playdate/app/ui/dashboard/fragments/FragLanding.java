package com.playdate.app.ui.dashboard.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.adapter.SuggestionAdapter;
import com.playdate.app.ui.dashboard.more_suggestion.FragMoreSuggestion;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragLanding extends Fragment {
    public FragLanding() {
    }

    private CommonClass clsCommon;
    private ViewPager vp_suggestion;
    private ArrayList<GetUserSuggestionData> lst_getUserSuggestions;
    private Onclick itemClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_landing, container, false);
        vp_suggestion = view.findViewById(R.id.vp_suggestion);
        RelativeLayout rl_page = view.findViewById(R.id.Rl_page);
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

        callGetUserSuggestionAPI();


        int height = new CommonClass().getScreenHeight(getActivity());

        int m1 = (int) getResources().getDimension(R.dimen._20sdp);
        int m2 = (int) getResources().getDimension(R.dimen._20sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._65sdp);

        rl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        TextView txt_footer_see_more = view.findViewById(R.id.txt_footer_see_more);
        txt_footer_see_more.setOnClickListener(v -> {

            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragMoreSuggestion());
        });

        callGetUserSuggestionAPI();
        return view;
    }

    private void callGetUserSuggestionAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("filter", "");
        hashMap.put("limit", "10");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<GetUserSuggestion> call = service.getUserSuggestion("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
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


                        // SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
                        SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), lst_getUserSuggestions, itemClick);
                        vp_suggestion.setAdapter(adapter);
                        vp_suggestion.setCurrentItem(8);
                        vp_suggestion.setPadding(130, 0, 130, 0);
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


    private void callAddFriendRequestApi(String s) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toUserID", s);
        SessionPref pref = SessionPref.getInstance(getActivity());
        Log.e("CommonModel", "" + pref.getStringVal(SessionPref.LoginUsertoken));

        Call<CommonModel> call = service.addFriendRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        Log.e("CommonModelData", "" + hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
