package com.playdate.app.ui.register.bio;

import static com.playdate.app.util.session.SessionPref.LoginUserpersonalBio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.profile.UploadProfileActivity;
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


public class BioActivity extends AppCompatActivity {

    private BioViewModel viewModel;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new BioViewModel();
        clsCommon = CommonClass.getInstance();
        com.playdate.app.databinding.ActivityBioBinding bioBinding = DataBindingUtil.setContentView(BioActivity.this, R.layout.activity_bio);
        bioBinding.setLifecycleOwner(this);
        mIntent = getIntent();
        bioBinding.setBioViewModel(viewModel);
        LinearLayout ll_bio_bg = findViewById(R.id.ll_bio_bg);

        if (mIntent.getBooleanExtra("fromProfile", false)) {
            SessionPref pref = SessionPref.getInstance(this);
            viewModel.BioText.setValue(pref.getStringVal(LoginUserpersonalBio));
        }
        viewModel.OnNextClick().observe(this, click -> {
            if (null == viewModel.BioText.getValue()) {
                clsCommon.showDialogMsg(BioActivity.this, "PlayDate", "Enter your personal bio.", "Ok");
            } else if (viewModel.BioText.getValue().trim().equals("")) {
                clsCommon.showDialogMsg(BioActivity.this, "PlayDate", "Enter your personal bio.", "Ok");
            } else {
                callAPI();
            }


        });

        viewModel.onBackClick().observe(this, click -> finish());


        ll_bio_bg.setOnClickListener(v -> {
//                Log.d("linear", "onClick:");
            clsCommon.hideKeyboard(v, BioActivity.this);
        });
    }

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String bio = viewModel.BioText.getValue();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));
        hashMap.put("personalBio", bio);// format 1990-08-12
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
                        pref.saveStringKeyVal(LoginUserpersonalBio, bio);

                        if (mIntent.getBooleanExtra("fromProfile", false)) {
                            Intent mIntent = new Intent();
                            setResult(409, mIntent);
                            finish();
                        } else {

                            startActivity(new Intent(BioActivity.this, UploadProfileActivity
                                    .class));

                        }


                    } else {
                        clsCommon.showDialogMsg(BioActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        clsCommon.showDialogMsg(BioActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(BioActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
