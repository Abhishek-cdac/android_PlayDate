package com.playdate.app.couple.ui.register.invitecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.connect.ConnectYourPartner;
import com.playdate.app.couple.ui.register.invitepartner.InvitePartnerActivity;
import com.playdate.app.couple.ui.register.invitepartner.InvitepartnerViewModel;
import com.playdate.app.databinding.ActivityInviteCodeBinding;
import com.playdate.app.databinding.ActivityInvitePartnerBinding;
import com.playdate.app.util.common.CommonClass;

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
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InviteCodeActivity.this, R.layout.activity_invite_code);
        binding.setLifecycleOwner(this);
        binding.setInviteCodeViewModel(viewModel);

//        viewModel.OnSubmitClick().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean click) {
//
//            }
//        });

//        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean click) {
//                finish();
//            }
//        });

//        viewModel.OnResendClick().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean click) {
//                finish();
//            }
//        });



    }


}
