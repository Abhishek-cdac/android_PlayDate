package com.playdate.app.ui.coupons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.coupons.adapters.CouponStoreAdapter;
import com.playdate.app.ui.coupons.adapters.MyCouponAdapter;

public class FragCouponStore extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupons_list, container, false);
        RecyclerView rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

//        CouponStoreAdapter adapter = new CouponStoreAdapter();
//        rv_coupons_list.setAdapter(adapter);

        MyCouponAdapter adapter1 = new MyCouponAdapter();
        rv_coupons_list.setAdapter(adapter1);

        return view;
    }
}
