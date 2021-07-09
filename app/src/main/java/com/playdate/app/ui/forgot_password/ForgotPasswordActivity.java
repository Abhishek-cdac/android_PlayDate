package com.playdate.app.ui.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityForgotPasswordBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.otp.OTPActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ForgotViewModel viewModel;
    private ActivityForgotPasswordBinding binding;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ForgotViewModel();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(ForgotPasswordActivity.this, R.layout.activity_forgot_password);
        binding.setLifecycleOwner(this);
        binding.setForgotViewModel(viewModel);


        mIntent = getIntent();
        if (mIntent.getBooleanExtra("fromProfile", false)) {
            showChangePasswordScreen();
        }

        viewModel.getResetPass().observe(this, phone -> {

            if (phone == null) {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Enter mobile number", "Ok");

            } else if (phone.trim().isEmpty()) {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Enter mobile number", "Ok");

            } else {
                callAPI(phone);
            }


        });
        viewModel.onChangeFinalClick().observe(this, val -> {

            if (viewModel.old_password.getValue() == null) {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Enter old password", "Ok");

            } else if (viewModel.newPass.getValue() == null) {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Enter new password", "Ok");

            } else if (viewModel.newPassConfirm.getValue() == null) {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Confirm new password", "Ok");

            } else if (viewModel.newPassConfirm.getValue().equals(viewModel.newPass.getValue())) {
                if (mIntent.getBooleanExtra("fromProfile", false)) {
                    callDirectResetAPI(viewModel.newPass.getValue(), viewModel.old_password.getValue());
                } else {
                    callAPIReset(viewModel.newPass.getValue());
                }

            } else {
                clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Confirm password and new password does not match", "Ok");
            }


        });

    }

    private void callDirectResetAPI(String newPass, String oldPass) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        SessionPref pref = SessionPref.getInstance(this);
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("oldPassword", oldPass);
        hashMap.put("newPassword", newPass);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.changePassword("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Password changed success!", "Ok");
                        finish();
                    } else {
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", jObjError.getJSONArray("data").getJSONObject(0).getString("msg"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callAPIReset(String value) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("phoneNo", viewModel.mobile.getValue());
        hashMap.put("otp", OTP);
        hashMap.put("password", value);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.resetPassword(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", "Password changed success!", "Ok");
                        finish();
                    } else {
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", jObjError.getJSONArray("data").getJSONObject(0).getString("msg"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callAPI(String phone) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("phoneNo", phone);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<LoginResponse> call = service.forgotPasswordSentOtp(hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {

                        Intent mIntent = new Intent(new Intent(ForgotPasswordActivity.this, OTPActivity
                                .class));
                        mIntent.putExtra("resendOTP", false);
                        mIntent.putExtra("Forgot", true);
                        mIntent.putExtra("resetPassword", true);
                        mIntent.putExtra("Phone", phone);
                        startActivityForResult(mIntent, 100);


                    } else {
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(ForgotPasswordActivity.this, "PlayDate", jObjError.getJSONArray("data").getJSONObject(0).getString("msg"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String OTP = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                if (data.getBooleanExtra("verified", false)) {
                    OTP = data.getStringExtra("OTP");
                    showResetPasswordScreen();
                }
            }
        }
    }

    private void showResetPasswordScreen() {
        binding.newPassword.setVisibility(View.VISIBLE);
        binding.newPasswordConfirm.setVisibility(View.VISIBLE);
        binding.forgotEmail.setVisibility(View.GONE);
        binding.txtHeader.setText("Enter new password");
        binding.sendMail.setVisibility(View.GONE);
        binding.changePassword.setVisibility(View.VISIBLE);
    }

    private void showChangePasswordScreen() {
        binding.newPassword.setVisibility(View.VISIBLE);
        binding.newPasswordConfirm.setVisibility(View.VISIBLE);
        binding.oldPassword.setVisibility(View.VISIBLE);
        binding.forgotEmail.setVisibility(View.GONE);
        binding.txtHeader.setText("Enter new password");
        binding.sendMail.setVisibility(View.GONE);
        binding.changePassword.setVisibility(View.VISIBLE);
        binding.changePassword.setText("Change Password");
    }
}