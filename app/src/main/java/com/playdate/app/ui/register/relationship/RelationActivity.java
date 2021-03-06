package com.playdate.app.ui.register.relationship;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.connect.ConnectYourPartner;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityRelationshipBinding;
import com.playdate.app.model.LoginResponse;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.common.TransparentProgressDialog;
import com.playdate.app.util.session.SessionPref;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelationActivity extends AppCompatActivity {

    private RelatiponShipViewModel viewModel;
    private ActivityRelationshipBinding binding;
    private int selectedRelationShip = -1;
    private CommonClass clsCommon;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new RelatiponShipViewModel();
        mIntent = getIntent();
        mHandler = new Handler(Looper.getMainLooper());
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(RelationActivity.this, R.layout.activity_relationship);
        binding.setLifecycleOwner(this);
        binding.setRelatiponShipViewModel(viewModel);


        viewModel.OnTakenClick().observe(this, click -> {
            selectedRelationShip = 1;
            binding.btnTaken.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.btnSingle.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });
        viewModel.OnSingleClick().observe(this, click -> {
            selectedRelationShip = 0;
            binding.btnTaken.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnSingle.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });

        viewModel.OnNextClick().observe(this, click -> {
            if (selectedRelationShip == -1) {
                clsCommon.showDialogMsg(RelationActivity.this, "PlayDate", "Please select relationship", "Ok");
            } else {
                callAPI();
            }
        });

        viewModel.onBackClick().observe(this, click -> finish());


        if (mIntent.getBooleanExtra("fromProfile", false)) {
            mHandler.postDelayed(() -> {

                if (mIntent.getStringExtra("Selected").toLowerCase().equals("single")) {
                    viewModel.setSingle();
                } else {
                    viewModel.setTaken();
                }
            }, 200);


        }
    }

    private Handler mHandler;

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", pref.getStringVal(SessionPref.LoginUserID));

        hashMap.put("relationship", selectedRelationShip == 1 ? "Taken" : "Single");// format 1990-08-12
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();


        Call<LoginResponse> call = service.updateProfile("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        pref.saveStringKeyVal(SessionPref.LoginUserrelationship, selectedRelationShip == 1 ? "Taken" : "Single");
                        if (mIntent.getBooleanExtra("fromProfile", false)) {

                            finish();
                        } else {
                            if (selectedRelationShip == 1) {
                                startActivity(new Intent(RelationActivity.this, ConnectYourPartner.class));
                            } else {
                                startActivity(new Intent(RelationActivity.this, InterestActivity.class));
                            }
                        }
                    } else {
                        clsCommon.showDialogMsg(RelationActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(RelationActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(RelationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(RelationActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
