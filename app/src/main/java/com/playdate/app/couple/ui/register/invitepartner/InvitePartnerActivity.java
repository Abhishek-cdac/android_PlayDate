package com.playdate.app.couple.ui.register.invitepartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;


import com.playdate.app.R;
import com.playdate.app.couple.ui.register.connect.ConnectYourPartner;
import com.playdate.app.couple.ui.register.invitesent.InviteSentActivity;
import com.playdate.app.databinding.ActivityInvitePartnerBinding;
import com.playdate.app.util.common.CommonClass;

public class InvitePartnerActivity extends AppCompatActivity {
    InvitepartnerViewModel viewModel;
    ActivityInvitePartnerBinding binding;
    ImageView iv_next;
    boolean once = false;
    int selectedinvitePartner = -1;
    CommonClass clsCommon;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InvitepartnerViewModel();
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InvitePartnerActivity.this, R.layout.activity_invite_partner);
        binding.setLifecycleOwner(this);
        binding.setInvitepartnerViewModel(viewModel);

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(InvitePartnerActivity.this, InviteSentActivity.class));
            }
        });

        viewModel.onBackClick(). observe(this,new Observer<Boolean>() {
            @Override
            public void onChanged (Boolean click){
                finish();
            }
        });


    }


}
