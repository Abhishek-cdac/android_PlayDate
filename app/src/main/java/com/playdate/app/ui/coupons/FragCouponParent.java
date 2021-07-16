package com.playdate.app.ui.coupons;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.NotificationCountModel;
import com.playdate.app.model.RestMain;
import com.playdate.app.ui.dashboard.OnAPIResponce;
import com.playdate.app.ui.dashboard.adapter.RestaurentListAdapter;
import com.playdate.app.ui.dashboard.data.CallAPI;
import com.playdate.app.ui.dashboard.fragments.FragmentSearchRestaurent;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.notification_screen.FragNotification;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCouponParent extends Fragment implements OnSizeDecided, View.OnClickListener, OnInnerFragmentClicks {
    //, OnAPIResponce {
    private WrapContentViewPager viewpager;
    private RecyclerView rv_restaurant;
    private RestaurentListAdapter adapterRestaurent;
    private SessionPref pref;
    private TextView txt_store;
    private TextView txt_my_coupon;



    private TextView txt_count;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_parent, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        callNotification();
        ImageView iv_dashboard_notification = view.findViewById(R.id.iv_dashboard_notification);
        ImageView iv_rest_search = view.findViewById(R.id.iv_rest_search);
        txt_store = view.findViewById(R.id.txt_store);
        txt_count = view.findViewById(R.id.txt_count);
        txt_my_coupon = view.findViewById(R.id.txt_my_coupon);
        pref = SessionPref.getInstance(getActivity());
        rv_restaurant = view.findViewById(R.id.rv_restaurant);
        CoupounPageAdapter adapter = new CoupounPageAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        adapterRestaurent = new RestaurentListAdapter(new ArrayList<>());
        rv_restaurant.setAdapter(adapterRestaurent);
        rv_restaurant.setLayoutManager(manager1);
        CallNotificationCount();
        callAPIRestaurant();

        txt_store.setOnClickListener(this);
        txt_my_coupon.setOnClickListener(this);
        iv_rest_search.setOnClickListener(this);
        iv_dashboard_notification.setOnClickListener(this);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.reMeasureCurrentPage(position);
                if (position == 0) {
                    txt_store.setTextColor(getResources().getColor(R.color.white));
                    txt_store.setBackground(getResources().getDrawable(R.drawable.menu_button));
                    txt_my_coupon.setBackground(null);
                    txt_my_coupon.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    txt_my_coupon.setTextColor(getResources().getColor(R.color.white));
                    txt_my_coupon.setBackground(getResources().getDrawable(R.drawable.menu_button));
                    txt_store.setBackground(null);
                    txt_store.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void CallNotificationCount() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("limit", "100");
//        hashMap.put("pageNo", "1");

        //  Call<RestMain> call = service.restaurants("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
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

    @Override
    public void setSize(double size) {
        Toast.makeText(getActivity(), "" + size, Toast.LENGTH_SHORT).show();
//        viewpager.getLayoutParams().height= (int) (size*100);
    }

    private void callAPIRestaurant() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");

        Call<RestMain> call = service.restaurants("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<RestMain>() {
            @Override
            public void onResponse(Call<RestMain> call, Response<RestMain> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        ArrayList<Restaurant> restaurentlst = response.body().getLst();
                        if (restaurentlst == null) {
                            restaurentlst = new ArrayList<>();
                        }
                        if (restaurentlst.size() > 0) {
//                            txt_serachfriend.setVisibility(View.GONE);
                            rv_restaurant.setVisibility(View.VISIBLE);
                            adapterRestaurent.updateList(restaurentlst);

                        } else {
//                            txt_serachfriend.setVisibility(View.VISIBLE);
                            rv_restaurant.setVisibility(View.GONE);
                        }
                    }
                }
//                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RestMain> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_store) {
            viewpager.setCurrentItem(0);
        } else if (v.getId() == R.id.iv_dashboard_notification) {
            try {
                ReplaceFrag(new FragNotification("Coupons"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            viewpager.setCurrentItem(1);
        }


        if (v.getId() == R.id.iv_rest_search) {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragmentSearchRestaurent());

        }
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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {

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

    CallAPI apiCall;

    void callNotification() {
        if (null != apiCall) {
            apiCall.callGetNotificationAPI(getContext());
        } else {
            new CallAPI().callGetNotificationAPI(getContext());
        }

    }

   /* @Override
    public void setNotiCount(int count) {
       */
    /* if (count > 0) {
            txt_count.setVisibility(View.VISIBLE);
            txt_count.setText("" + count);
        } else {
            txt_count.setVisibility(View.GONE);
            txt_count.setText("");
        }*/
    /*
    }*/

    @Override
    public void onResume() {
        super.onResume();
        callNotification();
    }
}


