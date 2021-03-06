package com.playdate.app.ui.register.interestin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityInterestinBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestActivity extends AppCompatActivity {

    private InterestInViewModel viewModel;
    private ActivityInterestinBinding binding;
    private boolean Male = false;
    private boolean FeMale = false;
    private boolean Other = false;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        viewModel = new InterestInViewModel();
        mIntent = getIntent();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interestin);
        binding.setLifecycleOwner(this);
        binding.setInterestInViewModel(viewModel);

        viewModel.OnNextClick().observe(this, click -> {
            if (!Male && !FeMale && !Other) {
                clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", "Please select at least one interests in", "Ok");
            } else {
                String interest = "";
                if (Male) {
                    interest = "Male";
                }
                if (FeMale) {
                    if (interest.isEmpty()) {
                        interest = "Female";
                    } else {
                        interest = interest + ",Female";
                    }
                }
                if (Other) {
                    if (interest.isEmpty()) {
                        interest = "Other";
                    } else {
                        interest = interest + ",Other";
                    }
                }


                callAPI(interest);
            }


        });

        viewModel.OnMaleClick().observe(this, click -> {
            if (click) {
                Male = true;
                binding.btnMale.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);

            } else {
                Male = false;
                binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.ivNext.setVisibility(View.GONE);

            }

        });


        viewModel.OnFemaleClick().observe(this, click -> {
            if (click) {
                FeMale = true;
                binding.btnFemale.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);

            } else {
                FeMale = false;
                binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.ivNext.setVisibility(View.GONE);

            }

        });
        viewModel.OnNonBinClick().observe(this, click -> {

            if (click) {
                Other = true;
                binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);

            } else {
                Other = false;
                binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.ivNext.setVisibility(View.GONE);

            }
        });

        viewModel.onBackClick().observe(this, click -> finish());


        if (mIntent.getBooleanExtra("fromProfile", false)) {
            new Handler().postDelayed(() -> {

                String[] arr = mIntent.getStringExtra("Selected").split(",");

                for (String s : arr) {
                    if (s.equals("Male")) {
                        viewModel.setMale();
                    }
                    if (s.equals("Female")) {
                        viewModel.setFeMale();
                    }
                    if (s.equals("Other")) {
                        viewModel.setNonBin();
                    }
                }


            }, 200);


        }

    }

    private void callAPI(String interest) {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("interestedIn", interest);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (Objects.requireNonNull(response.body()).getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserinterestedIn, interest);
                        if (mIntent.getBooleanExtra("fromProfile", false)) {

                            finish();
                        } else {
                            startActivity(new Intent(InterestActivity.this, UserNameActivity
                                    .class));
                        }

                    } else {
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
