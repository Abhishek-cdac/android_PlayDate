package com.playdate.app.ui.register.interestin;

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
import com.playdate.app.databinding.ActivityInterestinBinding;
import com.playdate.app.model.LoginResponse;
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

public class InterestActivity extends AppCompatActivity {

    InterestInViewModel viewModel;
    ActivityInterestinBinding binding;
    boolean Male = false;
    boolean FeMale = false;
    boolean Other = false;
    CommonClass clsCommon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clsCommon = CommonClass.getInstance();
        viewModel = new InterestInViewModel();
        binding = DataBindingUtil.setContentView(InterestActivity.this, R.layout.activity_interestin);
        binding.setLifecycleOwner(this);
        binding.setInterestInViewModel(viewModel);

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
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

                    startActivity(new Intent(InterestActivity.this, UserNameActivity
                            .class));

//                    callAPI(interest);
                }


            }
        });

        viewModel.OnMaleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (click) {
                    Male = true;
                    binding.btnMale.setBackground(getDrawable(R.drawable.selected_btn_back));
                } else {
                    Male = false;
                    binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
                }

                // binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
                // binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));

            }
        });
        viewModel.OnFemaleClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {

                if (click) {
                    FeMale = true;
                    binding.btnFemale.setBackground(getDrawable(R.drawable.selected_btn_back));
                } else {
                    FeMale = false;
                    binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
                }
                //  binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
//                binding.btnFemale.setBackground(getDrawable(R.drawable.selected_btn_back));
                // binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
            }
        });
        viewModel.OnNonBinClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {

                if (click) {
                    Other = true;
                    binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
                } else {
                    Other = false;
                    binding.btnNonBinary.setBackground(getDrawable(R.drawable.normal_btn_back));
                }

                //  binding.btnMale.setBackground(getDrawable(R.drawable.normal_btn_back));
                //  binding.btnFemale.setBackground(getDrawable(R.drawable.normal_btn_back));
//                binding.btnNonBinary.setBackground(getDrawable(R.drawable.selected_btn_back));
            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });

    }

    private void callAPI(String interest) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();


        hashMap.put("interestedIn", interest);// format 1990-08-12
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
                        pref.saveStringKeyVal(SessionPref.LoginUserinterestedIn, interest);
                        startActivity(new Intent(InterestActivity.this, UserNameActivity
                                .class));
                    } else {
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", response.body().getMessage(), "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        clsCommon.showDialogMsg(InterestActivity.this, "PlayDate", jObjError.getString("message").toString(), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InterestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
