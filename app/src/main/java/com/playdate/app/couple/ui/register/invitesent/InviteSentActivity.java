package com.playdate.app.couple.ui.register.invitesent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.databinding.ActivityInviteSentBinding;
import com.playdate.app.ui.register.interestin.InterestActivity;
import com.playdate.app.util.common.CommonClass;

public class InviteSentActivity extends AppCompatActivity {
    InviteSentViewModel viewModel;
    ActivityInviteSentBinding binding;

    boolean once = false;
    int selectedinvitePartner = -1;
    CommonClass clsCommon;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InviteSentViewModel();
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InviteSentActivity.this, R.layout.activity_invite_sent);
        binding.setLifecycleOwner(this);
        binding.setInviteSentViewModel(viewModel);


        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                startActivity(new Intent(InviteSentActivity.this, InterestActivity.class));
            }
        });

        viewModel.onBackClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                finish();
            }
        });


    }


}
