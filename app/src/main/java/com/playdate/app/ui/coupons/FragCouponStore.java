package com.playdate.app.ui.coupons;


import android.content.Intent;
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
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Account;
import com.playdate.app.model.GetCouponsData;
import com.playdate.app.model.GetCouponsModel;
import com.playdate.app.model.GetProfileDetails;
import com.playdate.app.model.GetProileDetailData;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.adapters.CouponStoreAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCouponStore extends Fragment {
    private RecyclerView rv_coupons_list;
    private ArrayList<GetCouponsData> lst_getCoupons;
    private CommonClass clsCommon;
    private Onclick itemClick;

    public FragCouponStore() {
    }

    private TextView txt_points;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_coupons_list, container, false);
        clsCommon = CommonClass.getInstance();
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        txt_points = view.findViewById(R.id.txt_points);

        itemClick = new Onclick() {
            @Override
            public void onItemClick(View view, int position, int value) {

            }

            @Override
            public void onItemClicks(View view, int position, int value, String s) {

            }

            @Override
            public void onItemClicks(View v, int adapterPosition, int i, String notifiationId, String userId) {

            }

            @Override
            public void onItemClicks(View v, int absoluteAdapterPosition, int i, String Coupon_id, String Coupon_code, String Coupon_points) {

                if (i == 11) {
                    try {
                        Intent intent = new Intent(getActivity(), ActivityCoupons.class);
                        intent.putExtra("Coupon_id", Coupon_id);
                        intent.putExtra("Coupon_code", Coupon_code);
                        intent.putExtra("Coupon_points", Coupon_points);
                        intent.putExtra("isFromCoupon", true);

                        if (null != account)
                            intent.putExtra("CurrentPoints", account.getCurrentPoints());

                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//

                }

            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };
        callGetCouponsApi();


        return view;
    }

    @Override
    public void onResume() {
        callAPIProfiileDetails();
        super.onResume();
    }

    private void callAPIProfiileDetails() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();


        Call<GetProfileDetails> call = service.getProfileDetails("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetProfileDetails>() {
            @Override
            public void onResponse(Call<GetProfileDetails> call, Response<GetProfileDetails> response) {
                pd.cancel();
                if (response.code() == 200) {
                    try {
                        if (response.body().getStatus() == 1) {
                            ArrayList<GetProileDetailData> lst_getPostDetail;
                            lst_getPostDetail = (ArrayList<GetProileDetailData>) response.body().getData();
                            if (lst_getPostDetail == null) {
                                lst_getPostDetail = new ArrayList<>();
                            }
                            account = lst_getPostDetail.get(0).getAccount().get(0);
                            txt_points.setText("" + account.getCurrentPoints());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<GetProfileDetails> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
            }
        });
    }

    Account account;

    private void callGetCouponsApi() {
        SessionPref pref = SessionPref.getInstance(getActivity());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        // hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("limit", "50");
        hashMap.put("pageNo", "1");


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<GetCouponsModel> call = service.getCoupons("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetCouponsModel>() {
            @Override
            public void onResponse(Call<GetCouponsModel> call, Response<GetCouponsModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        lst_getCoupons = (ArrayList<GetCouponsData>) response.body().getData();
                        if (lst_getCoupons == null) {
                            lst_getCoupons = new ArrayList<>();
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        rv_coupons_list.setLayoutManager(manager);

                        CouponStoreAdapter adapter = new CouponStoreAdapter(lst_getCoupons, itemClick);
                        rv_coupons_list.setAdapter(adapter);
//                        RestaurantSelectionAdapter adapter = new RestaurantSelectionAdapter(getActivity(), lst_getRestaurantsDetail);
//                        rv_restaurant.setAdapter(adapter);


                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetCouponsModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
