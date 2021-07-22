package com.playdate.app.business.couponsGenerate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.CoupounPageBusinessAdapter;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.model.GetBusinessCouponModel;
import com.playdate.app.ui.coupons.OnSizeDecided;
import com.playdate.app.ui.coupons.WrapContentViewPager;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCouponParentBusiness extends Fragment implements OnInnerFragmentClicks, OnSizeDecided {

    private WrapContentViewPager viewpager;

    public FragCouponParentBusiness() {
    }
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_parent_business, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        ImageView iv_dashboard_notification = view.findViewById(R.id.iv_dashboard_notification);
//        TextView txt_coupons = view.findViewById(R.id.txt_coupons);
        TextView txt_count = view.findViewById(R.id.txt_count);
        TextView generator = view.findViewById(R.id.generator);
        EditText edt_search = view.findViewById(R.id.edt_search);
//        SessionPref pref = SessionPref.getInstance(getActivity());
         tabLayout = view.findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setText(" Active "));
        tabLayout.addTab(tabLayout.newTab().setText("   Expired   "));



        generator.setOnClickListener(v -> startActivity(new Intent(getActivity(),CouponGenActivity.class)));


        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.reMeasureCurrentPage(position);
                if (position == 0) {
                } else {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        callGetBusinessCouponAPI();
        return view;
    }

    private void callGetBusinessCouponAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<GetBusinessCouponModel> call = service.getBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetBusinessCouponModel>() {
            @Override
            public void onResponse(Call<GetBusinessCouponModel> call, Response<GetBusinessCouponModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        try {
                            assert response.body() != null;
                            if (response.body().getStatus() == 1) {

                                ArrayList<GetBusinessCouponData> GetBusinessCouponLst = (ArrayList<GetBusinessCouponData>) response.body().getData();
                                if (GetBusinessCouponLst == null) {
                                    GetBusinessCouponLst = new ArrayList<>();
                                }
                                CoupounPageBusinessAdapter pagerAdapter = new CoupounPageBusinessAdapter(getChildFragmentManager(), tabLayout.getTabCount(), GetBusinessCouponLst);
                                viewpager.setAdapter(pagerAdapter);

                                viewpager.setCurrentItem(0);
                                tabLayout.setTabIndicatorFullWidth(false);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {


                }


            }

            @Override
            public void onFailure(Call<GetBusinessCouponModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void ReplaceFrag(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {

                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.commitAllowingStateLoss();

            //  mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();

            //  mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void NoFriends() {

    }

    @Override
    public void Reset() {

    }

    @Override
    public void loadProfile(String UserID) {

    }

    @Override
    public void loadMatchProfile(String UserID) {

    }

    @Override
    public void setSize(double size) {

    }
}