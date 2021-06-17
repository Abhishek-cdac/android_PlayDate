package com.playdate.app.ui.coupons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.playdate.app.model.RestMain;
import com.playdate.app.ui.dashboard.adapter.RestaurentListAdapter;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.session.SessionPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCouponParent extends Fragment implements OnSizeDecided, View.OnClickListener {
    WrapContentViewPager viewpager;
    RecyclerView rv_restaurant;
    private RestaurentListAdapter adapterRestaurent;
    private SessionPref pref;
    private TextView txt_store;
    private TextView txt_my_coupon;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupon_parent, container, false);
        viewpager = view.findViewById(R.id.viewpager);
        txt_store = view.findViewById(R.id.txt_store);
        txt_my_coupon = view.findViewById(R.id.txt_my_coupon);
        pref = SessionPref.getInstance(getActivity());
        rv_restaurant = view.findViewById(R.id.rv_restaurant);
        CoupounPageAdapter adapter = new CoupounPageAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        adapterRestaurent = new RestaurentListAdapter(new ArrayList<>());
        rv_restaurant.setAdapter(adapterRestaurent);
        rv_restaurant.setLayoutManager(manager1);
        callAPIRestaurant();
        txt_store.setOnClickListener(this);
        txt_my_coupon.setOnClickListener(this);

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
                        ArrayList<Restaurant> restaurentlst = (ArrayList<Restaurant>) response.body().getLst();
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
        } else {
            viewpager.setCurrentItem(1);
        }
    }
}


