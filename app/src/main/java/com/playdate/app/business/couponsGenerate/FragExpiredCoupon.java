package com.playdate.app.business.couponsGenerate;

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

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ExpiredCouponsAdapter;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.model.GetBusinessCouponModel;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragExpiredCoupon extends Fragment {
    private RecyclerView rv_coupons_list;
    private TextView noCoupon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_expired_coupons, container, false);
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        noCoupon = view.findViewById(R.id.no_coupon);
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
        hashMap.put("status", "Expired");

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<GetBusinessCouponModel> call = service.getBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetBusinessCouponModel>() {
            @Override
            public void onResponse(@NonNull Call<GetBusinessCouponModel> call, @NonNull Response<GetBusinessCouponModel> response) {
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
                                if (GetBusinessCouponLst.size() == 0) {
                                    noCoupon.setVisibility(View.VISIBLE);
                                    rv_coupons_list.setVisibility(View.GONE);
                                } else {
                                    noCoupon.setVisibility(View.GONE);
                                    rv_coupons_list.setVisibility(View.VISIBLE);

                                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                    rv_coupons_list.setLayoutManager(manager);
                                    ExpiredCouponsAdapter adapter = new ExpiredCouponsAdapter(GetBusinessCouponLst);
                                    rv_coupons_list.setAdapter(adapter);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<GetBusinessCouponModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
