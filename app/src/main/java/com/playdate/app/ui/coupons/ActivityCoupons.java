package com.playdate.app.ui.coupons;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.coupleprofile.CoupleUploadProfileActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.FaqData;
import com.playdate.app.model.FaqModel;
import com.playdate.app.model.GetCouponsModel;
import com.playdate.app.ui.coupons.adapters.FrequentlyQuestionAdapter;
import com.playdate.app.ui.restaurant.adapter.Restaurant;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCoupons extends AppCompatActivity implements View.OnClickListener {
    private String CouponCode;
    private int CurrentPoints;
    private RelativeLayout rl_getcode;
    private RelativeLayout rl_earn_point;
    private RelativeLayout rl_code;
    private String inviteLink;
    private TextView share_coupans;
    private TextView share_coupans1;
    ArrayList<FaqData> faq_list;
    ImageView surprise;
    TextView tv_Get_code;
    RecyclerView rv_frequently;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_coupan_code);
        String inviteCode = getIntent().getStringExtra("inviteCode");
        inviteLink = getIntent().getStringExtra("inviteLink");
//        Log.e("inviteCode...", "" + inviteCode);
//        Log.e("inviteLink...", "" + inviteLink);

        RelativeLayout copy_code = findViewById(R.id.copy_code);

        ImageView whatsup_coupan = findViewById(R.id.whatsup_coupan);
        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView message_coupan = findViewById(R.id.message_coupan);
        ImageView instagram_coupan = findViewById(R.id.instagram_coupan);
        ImageView facebook_coupan = findViewById(R.id.facebook_coupan);

        TextView tv_code = findViewById(R.id.tv_code);
        share_coupans = findViewById(R.id.share_coupans);
        share_coupans1 = findViewById(R.id.share_coupans1);
        surprise = findViewById(R.id.surprise);
        tv_Get_code = findViewById(R.id.tv_Get_code);
        rl_earn_point = findViewById(R.id.rl_earn_point);

        TextView txt_points = findViewById(R.id.txt_points);
        TextView txt_refer = findViewById(R.id.txt_refer);
        TextView every_time = findViewById(R.id.every_time);
        rl_getcode = findViewById(R.id.rl_getcode);
        rl_code = findViewById(R.id.rl_code);
        callFaqApi();
        /*value fetch from FragCouponStore*/
        Intent mIntent = getIntent();
        String couponId = mIntent.getStringExtra("Coupon_id");
        CouponCode = mIntent.getStringExtra("Coupon_code");
        String couponPoints = mIntent.getStringExtra("Coupon_points");
        int CurrentPoints = mIntent.getIntExtra("CurrentPoints", 0);
        boolean isFromCoupon = mIntent.getBooleanExtra("isFromCoupon", false);
        boolean isnoBalance = mIntent.getBooleanExtra("No Balance", false);
        txt_points.setText(couponPoints + " Points");
        if (isFromCoupon) {
            if (isnoBalance) {
                txt_refer.setText(R.string.something_went_wrong);
                every_time.setText(R.string.dont_have_enough_points);
                surprise.setImageResource(R.drawable.sad);
                rl_earn_point.setVisibility(View.VISIBLE);
                rl_getcode.setVisibility(View.GONE);
            } else {
                txt_refer.setText(R.string.play_and_earn);
                every_time.setText(R.string.every_points);
                surprise.setImageResource(R.drawable.surprise);
                rl_earn_point.setVisibility(View.GONE);
                rl_getcode.setVisibility(View.VISIBLE);


            }
        } else {
            txt_refer.setText(R.string.refer_your_friend);
            every_time.setText(R.string.every_time);
        }
        tv_code.setText(CouponCode);
        rl_getcode.setOnClickListener(v -> {
            if (isFromCoupon) {
                if (CurrentPoints > Integer.parseInt(couponPoints)) {
                    callAPIRedeemCouPon(couponId);
                } else {
                    new CommonClass().showDialogMsg(ActivityCoupons.this, "PlayDate", "Insufficient wallet points!", "Ok");
                }

            } else {
                share_coupans.setVisibility(View.INVISIBLE);
                share_coupans1.setVisibility(View.VISIBLE);
                rl_getcode.setVisibility(View.INVISIBLE);
                rl_code.setVisibility(View.VISIBLE);
            }


        });
        iv_back.setOnClickListener(v -> finish());
//        facebook_coupan.setOnClickListener(this);
//        instagram_coupan.setOnClickListener(this);
//        whatsup_coupan.setOnClickListener(this);
//        message_coupan.setOnClickListener(this);
        copy_code.setOnClickListener(this);


        rv_frequently = findViewById(R.id.rv_frequently);

    }

    private void callFaqApi() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("requestId", "");


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<FaqModel> call = service.getFaq("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<FaqModel>() {
            @Override
            public void onResponse(Call<FaqModel> call, Response<FaqModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        faq_list = (ArrayList<FaqData>) response.body().getData();
                        if (faq_list == null) {
                            faq_list = new ArrayList<>();
                        }
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        rv_frequently.setLayoutManager(manager);
                        FrequentlyQuestionAdapter adapter = new FrequentlyQuestionAdapter(faq_list, ActivityCoupons.this);
                        rv_frequently.setAdapter(adapter);


                    } else {
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        new CommonClass().showDialogMsg(ActivityCoupons.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<FaqModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ActivityCoupons.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void callAPIRedeemCouPon() {


//    }

    private void callAPIRedeemCouPon(String copy_code) {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("couponId", copy_code);


        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<GetCouponsModel> call = service.purchaseCoupons("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<GetCouponsModel>() {
            @Override
            public void onResponse(Call<GetCouponsModel> call, Response<GetCouponsModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        share_coupans.setVisibility(View.INVISIBLE);
                        share_coupans1.setVisibility(View.VISIBLE);
                        rl_getcode.setVisibility(View.INVISIBLE);
                        rl_code.setVisibility(View.VISIBLE);
                    } else {
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        new CommonClass().showDialogMsg(ActivityCoupons.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetCouponsModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ActivityCoupons.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.facebook_coupan) {
            // SharingToSocialMedia("com.facebook.katana");
            Intent fbIntent = new Intent(Intent.ACTION_SEND);
            fbIntent.setType("text/plain");
            fbIntent.setPackage("com.facebook.katana");
            fbIntent.putExtra(Intent.EXTRA_STREAM, inviteLink);
            try {
                startActivity(fbIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

            }
        }
        if (id == R.id.message_coupan) {

            shareTextUrl();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//          //  intent.setType(Telephony.Sms.getDefaultSmsPackage(this));
//            intent.setType("vnd.android-dir/mms-sms");
//            intent.putExtra(Intent.EXTRA_TEXT, inviteLink);
//            startActivity(intent);


        } else if (id == R.id.instagram_coupan) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.instagram.android");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Instagram have not been installed.", Toast.LENGTH_SHORT).show();

            }

        } else if (id == R.id.whtasapp_coupan) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();

            }

        } else if (id == R.id.copy_code) {

            copyToClipBoard();

        }

    }

    private void copyToClipBoard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("CouponCode", CouponCode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Coupon Code Copied", Toast.LENGTH_SHORT).show();

    }

    // Method to share either text or URL.
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
    }
}
