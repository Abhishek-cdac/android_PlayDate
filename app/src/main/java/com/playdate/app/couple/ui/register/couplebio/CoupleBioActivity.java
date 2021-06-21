package com.playdate.app.couple.ui.register.couplebio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.coupleprofile.CoupleUploadProfileActivity;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityBioBinding;
import com.playdate.app.databinding.ActivityCoupleBioBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.playdate.app.util.session.SessionPref.LoginUserpersonalBio;


public class CoupleBioActivity extends AppCompatActivity {

    private CoupleBioViewModel coupleBioViewModel;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coupleBioViewModel = new CoupleBioViewModel();
        clsCommon = CommonClass.getInstance();
        com.playdate.app.databinding.ActivityCoupleBioBinding bioBinding = DataBindingUtil.setContentView(CoupleBioActivity.this, R.layout.activity_couple_bio);
        bioBinding.setLifecycleOwner(this);
        mIntent = getIntent();
        bioBinding.setCoupleBioViewModel(coupleBioViewModel);
        if (mIntent.getBooleanExtra("fromProfile", false)) {
            SessionPref pref = SessionPref.getInstance(this);
           // coupleBioViewModel.BioText.setValue(pref.getStringVal(LoginUserpersonalBio));
        } else {


        }
        coupleBioViewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (null == coupleBioViewModel.BioText.getValue()) {
                    clsCommon.showDialogMsg(CoupleBioActivity.this, "PlayDate", "Enter your couple bio.", "Ok");
                } else if (coupleBioViewModel.BioText.getValue().toString().trim().equals("")) {
                    clsCommon.showDialogMsg(CoupleBioActivity.this, "PlayDate", "Enter your couple bio.", "Ok");
                } else {

                    if (mIntent.getBooleanExtra("fromProfile", false)) {
                        Intent mIntent = new Intent();
                        setResult(410, mIntent);
                        finish();
                    } else {
                        startActivity(new Intent(CoupleBioActivity.this, CoupleUploadProfileActivity.class));

                    }
                  //  callAPI();
                }


            }
        });

        coupleBioViewModel.onBackClick().observe(this, new Observer<Boolean>() {
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
        String bio = coupleBioViewModel.BioText.getValue();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("coupleBio", bio);// format 1990-08-12
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
                        pref.saveStringKeyVal(LoginUserpersonalBio, bio);

                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            Intent mIntent = new Intent();
                            setResult(410, mIntent);
                            finish();
                        } else {

                            startActivity(new Intent(CoupleBioActivity.this, CoupleUploadProfileActivity.class));

                        }



                    } else {
                        clsCommon.showDialogMsg(CoupleBioActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(CoupleBioActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(CoupleBioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(CoupleBioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
