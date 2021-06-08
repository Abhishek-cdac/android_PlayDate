package com.playdate.app.ui.register.age_verification;

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
import com.playdate.app.databinding.ActivityAgeVerificationBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.login.LoginActivity;
import com.playdate.app.ui.register.gender.GenderSelActivity;
import com.playdate.app.ui.register.otp.OTPActivity;
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

public class AgeVerifiationActivity extends AppCompatActivity {
    private AgeVerificationViewModel age_verify_viewmodel;

    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        age_verify_viewmodel = new AgeVerificationViewModel();
        clsCommon = CommonClass.getInstance();
        mIntent = getIntent();
        com.playdate.app.databinding.ActivityAgeVerificationBinding binding = DataBindingUtil.setContentView(AgeVerifiationActivity.this, R.layout.activity_age_verification);
        binding.setLifecycleOwner(this);
        binding.setAgeVerificationViewModel(age_verify_viewmodel);


        age_verify_viewmodel.onRegisterUser().observe(this, loginUser -> AgeVerifiationActivity.this.startActivity(new Intent(AgeVerifiationActivity.this, OTPActivity.class)));
        age_verify_viewmodel.iv_backClick.observe(this, loginUser -> finish());
        age_verify_viewmodel.DaySelectedPosition().observe(this, val -> {
        });
        age_verify_viewmodel.MonthSelectedPosition().observe(this, val -> age_verify_viewmodel.setDays(val));
        age_verify_viewmodel.YearSelectedPosition().observe(this, val -> age_verify_viewmodel.setYear(val));
        age_verify_viewmodel.onNextClick().observe(this, val -> {

            if (age_verify_viewmodel.getYearSelected().toLowerCase().equals("year")) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select Year of birth", "Ok");
            } else if (age_verify_viewmodel.getMonthSelected()==0) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select month of birth", "Ok");
            } else if (age_verify_viewmodel.getDaySelected().toLowerCase().equals("day")) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select day of birth", "Ok");
            } else {
//                startActivity(new Intent(AgeVerifiationActivity.this, GenderSelActivity.class));
                callAPI();
            }

        });

        if (mIntent.getBooleanExtra("fromProfile", false)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    String CurrentDOB = mIntent.getStringExtra("CurrentDOB");
                    String ar[] = CurrentDOB.split("-");
                    String CurrentYYYY = ar[0];
                    String CurrentMM = ar[1];
                    String CurrentDDD = ar[2];
                    age_verify_viewmodel.setDates(CurrentYYYY, CurrentMM, CurrentDDD);
                }
            }, 200);


        }

    }

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String MM="";
        if(age_verify_viewmodel.getMonthSelected()<10){
            MM="0"+age_verify_viewmodel.getMonthSelected();
        }else{
            MM=""+age_verify_viewmodel.getMonthSelected();
        }

        String DD="";
        if(age_verify_viewmodel.getDaySelected().length()==1){
            DD="0"+age_verify_viewmodel.getDaySelected();
        }else{
            DD=""+age_verify_viewmodel.getDaySelected();
        }

        String DOB = age_verify_viewmodel.getYearSelected() + "-" + MM + "-" + DD;
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("birthDate", DOB);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        //  SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserbirthDate, DOB);
                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            finish();
                        } else {
                            startActivity(new Intent(AgeVerifiationActivity.this, GenderSelActivity.class));
                        }


                    } else {
                        Log.e("PlayDateDOB", "" + response.body().getMessage());
                        clsCommon.showDialogMsg(AgeVerifiationActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e("PlayDateDOB", "" + response.body().getMessage());

                        clsCommon.showDialogMsg(AgeVerifiationActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(AgeVerifiationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(AgeVerifiationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}