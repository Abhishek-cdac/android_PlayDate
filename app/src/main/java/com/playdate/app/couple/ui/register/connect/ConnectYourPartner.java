package com.playdate.app.couple.ui.register.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.invitecode.InviteCodeActivity;
import com.playdate.app.couple.ui.register.invitepartner.InvitePartnerActivity;
import com.playdate.app.databinding.ActivityConnectYourPartnerBinding;
import com.playdate.app.util.common.CommonClass;

public class ConnectYourPartner extends AppCompatActivity {

    private ActivityConnectYourPartnerBinding binding;
    private int selectedconnect = -1;
    private CommonClass clsCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectYourPartnerViewModel viewModel = new ConnectYourPartnerViewModel();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(ConnectYourPartner.this, R.layout.activity_connect_your_partner);
        binding.setLifecycleOwner(this);
        binding.setConnectYourPartnerViewModel(viewModel);


        viewModel.OnJoinClick().observe(this, click -> {
            selectedconnect = 1;
            binding.btnJoin.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.btnInvite.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });
        viewModel.OnInviteClick().observe(this, click -> {
            selectedconnect = 0;
            binding.btnJoin.setBackground(getDrawable(R.drawable.normal_btn_back));
            binding.btnInvite.setBackground(getDrawable(R.drawable.selected_btn_back));
            binding.ivNext.setVisibility(View.VISIBLE);
        });

        viewModel.OnNextClick().observe(this, click -> {
            if (selectedconnect == -1) {
                clsCommon.showDialogMsg(ConnectYourPartner.this, "PlayDate", "Please select ", "Ok");
            } else if (selectedconnect == 1) {
                startActivity(new Intent(ConnectYourPartner.this, InviteCodeActivity.class));
            } else if (selectedconnect == 0) {

                startActivity(new Intent(ConnectYourPartner.this, InvitePartnerActivity.class));

            }

        });

        viewModel.onBackClick().observe(this, click -> finish());


    }


}
