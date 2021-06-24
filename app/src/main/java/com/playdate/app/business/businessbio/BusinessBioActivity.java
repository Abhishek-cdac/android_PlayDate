package com.playdate.app.business.businessbio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityBusinessBioBinding;

import com.playdate.app.model.LoginResponse;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class BusinessBioActivity extends AppCompatActivity {

    private BusinessBioViewModel businessBioViewModel;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessBioViewModel = new BusinessBioViewModel();
        clsCommon = CommonClass.getInstance();
        ActivityBusinessBioBinding bioBinding = DataBindingUtil.setContentView(BusinessBioActivity.this, R.layout.activity_business_bio);
        bioBinding.setLifecycleOwner(this);
        mIntent = getIntent();
        bioBinding.setBusinessBioViewModel(businessBioViewModel);
        if (mIntent.getBooleanExtra("fromProfile", false)) {
            SessionPref pref = SessionPref.getInstance(this);
           // businessBioViewModel.BioText.setValue(pref.getStringVal(LoginUserpersonalBio));
        } else {


        }
        businessBioViewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (null == businessBioViewModel.BioText.getValue()) {
                    clsCommon.showDialogMsg(BusinessBioActivity.this, "PlayDate", "Enter your business bio.", "Ok");
                } else if (businessBioViewModel.BioText.getValue().toString().trim().equals("")) {
                    clsCommon.showDialogMsg(BusinessBioActivity.this, "PlayDate", "Enter your business bio.", "Ok");
                } else {

                    if (mIntent.getBooleanExtra("fromProfile", false)) {
                        Intent mIntent = new Intent();
                        setResult(410, mIntent);
                        finish();
                    } else {
                     //   startActivity(new Intent(BusinessBioActivity.this, CoupleUploadProfileActivity.class));

                    }
                  //  callAPI();
                }


            }
        });

        businessBioViewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });
    }

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String bio = businessBioViewModel.BioText.getValue();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("businessbio", bio);// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
//      Toast.makeText(this, ""+pref.getStringVal(SessionPref.LoginUsertoken), Toast.LENGTH_SHORT).show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                   //     pref.saveStringKeyVal(LoginUserbusinessBio, bio);

                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            Intent mIntent = new Intent();
                            setResult(410, mIntent);
                            finish();
                        } else {

                          //  startActivity(new Intent(BusinessBioActivity.this, CoupleUploadProfileActivity.class));

                        }



                    } else {
                        clsCommon.showDialogMsg(BusinessBioActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(BusinessBioActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(BusinessBioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(BusinessBioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
