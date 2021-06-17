package com.playdate.app.ui.coupons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Account;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.model.MyCoupons;
import com.playdate.app.model.MyCouponsModel;
import com.playdate.app.model.MyCouponsWrap;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.adapters.MyCouponAdapter;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragMyCoupons extends Fragment {
    private RecyclerView rv_coupons_list;
    private Onclick itemClick;
    private TextView txt_points;

    public FragMyCoupons() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_my_coupan_code, container, false);
        //  private ArrayList<GetCouponsData> lst_getCoupons;
//        CommonClass clsCommon = CommonClass.getInstance();
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        txt_points = view.findViewById(R.id.txt_points);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        callAPIGetMyCoupons();


        return view;
    }

    @Override
    public void onResume() {
//        callAPIProfiileDetails();
        super.onResume();
    }

//    private void callAPIProfiileDetails() {
//        SessionPref pref = SessionPref.getInstance(getActivity());
//
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Map<String, String> hashMap = new HashMap<>();
//
//        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
//
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
//        pd.show();
//
//
//        Call<GetProfileDetails> call = service.getProfileDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        call.enqueue(new Callback<GetProfileDetails>() {
//            @Override
//            public void onResponse(Call<GetProfileDetails> call, Response<GetProfileDetails> response) {
//                pd.cancel();
//                if (response.code() == 200) {
//                    try {
//                        if (response.body().getStatus() == 1) {
//                            ArrayList<GetProileDetailData> lst_getPostDetail;
//                            lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
//                            if (lst_getPostDetail == null) {
//                                lst_getPostDetail = new ArrayList<>();
//                            }
//                            account = lst_getPostDetail.get(0).getAccount().get(0);
//                            txt_points.setText("" + account.getCurrentPoints());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<GetProfileDetails> call, Throwable t) {
//                t.printStackTrace();
//                pd.cancel();
//            }
//        });
//    }

//    Account account;

    private void callAPIGetMyCoupons() {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", "100");
        SessionPref pref = SessionPref.getInstance(getActivity());

        Call<MyCouponsModel> call = service.getMyCoupons("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<MyCouponsModel>() {
            @Override
            public void onResponse(Call<MyCouponsModel> call, Response<MyCouponsModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        MyCouponsWrap wrap = response.body().getData();
                        List<MyCoupons> lst = wrap.getLst();
                        if (lst == null) {
                            lst = new ArrayList<>();
                        }
                        txt_points.setText("" + wrap.getAccount().getCurrentPoints());
                        MyCouponAdapter adapter = new MyCouponAdapter(lst);
                        rv_coupons_list.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<MyCouponsModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
