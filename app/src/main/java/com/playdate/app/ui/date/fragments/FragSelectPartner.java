package com.playdate.app.ui.date.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CreateDateGetPartnerData;
import com.playdate.app.model.CreateDateGetPartnerModel;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.date.adapter.PartnerViewPagerAdapter;
import com.playdate.app.ui.date.adapter.SuggestedDateAdapter;
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

public class FragSelectPartner extends Fragment implements SuggestedDateAdapter.SuggestionsAdapterListner {


    //    private ArrayList<PartnerImage> list = new ArrayList<>();
    private ArrayList<CreateDateGetPartnerData> lst_CreateDateGetPartner;
    private Onclick itemClick;
    private ViewPager vp_partners;
    private TextView tv_waiting;
    private TextView tv_or;
    private SpinKitView spin_kit;
    private CommonClass clsCommon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_select_partner, container, false);

        clsCommon = CommonClass.getInstance();
        vp_partners = view.findViewById(R.id.vp_partners);
        Button btn_search_partner = view.findViewById(R.id.btn_search_partner);
        btn_search_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                Fragment fragment = new FragSearchDate();
                frag.ReplaceFrag(fragment);
            }
        });
        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {

            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String commentId, String postId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {
                if (i == 20) {

                    try {
                        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                        Fragment fragment = new FragPartnerSelected();
                        Bundle bundle = new Bundle();
                        bundle.putString("profile_name", lst_CreateDateGetPartner.get(position).getUsername());
                        bundle.putString("profile_points", lst_CreateDateGetPartner.get(position).getTotalPoints());
                        bundle.putString("profile_userId", lst_CreateDateGetPartner.get(position).getId());
                        bundle.putString("profile_image", lst_CreateDateGetPartner.get(position).getProfilePicPath());
                        Log.e("profile_toUserID..", "" + lst_CreateDateGetPartner.get(position).getId());
                        fragment.setArguments(bundle);
                        assert frag != null;
                        frag.ReplaceFrag(fragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        callCreateDateGetPartnerListAPI();


        return view;
    }


    private void callCreateDateGetPartnerListAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit","100");
        hashMap.put("pageNo", "1");//Hardcode
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        SessionPref pref = SessionPref.getInstance(getActivity());
        Call<CreateDateGetPartnerModel> call = service.createDateGetPartnerList("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CreateDateGetPartnerModel>() {
            @Override
            public void onResponse(Call<CreateDateGetPartnerModel> call, Response<CreateDateGetPartnerModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        lst_CreateDateGetPartner = (ArrayList<CreateDateGetPartnerData>) response.body().getData();
                        if (lst_CreateDateGetPartner == null) {
                            lst_CreateDateGetPartner = new ArrayList<>();
                        }

                        PartnerViewPagerAdapter adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner, itemClick);
                        // adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner);
                        vp_partners.setAdapter(adapter);
                        vp_partners.setClipToPadding(false);
                        vp_partners.setPageMargin(-450);
//                        vp_partners.setPageTransformer(true, new RotateUpTransformer());

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
            public void onFailure(Call<CreateDateGetPartnerModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onSuggestionSelected(CreateDateGetPartnerData createDateGetPartnerData) {

    }
}



