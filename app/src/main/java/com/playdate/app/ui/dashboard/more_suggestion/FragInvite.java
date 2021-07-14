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
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.model.Premium;
import com.playdate.app.model.PremiumPlans;
import com.playdate.app.ui.dashboard.OnClosePremium;
import com.playdate.app.ui.dashboard.adapter.InviteAdapter;
import com.playdate.app.ui.dashboard.premium.PremiumAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragInvite extends Fragment implements OnClosePremium {

    public FragInvite() {
    }

    private RecyclerView recyclerView;
    private ViewPager vp_premium;
    public SessionPref pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_invite, container, false);
        recyclerView = view.findViewById(R.id.invite_listView);
        vp_premium = view.findViewById(R.id.vp_premium);
        pref = SessionPref.getInstance(getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        CommonClass commonClass = CommonClass.getInstance();
        vp_premium.getLayoutParams().height = (int) (commonClass.getScreenHeight(getActivity()) - getActivity().getResources().getDimension(R.dimen._250sdp));


        callAPI();
        callAPIShowPremium();
        return view;
    }

    ArrayList<PremiumPlans> lstPremium;

    private void callAPIShowPremium() {
        //showPremium();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        Call<Premium> call = service.getPackages("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<Premium>() {
            @Override
            public void onResponse(Call<Premium> call, Response<Premium> response) {
                try {

                    if (response.code() == 200) {
                        if (response.body().getStatus() == 1) {
                            lstPremium = response.body().getLstPremiumPlan();
                            PremiumAdapter adapter = new PremiumAdapter(getFragmentManager(), FragInvite.this,lstPremium);
                            vp_premium.setAdapter(adapter);
                            setFun();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Premium> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    int page = 0;

    private void setFun() {
        vp_premium.postDelayed(() -> {
            if (page > 2) {
                page = 0;
            }
            vp_premium.setCurrentItem(page);
            page++;
            setFun();

        }, 3000);
    }

    ArrayList<GetProileDetailData> lst_getPostDetail;
    String inviteCode;
    String inviteLink;

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();

        Call<GetProfileDetails> call = service.getProfileDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetProfileDetails>() {
            @Override
            public void onResponse(Call<GetProfileDetails> call, Response<GetProfileDetails> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
                        if (lst_getPostDetail == null) {
                            lst_getPostDetail = new ArrayList<>();
                        }
                        inviteCode = String.valueOf(lst_getPostDetail.get(0).getInviteCode());
                        inviteLink = String.valueOf(lst_getPostDetail.get(0).getInviteLink());
                        Log.e("inviteCode", "" + inviteCode);
                        Log.e("inviteLink", "" + inviteLink);
                        InviteAdapter adapter = new InviteAdapter(inviteCode,
                                inviteLink);
                        recyclerView.setAdapter(adapter);
                    }
                }


            }

            @Override
            public void onFailure(Call<GetProfileDetails> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void closePremium() {
        vp_premium.setVisibility(View.GONE);
    }
}
