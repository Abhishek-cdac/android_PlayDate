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
import com.playdate.app.model.GetUserSuggestion;
import com.playdate.app.model.GetUserSuggestionData;
import com.playdate.app.model.PartnerImage;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.dashboard.adapter.SuggestionAdapter;
import com.playdate.app.ui.date.adapter.PartnerViewPagerAdapter;
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

public class FragSelectPartner extends Fragment {

    //implements OnPartnerSelectedListener {
    ArrayList<PartnerImage> list = new ArrayList<>();
    ArrayList<CreateDateGetPartnerData> lst_CreateDateGetPartner;
    //    PartnerViewPagerAdapter adapter;
    Button btn_search_partner;
    ViewPager vp_partners;
    TextView tv_waiting;
    TextView tv_or;
    SpinKitView spin_kit;
    CommonClass clsCommon;
    Onclick itemClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_select_partner, container, false);

        clsCommon = CommonClass.getInstance();
        vp_partners = view.findViewById(R.id.vp_partners);
        btn_search_partner = view.findViewById(R.id.btn_search_partner);
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
                if (i == 20) {
                    OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();

                    Fragment fragment = new FragPartnerSelected();
                    Bundle bundle = new Bundle();
                    bundle.putString("profile_image", lst_CreateDateGetPartner.get(absoluteAdapterPosition).getProfilePicPath());
                    bundle.putString("profile_name", lst_CreateDateGetPartner.get(absoluteAdapterPosition).getUsername());
                    bundle.putString("profile_points", lst_CreateDateGetPartner.get(absoluteAdapterPosition).getTotalPoints());
                    fragment.setArguments(bundle);
                    frag.ReplaceFrag(fragment);
                }
            }
        };

        callCreateDateGetPartnerListAPI();


        /*list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://images.saymedia-content.com/.image/t_share/MTc0MDkwNjUxNDc2OTYwODM0/5-instagram-models-you-should-be-following.png", "malaika2124", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1400"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/esquireuk.cdnds.net/17/17/elizabeth-turner.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "myron1122", "1300"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "geneeeres", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
        list.add(new PartnerImage("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/4p3a7420-copy-1524689604.jpg", "kaylajenner12", "1200"));
*/

       /* PartnerViewPagerAdapter  adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner, this);
        // adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner);
        vp_partners.setAdapter(adapter);
        vp_partners.setClipToPadding(false);
        vp_partners.setPageMargin(-450);*/

        return view;
    }


/*    @Override
    public void OnPortnerSelect(int position) {

        OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();

        Fragment fragment = new FragPartnerSelected();
        Bundle bundle = new Bundle();
        bundle.putString("profile_image", list.get(position).getImage());
        bundle.putString("profile_name", list.get(position).getName());
        bundle.putString("profile_points", list.get(position).getPoints());
        fragment.setArguments(bundle);
        frag.ReplaceFrag(fragment);

        // Intent intent = new Intent(view.getContext(), PartnerSelected.class);

    }*/

    private void callCreateDateGetPartnerListAPI() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "50");
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

                        Log.e("CreateDate", "CreateDateGetPartnerData");
                        lst_CreateDateGetPartner = (ArrayList<CreateDateGetPartnerData>) response.body().getData();
                        if (lst_CreateDateGetPartner == null) {
                            lst_CreateDateGetPartner = new ArrayList<>();
                        }
                        Log.e("CreateDate", "" + lst_CreateDateGetPartner.size());


                        // SuggestionAdapter adapter = new SuggestionAdapter(getActivity());
                        //   SuggestionAdapter adapter = new SuggestionAdapter(getActivity(), lst_CreateDateGetPartner, itemClick);

                        PartnerViewPagerAdapter adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner, itemClick);
                        // adapter = new PartnerViewPagerAdapter(getActivity(), lst_CreateDateGetPartner);
                        vp_partners.setAdapter(adapter);
                        vp_partners.setClipToPadding(false);
                        vp_partners.setPageMargin(-450);

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


}

interface OnPartnerSelectedListener {
    void OnPortnerSelect(int inex);
}


