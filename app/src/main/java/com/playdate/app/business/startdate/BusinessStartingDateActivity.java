package com.playdate.app.business.startdate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.business.businessbio.BusinessBioActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityBusinessStartDateBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.otp.OTPActivity;
import com.playdate.app.ui.register.username.UserNameActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessStartingDateActivity extends AppCompatActivity {
    private BusinessStartingDateViewModel business_Starting_Date_ViewModel;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        business_Starting_Date_ViewModel = new BusinessStartingDateViewModel();
        clsCommon = CommonClass.getInstance();
        mIntent = getIntent();
        ActivityBusinessStartDateBinding binding = DataBindingUtil.setContentView(BusinessStartingDateActivity.this, R.layout.activity_business_start_date);
        binding.setLifecycleOwner(this);
        binding.setBusinessStartingDateViewModel(business_Starting_Date_ViewModel);


        business_Starting_Date_ViewModel.onRegisterUser().observe(this, loginUser -> BusinessStartingDateActivity.this.startActivity(new Intent(BusinessStartingDateActivity.this, OTPActivity.class)));
        business_Starting_Date_ViewModel.iv_backClick.observe(this, loginUser -> {
            finish();
        });
        business_Starting_Date_ViewModel.DaySelectedPosition().observe(this, val -> {
        });
        business_Starting_Date_ViewModel.MonthSelectedPosition().observe(this, val -> business_Starting_Date_ViewModel.setDays(val));
        business_Starting_Date_ViewModel.YearSelectedPosition().observe(this, val -> business_Starting_Date_ViewModel.setYear(val));
        business_Starting_Date_ViewModel.onNextClick().observe(this, val -> {

            if (business_Starting_Date_ViewModel.getYearSelected().toLowerCase().equals("year")) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select Year of business starting date", "Ok");
            } else if (business_Starting_Date_ViewModel.getMonthSelected() == 0) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select month of business starting date", "Ok");
            } else if (business_Starting_Date_ViewModel.getDaySelected().toLowerCase().equals("day")) {
                clsCommon.showDialogMsg(this, "PlayDate", "Please select day of business starting date", "Ok");
            } else {
                   callAPI();
            }

        });


        if (mIntent.getBooleanExtra("fromProfile", false)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    String CurrentDOB = mIntent.getStringExtra("CurrentDOB");
                    String[] ar = CurrentDOB.split("-");
                    String CurrentYYYY = ar[0];
                    String CurrentMM = ar[1];
                    String CurrentDDD = ar[2];
                    business_Starting_Date_ViewModel.setDates(CurrentYYYY, CurrentMM, CurrentDDD);
                }
            }, 200);
        }
    }

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String MM = "";
        if (business_Starting_Date_ViewModel.getMonthSelected() < 10) {

            MM = "0" + business_Starting_Date_ViewModel.getMonthSelected();
        } else {
            MM = "" + business_Starting_Date_ViewModel.getMonthSelected();
        }

        String DD = "";
        if (business_Starting_Date_ViewModel.getDaySelected().length() == 1) {
            DD = "0" + business_Starting_Date_ViewModel.getDaySelected();
        } else {
            DD = "" + business_Starting_Date_ViewModel.getDaySelected();
        }

        String BusinessStartDate = business_Starting_Date_ViewModel.getYearSelected() + "-" + MM + "-" + DD;
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("birthDate", BusinessStartDate);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//        SessionPref pref = SessionPref.getInstance(this);
//        Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();
        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                          pref.saveStringKeyVal(SessionPref.LoginUserbirthDate, BusinessStartDate);
                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            finish();
                        } else {
                            startActivity(new Intent(BusinessStartingDateActivity.this, UserNameActivity.class));
                            finish();
                        }


                    } else {
                        clsCommon.showDialogMsg(BusinessStartingDateActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(BusinessStartingDateActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(BusinessStartingDateActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(BusinessStartingDateActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}