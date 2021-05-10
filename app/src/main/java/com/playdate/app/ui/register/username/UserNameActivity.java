package com.playdate.app.ui.register.username;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityUsernameBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.bio.BioActivity;
import com.playdate.app.ui.register.relationship.RelationActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNameActivity extends AppCompatActivity {
    UserNameViewModel userNameViewModel;
    ActivityUsernameBinding binding;
    Intent mIntent;
    CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        userNameViewModel = new UserNameViewModel();
        binding = DataBindingUtil.setContentView(UserNameActivity.this, R.layout.activity_username);
        binding.setLifecycleOwner(this);
        binding.setUserNameViewModel(userNameViewModel);
        mIntent = getIntent();
        userNameViewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (mIntent.getBooleanExtra("fromProfile", false)) {
                    Intent mIntent = new Intent();
                    setResult(408, mIntent);
                    finish();
                } else {

                    if (userNameViewModel.UserName.getValue() != null) {
                        startActivity(new Intent(UserNameActivity.this, BioActivity.class));
//                        callAPI(userNameViewModel.UserName.getValue());
                    }


                }

            }
        });
        userNameViewModel.OnUserNameInput().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String charSeq) {
//                if (charSeq.length() == 5) {
//                    startTimer();
//                } else {
////                    binding.spinKit.setVisibility(View.GONE);
//                }

            }
        });

//        iv_next

        userNameViewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });


    }

    private void callAPI(String uname) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("username", uname);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bareer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserusername, uname);
                        startActivity(new Intent(UserNameActivity.this, BioActivity.class));
                    } else {
                        clsCommon.showDialogMsg(UserNameActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(UserNameActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
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

    private void startTimer() {
        binding.spinKit.setVisibility(View.VISIBLE);
        new CommonClass().hideKeyboard(binding.spinKit, this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.spinKit.setVisibility(View.GONE);
                binding.ivNext.setVisibility(View.VISIBLE);
                binding.edtFullname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);

            }
        }, 1000);
    }
}
