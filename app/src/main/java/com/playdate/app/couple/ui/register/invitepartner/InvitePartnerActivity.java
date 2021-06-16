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
    ImageView iv_share_code;
    boolean once = false;
    int selectedinvitePartner = -1;
    CommonClass clsCommon;
    Intent mIntent;
    String inviteLink = "Welcome to PlayDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InvitepartnerViewModel();
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InvitePartnerActivity.this, R.layout.activity_invite_partner);
        binding.setLifecycleOwner(this);
        binding.setInvitepartnerViewModel(viewModel);
        iv_share_code = findViewById(R.id.iv_share_code);

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(InvitePartnerActivity.this, InviteSentActivity.class));
            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });

        iv_share_code.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_TEXT, inviteLink);
            startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
        });

    }


}
