package com.playdate.app.ui.date.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.DateRequestData;
import com.playdate.app.model.DatingRequest;
import com.playdate.app.model.DatingRequestStatus;
import com.playdate.app.ui.date.OnBackPressed;
import com.playdate.app.ui.interfaces.OnInnerFragmentClicks;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragPartnerSelected extends Fragment {
    public FragPartnerSelected() {
    }

    private ImageView iv_accepted;
    private TextView tv_waiting;
    private String profile_userId;
    boolean accepted = false;
    private CommonClass clsCommon;
    private SpinKitView spin_kit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_selected_partner, container, false);
        clsCommon = CommonClass.getInstance();
        spin_kit = view.findViewById(R.id.spin_kit);
        ImageView iv_back = view.findViewById(R.id.iv_back);
        ImageView cancel = view.findViewById(R.id.cancel);
        ImageView iv_partner_image = view.findViewById(R.id.partner_image);
        iv_accepted = view.findViewById(R.id.iv_accepted);
        tv_waiting = view.findViewById(R.id.tv_waiting);
        TextView tv_points = view.findViewById(R.id.tv_points);
        TextView tv_username = view.findViewById(R.id.tv_username);
        iv_back.setOnClickListener(v -> {
            goBack();
        });
        cancel.setOnClickListener(v -> {
            goBack();
        });

        Bundle bundle = getArguments();
        String image_url = Objects.requireNonNull(bundle).getString("profile_image", "");
        String image_name = bundle.getString("profile_name", "");
        String image_points = bundle.getString("profile_points", "");
        profile_userId = bundle.getString("profile_userId", "");
        Log.e("profile_toUserID", "" + profile_userId);

        callCreateDateRequestPartnerApI();

        tv_username.setText(image_name);
        tv_points.setText(image_points);
        if (null != image_url) {
            if (!image_url.isEmpty())
                Picasso.get().load(image_url).placeholder(R.drawable.cupertino_activity_indicator).into(iv_partner_image);
        }

//        tv_waiting.setOnClickListener(v -> {
//            if (!accepted) {
//                iv_loading.setVisibility(View.GONE);
//                tv_waiting.setText(R.string.accepted_patner);
//                iv_accepted.setVisibility(View.VISIBLE);
//                accepted = true;
//            } else {
//                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
//                Objects.requireNonNull(frag).ReplaceFrag(new FragSelectDate());
//            }
//        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                callCancelDateRequest();
                return true;
            }
            return false;
        });
        return view;

    }


    void goBack() {
        try {
            callCancelDateRequest();
            OnBackPressed ref = (OnBackPressed) getActivity();
            ref.onBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String DateRequestID = "";

    private void callCreateDateRequestPartnerApI() {
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("toUserId", profile_userId);
        Call<DatingRequest> call = service.createDateRequestPartner("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<DatingRequest>() {
            @Override
            public void onResponse(Call<DatingRequest> call, Response<DatingRequest> response) {
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        DateRequestData data = response.body().getData();
                        DateRequestID = data.getRequestId();
                        checkForRequestStatus();
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
            public void onFailure(Call<DatingRequest> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    Handler mHandler;
    int count = 0;

    private void checkForRequestStatus() {

        if (accepted) {
            return;
        } else if (count == 5) {
            clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "Partner not responding", "Ok");
            goBack();
            return;
        }
        //temp code

//        if (count == 3) {
//            accepted = true;
//            tv_waiting.setText(R.string.accepted_patner);
//            iv_accepted.setVisibility(View.VISIBLE);
//            spin_kit.setVisibility(View.GONE);
//
//            mHandler.postDelayed(() -> {
//                OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
//                Objects.requireNonNull(frag).ReplaceFrag(new FragSelectDate());
//            }, 1500);
//
//            return;
//        }

        //temp code


        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.postDelayed(() -> {
            callAPI();
            count = count + 1;
            checkForRequestStatus();
        }, 5000);
    }

    private void NextPage() {
        accepted = true;
        tv_waiting.setText(R.string.accepted_patner);
        iv_accepted.setVisibility(View.VISIBLE);
        spin_kit.setVisibility(View.GONE);

        mHandler.postDelayed(() -> {
            OnInnerFragmentClicks frag = (OnInnerFragmentClicks) getActivity();
            Objects.requireNonNull(frag).ReplaceFrag(new FragSelectDate());
        }, 1500);

    }

    private void callAPI() {

        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("requestId", DateRequestID);
        Call<DatingRequestStatus> call = service.getMyCreateDateRequestStatus("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<DatingRequestStatus>() {
            @Override
            public void onResponse(Call<DatingRequestStatus> call, Response<DatingRequestStatus> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        List<DateRequestData> data = response.body().getData();
                        if (data.get(0).getStatus().toLowerCase().equals("accepted")) {
                            NextPage();
                        } else if (data.get(0).getStatus().toLowerCase().equals("rejected")) {
                            clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", "Partner not responding", "Ok");
                            goBack();
                        }

                    } else {
//                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {


                }


            }

            @Override
            public void onFailure(Call<DatingRequestStatus> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void callCancelDateRequest() {
        if (DateRequestID == null) {
            return;
        } else if (DateRequestID.isEmpty()) {
            return;
        }
        SessionPref pref = SessionPref.getInstance(getActivity());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("requestId", DateRequestID);
//        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(getActivity());
//        pd.show();
        Call<DatingRequest> call = service.deleteDateRequest("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<DatingRequest>() {
            @Override
            public void onResponse(Call<DatingRequest> call, Response<DatingRequest> response) {
//                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {

                    } else {
                        clsCommon.showDialogMsgfrag(getActivity(), "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {


                }


            }

            @Override
            public void onFailure(Call<DatingRequest> call, Throwable t) {
                t.printStackTrace();
//                pd.cancel();
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
