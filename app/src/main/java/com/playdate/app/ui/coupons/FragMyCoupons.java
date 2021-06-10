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
import com.playdate.app.model.GetCouponsData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.adapters.CouponStoreAdapter;
import com.playdate.app.ui.coupons.adapters.MyCouponAdapter;
import com.playdate.app.util.common.CommonClass;

import java.util.ArrayList;

public class FragMyCoupons extends Fragment {
    RecyclerView rv_coupons_list;
  //  private ArrayList<GetCouponsData> lst_getCoupons;
    private CommonClass clsCommon;
    Onclick itemClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_coupan_code, container, false);
        clsCommon = CommonClass.getInstance();
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        MyCouponAdapter adapter = new MyCouponAdapter();
        rv_coupons_list.setAdapter(adapter);
        return view;
    }
}
