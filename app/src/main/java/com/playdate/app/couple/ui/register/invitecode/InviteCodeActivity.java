package com.playdate.app.couple.ui.register.invitecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.playdate.app.R;
import com.playdate.app.data.api.GetDataService;
import com.playdate.app.data.api.RetrofitClientInstance;
import com.playdate.app.databinding.ActivityInviteCodeBinding;
import com.playdate.app.model.CommonModel;
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



public class InviteCodeActivity extends AppCompatActivity {
    InviteCodeViewModel viewModel;
    ActivityInviteCodeBinding binding;

    boolean once = false;
    int selectedinviteCode = -1;
    CommonClass clsCommon;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InviteCodeViewModel();
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InviteCodeActivity.this, R.layout.activity_invite_code);
        binding.setLifecycleOwner(this);
        binding.setInviteCodeViewModel(viewModel);

        viewModel.OnSubmitClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                if (null == viewModel.txtInviteCode.getValue()) {
                    clsCommon.showDialogMsg(InviteCodeActivity.this, "PlayDate", "Enter InviteCode!", "Ok");
                } else if (viewModel.txtInviteCode.getValue().isEmpty()) {
                    clsCommon.showDialogMsg(InviteCodeActivity.this, "PlayDate", "Enter InviteCode!", "Ok");
                }
//                else if (viewModel.txtInviteCode.getValue().length() < 4) {
//                    clsCommon.showDialogMsg(InviteCodeActivity.this, "PlayDate", "InviteCode must be of 4 characters in length", "Ok");
//                }
                else {
                    callAPI();

                }
            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });

//        viewModel.OnResendClick().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean click) {
//                finish();
//            }
//        });


    }

    private void callAPI() {
        SessionPref pref = SessionPref.getInstance(this);
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Map<String, String> hashMap = new HashMap<>();
        String joinInviteCode = viewModel.txtInviteCode.getValue();

        hashMap.put("inviteCode", joinInviteCode);
        TransparentProgressDialog pd = TransparentProgressDialog.getInstance(this);
        pd.show();
        Call<CommonModel> call = service.joinCoupleCode("Bearer " + pref.getStringVal(SessionPref.LoginUsertoken), hashMap);
        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                pd.cancel();
                if (response.code() == 200) {
                    if (response.body().getStatus() == 1) {
                        Log.d("InviteCode..", response.body().toString());
                        nextPage();
//                        SessionPref pref=SessionPref.getInstance(InviteCodeActivity.this);
//                        pref.saveBoolKeyVal(LoginVerified, true);
//                        finish();
                    } else {
                        clsCommon.showDialogMsg(InviteCodeActivity.this, "PlayDate", "Something went wrong...Please try later!", "Ok");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        clsCommon.showDialogMsg(InviteCodeActivity.this, "PlayDate", jObjError.getString("message"), "Ok");
                    } catch (Exception e) {
                        Toast.makeText(InviteCodeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                t.printStackTrace();
                pd.cancel();
                Toast.makeText(InviteCodeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextPage() {

        InviteCodeActivity.this.startActivity(new Intent(InviteCodeActivity.this, InterestActivity.class));


    }

}
