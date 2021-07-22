package com.playdate.app.business.couponsGenerate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.playdate.app.model.GetBusinessCouponData;
import com.playdate.app.model.GetBusinessCouponModel;
import com.playdate.app.model.MyCoupons;
import com.playdate.app.model.MyCouponsModelStore;
import com.playdate.app.model.MyCouponsWrap;
import com.playdate.app.model.MyCouponsWrapStore;
import com.playdate.app.ui.coupons.FragCouponStore;
import com.playdate.app.ui.coupons.adapters.CouponStoreAdapter;
import com.playdate.app.ui.coupons.adapters.MyCouponAdapter;
import com.playdate.app.ui.dashboard.adapter.RestaurentAdapter;
import com.playdate.app.ui.dashboard.fragments.FragmentSearchRestaurent;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragActiveCoupons extends Fragment implements OnInnerFragmentClicks {
    private RecyclerView rv_coupons_list;
    ActiveCouponsAdapter adapter;
    private Fragment CurrentFrag;
    private CommonClass clsCommon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_active_coupons, container, false);
        clsCommon = CommonClass.getInstance();
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        enableSwipeToDeleteAndUndo();
        enableSwipeToUpdateAndUndo();

        callGetBusinessCouponAPI();

      /*  RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rv_coupons_list.setLayoutManager(manager);

        adapter = new ActiveCouponsAdapter();
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

                                RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                rv_coupons_list.setLayoutManager(manager);
                                adapter = new ActiveCouponsAdapter(GetBusinessCouponLst,  FragActiveCoupons.this);
                                rv_coupons_list.setAdapter(adapter);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

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


    private void enableSwipeToUpdateAndUndo() {
        SwipeToUpdateCallback swipeToUpdateCallback = new SwipeToUpdateCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                ReplaceFragWithStack(new FragCouponGenerator());
//                adapter.removeItem(position);
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

                adapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(rv_coupons_list);
    }

    @Override
    public void ReplaceFrag(Fragment fragment) {

    }

    @Override
    public void ReplaceFragWithStack(Fragment fragment) {
        try {
            CurrentFrag = fragment;
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragmentManager.getFragments().size() > 0) {
                ft.replace(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            } else {
                ft.add(R.id.flFragment, fragment, fragment.getClass().getSimpleName());
            }
            ft.addToBackStack("tags");
            ft.commitAllowingStateLoss();

            //  mSwipeRefreshLayout.setEnabled(CurrentFrag.getClass().getSimpleName().equals("FragSocialFeed"));

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
}



