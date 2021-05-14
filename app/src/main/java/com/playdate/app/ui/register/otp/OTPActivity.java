package com.playdate.app.ui.register.otp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityOtpBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.util.session.SessionPref.LoginVerified;

public class OTPActivity extends AppCompatActivity {

    String phone;
    CommonClass clsCommon;
    private OTPViewModel otpViewModel;
    private ActivityOtpBinding binding;
    Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        otpViewModel = new OTPViewModel();
        binding = DataBindingUtil.setContentView(OTPActivity.this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setOTPViewModel(otpViewModel);
        mIntent = getIntent();
        phone = mIntent.getStringExtra("Phone");
        otpViewModel.setMobile(phone);
        otpViewModel.startTimer();

        otpViewModel.onRegisterUser().observe(this, loginUser -> {


            if (null == otpViewModel.txtOTP.getValue()) {
                clsCommon.showDialogMsg(OTPActivity.this, "PlayDate", "Enter OTP!", "Ok");
            } else if (otpViewModel.txtOTP.getValue().isEmpty()) {
                clsCommon.showDialogMsg(OTPActivity.this, "PlayDate", "Enter OTP!", "Ok");
            } else if (otpViewModel.txtOTP.getValue().length() < 4) {
                clsCommon.showDialogMsg(OTPActivity.this, "PlayDate", "OTP must be of 4 characters in length", "Ok");
            } else {
                if (mIntent.getBooleanExtra("Forgot", false)) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra("verified", true);
                    mIntent.putExtra("OTP", otpViewModel.txtOTP.getValue());
                    setResult(101, mIntent);
                    finish();

                } else {
                    callAPI();
                }

            }


        });
        otpViewModel.resendClick.observe(this, loginUser -> {
//            Toast.makeText(OTPActivity.this, "Resent", Toast.LENGTH_SHORT).show();
            binding.txtResend.setVisibility(View.INVISIBLE);
            binding.txtTimer.setVisibility(View.VISIBLE);
            otpViewModel.startTimer();
            stopAnimAfter3Sec();
            callAPIResentOTP();
        });
        otpViewModel.CountDownFinish.observe(this, loginUser -> {
            binding.txtResend.setVisibility(View.VISIBLE);
            binding.txtTimer.setVisibility(View.GONE);
        });
        otpViewModel.iv_backClick.observe(this, loginUser -> {
            finish();
        });
        otpViewModel.txtOTP.observe(this, loginUser -> {
            if (loginUser.length() > 3) {
                new CommonClass().hideKeyboard(binding.edtOtp, OTPActivity.this);
                binding.edtOtp.clearFocus();
            }

        });
        if (mIntent.getBooleanExtra("resendOTP", false)) {
            callAPIResentOTP();
        }
        stopAnimAfter3Sec();

    }

    private void stopAnimAfter3Sec() {
        binding.spinKit.setVisibility(View.VISIBLE);
        binding.ivDone.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                binding.spinKit.setVisibility(View.INVISIBLE);
                binding.ivDone.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    private void callAPI() {
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("phoneNo", phone);
        hashMap.put("otp", otpViewModel.txtOTP.getValue());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.verifyOtp(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        Log.d("OTP..", response.body().toString());
                        nextPage();
                        SessionPref pref=SessionPref.getInstance(OTPActivity.this);
                        pref.saveBoolKeyVal(LoginVerified, true);
                        finish();
                    } else {
                        clsCommon.showDialogMsg(OTPActivity.this, "PlayDate", "Something went wrong...Please try later!", "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        clsCommon.showDialogMsg(OTPActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(OTPActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(OTPActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAPIResentOTP() {
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("phoneNo", phone);
//        hashMap.put("otp", otpViewModel.txtOTP.getValue());
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();

        SessionPref pref = SessionPref.getInstance(this);
        Call<LoginResponse> call;
//        if (mIntent.getBooleanExtra("resetPassword", false)) {
//            call = service.forgotPasswordSentOtp(hashMap);
//        } else {
        call = service.resendVerifyOtp("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
//        }


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(OTPActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextPage() {
//        if (mIntent.getBooleanExtra("resetPassword", false)) {
//
//        } else {
        OTPActivity.this.startActivity(new Intent(OTPActivity.this, AgeVerifiationActivity.class));
//        }

    }
}
