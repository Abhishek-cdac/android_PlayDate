package com.playdate.app.business.couponsGenerate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.CoupounPageBusinessAdapter;

import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.NotificationCountModel;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.util.session.SessionPref;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCouponParentBusiness extends Fragment   {

    //    WrapContentViewPager viewpager;
    ViewPager viewpager;
    private RecyclerView rv_coupons_list;
    private SessionPref pref;
    private TextView txt_coupons;
    private TextView generator;
    ImageView iv_dashboard_notification;
    TextView txt_count;
    EditText edt_search;
    TabLayout tabLayout;
    private Fragment CurrentFrag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_parent_business, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        iv_dashboard_notification = view.findViewById(R.id.iv_dashboard_notification);
        txt_coupons = view.findViewById(R.id.txt_coupons);
        txt_count = view.findViewById(R.id.txt_count);
        generator = view.findViewById(R.id.generator);
        edt_search = view.findViewById(R.id.edt_search);
        pref = SessionPref.getInstance(getActivity());
        tabLayout = view.findViewById(R.id.tab);
        CallNotificationCount();
        tabLayout.addTab(tabLayout.newTab().setText(" Active "));
        tabLayout.addTab(tabLayout.newTab().setText("   Expired   "));

        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   new FragCouponsGenerater(getActivity()).show();
                startActivity(new Intent(getActivity(),CouponGenActivity.class));
            }
        });

        iv_dashboard_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnInnerFragmentClicks ref = (OnInnerFragmentClicks) getActivity();
                Objects.requireNonNull(ref).ReplaceFrag(new FragNotification(""));
            }
        });

        CoupounPageBusinessAdapter pagerAdapter = new CoupounPageBusinessAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(0);
        tabLayout.setTabIndicatorFullWidth(false);
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


        return view;


    }

    private void CallNotificationCount() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        Call<NotificationCountModel> call = service.getNotificationCount("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<NotificationCountModel>() {
            @Override
            public void onResponse(Call<NotificationCountModel> call, Response<NotificationCountModel> response) {
                try {

                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getStatus() == 1) {
                            int countNotification = response.body().getData().get(0).getTotalUnreadNotification();
                            Log.e("countNotification", "" + countNotification);

                            if (countNotification > 0 && countNotification <= 9) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setPadding(10, 0, 10, 0); //1-9
                                txt_count.setText("" + countNotification);

                            } else if (countNotification > 9 && countNotification <= 99) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setPadding(6, 2, 7, 2);  ///10-99
                                txt_count.setText("" + countNotification);

                            } else if (countNotification > 99) {
                                txt_count.setVisibility(View.VISIBLE);
                                txt_count.setTextSize(8);
                                txt_count.setPadding(5, 3, 5, 3);  ///99+
                                txt_count.setText("99+");

                            } else {
                                txt_count.setVisibility(View.GONE);
                                txt_count.setText("");

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NotificationCountModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}