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
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;
import com.playdate.app.business.couponsGenerate.adapter.ExpiredCouponsAdapter;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.model.GetBusinessCouponModel;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragExpiredCoupon extends Fragment {
    private ArrayList<GetBusinessCouponData> lst;
    RecyclerView rv_coupons_list;
    TextView noCoupon;
//    public FragExpiredCoupon(ArrayList<GetBusinessCouponData> lst) {
//    this.lst=lst;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_expired_coupons, container, false);
         rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        noCoupon = view.findViewById(R.id.no_coupon);
         callGetBusinessCouponAPI();
        //        *****Temp Code*****
  /*      String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(inputPattern, Locale.getDefault());
        String formattedDate = df.format(c);
        ArrayList<GetBusinessCouponData> lstTemp = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            try {
                String[] s = lst.get(i).getCouponValidTillDate().split("T");
                Date date = inputFormat.parse(s[0]);
                Date date1 = inputFormat.parse(formattedDate);
                if (date.before(date1)) {
                    lstTemp.add(lst.get(i));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }*/

//        *****Temp Code*****

/*
        ExpiredCouponsAdapter adapter = new ExpiredCouponsAdapter(lstTemp);
        rv_coupons_list.setAdapter(adapter);*/

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
                                if (GetBusinessCouponLst.size()==0){
                                    noCoupon.setVisibility(View.VISIBLE);
                                    rv_coupons_list.setVisibility(View.GONE);
                                }
                                else{
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


                    } else {
                   //    clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
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

}
