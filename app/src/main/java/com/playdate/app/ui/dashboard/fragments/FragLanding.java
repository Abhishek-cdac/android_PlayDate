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
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragLanding extends Fragment {
    CommonClass clsCommon;
    ViewPager vp_suggestion;
    RelativeLayout Rl_page;
    ArrayList<GetUserSuggestionData> lst_getUserSuggestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_landing, container, false);
        vp_suggestion = view.findViewById(R.id.vp_suggestion);
        Rl_page = view.findViewById(R.id.Rl_page);
        clsCommon = CommonClass.getInstance();
      //  callGetUserSuggestionAPI();

     // SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), getActivity().getSupportFragmentManager());
        SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
        vp_suggestion.setAdapter(adapter);
        vp_suggestion.setCurrentItem(8);
        vp_suggestion.setPadding(130, 0, 130, 0);


        int height = new CommonClass().getScreenHeight(getActivity());

        int m1 = (int) getResources().getDimension(R.dimen._20sdp);
        int m2 = (int) getResources().getDimension(R.dimen._20sdp);
        int m3 = (int) getResources().getDimension(R.dimen._20sdp);
        int m4 = (int) getResources().getDimension(R.dimen._20sdp);
        int m5 = (int) getResources().getDimension(R.dimen._60sdp);
        int m6 = (int) getResources().getDimension(R.dimen._65sdp);

        Rl_page.getLayoutParams().height = height - (m1 + m2 + m3 + m4 + m5 + m6);

        TextView txt_footer_see_more = view.findViewById(R.id.txt_footer_see_more);
        txt_footer_see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                frag.ReplaceFrag(new FragMoreSuggestion());
            }
        });

        callGetUserSuggestionAPI();
        return view;
    }

    private void callGetUserSuggestionAPI() {


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("filter", "");
        hashMap.put("limit", "10");
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
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        lst_getUserSuggestions = (ArrayList<GetUserSuggestionData>) response.body().getData();
                        if (lst_getUserSuggestions == null) {
                            lst_getUserSuggestions = new ArrayList<>();
                        }

                        Log.e("GetUserDataSize", "" + lst_getUserSuggestions.size());

                        SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
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
}
