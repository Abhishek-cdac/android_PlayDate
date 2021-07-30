package com.playdate.app.business.couponsGenerate;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.business.couponsGenerate.adapter.ActiveCouponsAdapter;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.model.GetBusinessCouponModel;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragActiveCoupons extends Fragment implements OnInnerFragmentClicks {
    private RecyclerView rv_coupons_list;
    private ActiveCouponsAdapter adapter;
    private TextView no_coupon;
    private CommonClass clsCommon;
    private ArrayList<GetBusinessCouponData> GetBusinessCouponLst = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_active_coupons, container, false);
        clsCommon = CommonClass.getInstance();

        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        no_coupon = view.findViewById(R.id.no_coupon);
        callGetBusinessCouponAPI();
        enableSwipeToDeleteAndUndo();
        enableSwipeToUpdateAndUndo();



        return view;
    }


    private void enableSwipeToUpdateAndUndo() {
        SwipeToUpdateCallback swipeToUpdateCallback = new SwipeToUpdateCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(getActivity(), CouponGenUpdateActivity.class);
                intent.putExtra("couponId", GetBusinessCouponLst.get(position).getCouponId());
                intent.putExtra("couponTitle", GetBusinessCouponLst.get(position).getCouponTitle());
                intent.putExtra("couponPercentageOff", GetBusinessCouponLst.get(position).getCouponPercentageValue());
                intent.putExtra("couponAvailableDays", GetBusinessCouponLst.get(position).getCouponValidTillDate());
                intent.putExtra("couponAmountOff", GetBusinessCouponLst.get(position).getCouponAmountOf());
                intent.putExtra("couponNewPrice", GetBusinessCouponLst.get(position).getNewPrice());
                intent.putExtra("couponFreeItem", GetBusinessCouponLst.get(position).getFreeItem());
                intent.putExtra("couponPointsValue", GetBusinessCouponLst.get(position).getCouponPurchasePoint());
                intent.putExtra("couponAwardlevelValue", GetBusinessCouponLst.get(position).getAwardlevelValue());
                intent.putExtra("CouponImage", GetBusinessCouponLst.get(position).getCouponImage());
                intent.putExtra("awardedByUpdate", GetBusinessCouponLst.get(position).getAwardedBy());
                startActivity(intent);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToUpdateCallback);
        itemTouchHelper.attachToRecyclerView(rv_coupons_list);
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                String couponId = GetBusinessCouponLst.get(position).getCouponId();
                callDeleteBusinessCouponAPI(couponId);
                // adapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(rv_coupons_list);
    }

    private void callDeleteBusinessCouponAPI(String couponId) {

        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("couponId", couponId);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<CommonModel> call = service.deleteBusinessCoupon("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonModel> call, @NonNull Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        try {
                            assert response.body() != null;
                            if (response.body().getStatus() == 1) {
                                callGetBusinessCouponAPI();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonModel> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void ReplaceFrag(Fragment fragment) {

    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {
        try {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onResume() {
        super.onResume();
        callGetBusinessCouponAPI();
    }

    private void callGetBusinessCouponAPI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("status", "Active");

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
                                GetBusinessCouponLst = (ArrayList<GetBusinessCouponData>) response.body().getData();


                                if (GetBusinessCouponLst.size() == 0) {
                                    no_coupon.setVisibility(View.VISIBLE);
                                    rv_coupons_list.setVisibility(View.GONE);
                                } else {
                                    no_coupon.setVisibility(View.GONE);
                                    rv_coupons_list.setVisibility(View.VISIBLE);
                                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                    rv_coupons_list.setLayoutManager(manager);
                                    adapter = new ActiveCouponsAdapter(GetBusinessCouponLst, FragActiveCoupons.this);
                                    rv_coupons_list.setAdapter(adapter);
                                }


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        clsCommon.showDialogMsgFrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
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



