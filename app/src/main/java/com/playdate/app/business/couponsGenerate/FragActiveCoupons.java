package com.playdate.app.business.couponsGenerate;

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
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;

public class FragActiveCoupons extends Fragment {
    private RecyclerView rv_coupons_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_active_coupons, container, false);
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        ActiveCouponsAdapter adapter = new ActiveCouponsAdapter();
        rv_coupons_list.setAdapter(adapter);

        return view;
    }
}
