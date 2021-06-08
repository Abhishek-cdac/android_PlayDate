package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.CommonModel;
import com.playdate.app.model.GetCoupleProfileData;
import com.playdate.app.model.GetCoupleProfileModel;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.data.api.RetrofitClientInstance.BASE_URL_IMAGE;

public class FragPartnerSelected extends Fragment {
    public FragPartnerSelected() {
    }

    ImageView iv_partner_image;
    ImageView iv_accepted;
    ImageView iv_loading;
    ImageView iv_back;
    TextView tv_waiting;
    TextView tv_points;
    TextView tv_username;
    String image_url, profile_userId;
    String image_name;
    String image_points;
    boolean accepted = false;
    private CommonClass clsCommon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_selected_partner, container, false);
        clsCommon = CommonClass.getInstance();

        iv_back = view.findViewById(R.id.iv_back);
        iv_partner_image = view.findViewById(R.id.partner_image);
        iv_accepted = view.findViewById(R.id.iv_accepted);
        iv_loading = view.findViewById(R.id.iv_loading);
        tv_waiting = view.findViewById(R.id.tv_waiting);
        tv_points = view.findViewById(R.id.tv_points);
        tv_username = view.findViewById(R.id.tv_username);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Bundle bundle = getArguments();
        image_url = bundle.getString("profile_image", "");
        image_name = bundle.getString("profile_name", "");
        image_points = bundle.getString("profile_points", "");
        profile_userId = bundle.getString("profile_userId", "");
        Log.e("profile_toUserID", "" + profile_userId);

        callCreateDateRequestPartnerApI();

        tv_username.setText(image_name);
        tv_points.setText(image_points);
        Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);
        tv_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accepted) {
                    iv_loading.setVisibility(View.GONE);
                    tv_waiting.setText(R.string.accepted_patner);
                    iv_accepted.setVisibility(View.VISIBLE);
                    accepted = true;
                } else {
//                    startActivity(new Intent(getActivity(), SelectDateActivity.class));
                    OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
                    frag.ReplaceFrag(new FragSelectDate());
                }


            }
        });

        return view;

    }

    private void callCreateDateRequestPartnerApI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("toUserId", profile_userId);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
        pd.show();
        Call<CommonModel> call = service.createDateRequestPartner("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_date_selected_partner);
//        iv_partner_image = findViewById(R.id.partner_image);
//        iv_accepted = findViewById(R.id.iv_accepted);
//        iv_loading = findViewById(R.id.iv_loading);
//        tv_waiting = findViewById(R.id.tv_waiting);
//        tv_points = findViewById(R.id.tv_points);
//        tv_username = findViewById(R.id.tv_username);
//
//        Intent intent = getIntent();
//        image_url = intent.getStringExtra("profile_image");
//        image_name = intent.getStringExtra("profile_name");
//        image_points = intent.getStringExtra("profile_points");
//
//        tv_username.setText(image_name);
//        tv_points.setText(image_points);
//        Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);
//
////        if (accepted) {
////            iv_loading.setVisibility(View.GONE);
////            tv_waiting.setText(R.string.accepted_patner);
////            iv_accepted.setVisibility(View.VISIBLE);
////        } else {
////            iv_loading.setVisibility(View.VISIBLE);
////            tv_waiting.setText(R.string.waiting_partner);
////            iv_accepted.setVisibility(View.GONE);
////        }
//
//        tv_waiting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!accepted) {
//                    iv_loading.setVisibility(View.GONE);
//                    tv_waiting.setText(R.string.accepted_patner);
//                    iv_accepted.setVisibility(View.VISIBLE);
//                    accepted = true;
//                } else {
//                    startActivity(new Intent(PartnerSelected.this, SelectDateActivity.class));
//                }
//
//
//            }
//        });
//
//    }

}
