package com.playdate.app.ui.register.username;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.business.businessbio.BusinessBioActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityUsernameBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.util.session.SessionPref.LoginUserusername;

public class UserNameActivity extends AppCompatActivity {
    private UserNameViewModel userNameViewModel;
    private ActivityUsernameBinding binding;
    private Intent mIntent;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        userNameViewModel = new UserNameViewModel();
        binding = DataBindingUtil.setContentView(UserNameActivity.this, R.layout.activity_username);
        binding.setLifecycleOwner(this);
        binding.setUserNameViewModel(userNameViewModel);
        mIntent = getIntent();
        binding.setUserNameViewModel(userNameViewModel);
        RelativeLayout rl_username_bg = findViewById(R.id.rl_username_bg);
        if (mIntent.getBooleanExtra("fromProfile", false)) {
            SessionPref pref = SessionPref.getInstance(this);
            userNameViewModel.OnUserNameInput().setValue(pref.getStringVal(LoginUserusername));
        }

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        };
        binding.edtFullname.setFilters(new InputFilter[]{filter});


        userNameViewModel.OnNextClick().observe(this, click -> {

            if (userNameViewModel.UserName.getValue() != null) {
                callAPI(userNameViewModel.UserName.getValue());
            }

        });
        userNameViewModel.OnUserNameInput().

                observe(this, charSeq -> {
//                if (charSeq.length() == 5) {
//                    startTimer();
//                } else {
////                    binding.spinKit.setVisibility(View.GONE);
//                }

                });


        userNameViewModel.onBackClick().

                observe(this, click -> finish());

        rl_username_bg.setOnClickListener(v -> clsCommon.hideKeyboard(v, UserNameActivity.this));

    }


    private void callAPI(String uname) {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("username", uname);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.updateUsername("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(LoginUserusername, uname);

                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            Intent mIntent = new Intent();
                            setResult(408, mIntent);
                            finish();
                        } else {
                            if(pref.getBoolVal(SessionPref.isBusiness)){
                                startActivity(new Intent(UserNameActivity.this, BusinessBioActivity.class));
                            }else{
                                startActivity(new Intent(UserNameActivity.this, BioActivity.class));
                            }




                        }


                    } else {
                        clsCommon.showDialogMsg(UserNameActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(UserNameActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(UserNameActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(UserNameActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != handler)
                handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler;


    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (null != handler) {
                handler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void startTimer() {
//        binding.spinKit.setVisibility(View.VISIBLE);
//        new CommonClass().hideKeyboard(binding.spinKit, this);
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.spinKit.setVisibility(View.GONE);
//                binding.ivNext.setVisibility(View.VISIBLE);
//                binding.edtFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
//
//            }
//        }, 1000);
//    }
}
