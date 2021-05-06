package com.playdate.app.ui.register.gender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityGenderBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.age_verification.AgeVerifiationActivity;
import com.playdate.app.ui.register.interestin.InterestActivity;
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

public class GenderSelActivity extends AppCompatActivity {

    GenderViewModel viewModel;
    ActivityGenderBinding binding;

    boolean once = false;
    int selectedGender = -1;
    CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new GenderViewModel();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(GenderSelActivity.this, R.layout.activity_gender);
        binding.setLifecycleOwner(this);
        binding.setGenderViewModel(viewModel);

        viewModel.OnFeMaleClick().observe(this, click -> {
            selectedGender = 1;
            binding.btnTaken.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.btnSingle.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));



            binding.ivNext.setVisibility(View.VISIBLE);
        });
        viewModel.OnMaleClick().observe(this, click -> {
            selectedGender = 0;
            binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnSingle.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });


        viewModel.OnNBClick().observe(this, click -> {
            selectedGender = 2;
            binding.btnSingle.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (selectedGender == -1) {
                    clsCommon.showDialogMsg(GenderSelActivity.this, "PlayDate", "Please select your gender", "Ok");
                } else {
                    callAPI();
                }


            }
        });

        viewModel.onBackClick().observe(this, click -> finish());
    }

    private void callAPI() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("gender", selectedGender == 1 ? "Female" : selectedGender == 0 ? "Male" : "Other");// format 1990-08-12
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
                        pref.saveStringKeyVal(SessionPref.LoginUsergender, selectedGender == 1 ? "Female" : selectedGender == 0 ? "Male" : "Other");
                        startActivity(new Intent(GenderSelActivity.this, RelationActivity.class));
                    } else {
                        clsCommon.showDialogMsg(GenderSelActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(GenderSelActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(GenderSelActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(GenderSelActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
