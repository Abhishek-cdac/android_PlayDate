package com.playdate.app.ui.coupons;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.Account;
import com.playdate.app.model.GetCouponsData;
import com.playdate.app.model.MyCouponsModelStore;
import com.playdate.app.model.MyCouponsWrapStore;
import com.playdate.app.ui.chat.request.Onclick;
import com.playdate.app.ui.coupons.adapters.CouponStoreAdapter;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogSelectedRestaurant extends Dialog {
    private String rest_name;
    private String rest_image;
    TextView tv_rest_name;
    ImageView iv_rest_image;
    ImageView iv_close;
    RecyclerView rv_coupons_list;
    private ArrayList<GetCouponsData> lst_getCoupons;
    private ArrayList<GetCouponsData> lst_filteredRest = new ArrayList<>();
    Onclick itemClick;
    private CommonClass clsCommon;
    private Account account;
    private TextView tv_unavailavble;


    public DialogSelectedRestaurant(@NonNull Context context, String rest_name, String rest_image) {
        super(context, R.style.My_Dialog);
        this.rest_name = rest_name;
        this.rest_image = rest_image;

        WindowManager.LayoutParams wlmp = getWindow().getAttributes();

        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_rest_selected, null);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(view);
        callGetCouponsApi();
        clsCommon = CommonClass.getInstance();
        tv_rest_name = view.findViewById(R.id.rest_name);
        iv_rest_image = view.findViewById(R.id.rest_image);
        iv_close = view.findViewById(R.id.iv_close);
        tv_unavailavble = view.findViewById(R.id.tv_unavailavble);
        rv_coupons_list = view.findViewById(R.id.rv_coupons_list);
        rv_coupons_list.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        tv_rest_name.setText(rest_name);
        Picasso.get().load(rest_image).fit().centerCrop().placeholder(R.drawable.cupertino_activity_indicator).into(iv_rest_image);

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
                        Intent intent = new Intent(context, ActivityCoupons.class);
                        intent.putExtra("Coupon_id", Coupon_id);
                        intent.putExtra("Coupon_code", Coupon_code);
                        intent.putExtra("Coupon_points", Coupon_points);
                        intent.putExtra("isFromCoupon", true);
                        intent.putExtra("No Balance", false);

                        if (null != account)
                            intent.putExtra("CurrentPoints", account.getCurrentPoints());


                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == 12) {
                    Intent intent = new Intent(context, ActivityCoupons.class);
                    intent.putExtra("Coupon_points", Coupon_points);
                    intent.putExtra("isFromCoupon", true);
                    intent.putExtra("No Balance", true);

                    if (null != account)
                        intent.putExtra("CurrentPoints", account.getCurrentPoints());

                    context.startActivity(intent);
                }
            }

            @Override
            public void onItemClicks(View v, int position, int i, String username, String totalPoints, String id, String profilePicPath) {

            }
        };

        iv_close.setOnClickListener(v -> dismiss());
    }

    private void callGetCouponsApi() {
        SessionPref pref = SessionPref.getInstance(getContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("limit", "100");
        hashMap.put("pageNo", "1");


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getContext());
        pd.show();
        Call<MyCouponsModelStore> call = service.getCoupons("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<MyCouponsModelStore>() {
            @Override
            public void onResponse(Call<MyCouponsModelStore> call, Response<MyCouponsModelStore> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        try {
                            MyCouponsWrapStore wrap = response.body().getData();
                            lst_getCoupons = wrap.getGetAllCoupons();
                            if (lst_getCoupons == null) {
                                lst_getCoupons = new ArrayList<>();
                            }

                            ArrayList<GetCouponsData> lst = new ArrayList<>();

                            try {
                                for (GetCouponsData data : lst_getCoupons) {
//                                    GetCouponsData data = lst_getCoupons.get(i);
                                    if (data.getRestaurants().get(0).getName().equals(rest_name)) {

                                        lst.add(data);
//                                        Log.d("Filtered Lst", "onResponse: " + rest_name + " --- " + lst.get(1).getRestaurants().get(0).getName());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            lst_filteredRest = lst;

                            Log.d("Filtered Lst", "onResponseAfter: " + rest_name + " --- " + lst_filteredRest.size());

                            if (lst_filteredRest.isEmpty()) {
//                                    lst_filteredRest = new ArrayList<>();

                                tv_unavailavble.setVisibility(View.VISIBLE);
                            }
                            CouponStoreAdapter adapter = new CouponStoreAdapter(lst_filteredRest, itemClick, true);
                            rv_coupons_list.setAdapter(adapter);
                            adapter.setListerner(DialogSelectedRestaurant.this);
                            account = wrap.getAccount();
//                            txt_points.setText("" + wrap.getAccount().getCurrentPoints());
                            adapter.setCurrentPoints(wrap.getAccount().getCurrentPoints());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        clsCommon.showDialogMsgfrag(getContext(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), "" + jObjError.getString("message"), Toast.LENGTH_SHORT).show();
//                        clsCommon.showDialogMsgfrag(getContext(), "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<MyCouponsModelStore> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMsg() {
        Toast.makeText(getContext(), "This coupon is already purchased!", Toast.LENGTH_SHORT).show();
//        clsCommon.showDialogMsgfrag(getContext(), "PlayDate", "This coupon is already purchased!", "Ok");
    }
}
