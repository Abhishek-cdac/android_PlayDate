package com.playdate.app.couple.ui.register.connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.invitecode.InviteCodeActivity;
import com.playdate.app.couple.ui.register.invitepartner.InvitePartnerActivity;
import com.playdate.app.databinding.ActivityConnectYourPartnerBinding;
import com.playdate.app.util.common.CommonClass;

public class ConnectYourPartner extends AppCompatActivity {

    ConnectYourPartnerViewModel viewModel;
    ActivityConnectYourPartnerBinding binding;

    boolean once = false;
    int selectedconnect = -1;
    CommonClass clsCommon;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ConnectYourPartnerViewModel();
        mIntent = getIntent();
        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(ConnectYourPartner.this, R.layout.activity_connect_your_partner);
        binding.setLifecycleOwner(this);
        binding.setConnectYourPartnerViewModel(viewModel);


        viewModel.OnJoinClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                selectedconnect = 1;
                binding.btnJoin.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.btnInvite.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);
//                if (!once) {
//                    Animation fadeInAnimation = AnimationUtils.loadAnimation(RelationActivity.this, R.anim.slide_in_left);
//                    binding.ivNext.startAnimation(fadeInAnimation);
//                    once=true;
//                }
            }
        });
        viewModel.OnInviteClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                selectedconnect = 0;
                binding.btnJoin.setBackground(getDrawable(R.drawable.normal_btn_back));
                binding.btnInvite.setBackground(getDrawable(R.drawable.selected_btn_back));
                binding.ivNext.setVisibility(View.VISIBLE);
//                if (!once) {
//                    Animation fadeInAnimation = AnimationUtils.loadAnimation(RelationActivity.this, R.anim.slide_in_left);
//                    binding.ivNext.startAnimation(fadeInAnimation);
//                    once=true;
//                }
            }
        });

        viewModel.OnNextClick().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean click) {
                Log.e("selectedconnect", "" + selectedconnect);

                if (selectedconnect==-1) {
                    clsCommon.showDialogMsg(ConnectYourPartner.this, "PlayDate", "Please select ", "Ok");
                } else if (selectedconnect==1)
                {
                   startActivity(new Intent(ConnectYourPartner.this, InviteCodeActivity.class));
                } else if (selectedconnect==0) {

                   startActivity(new Intent(ConnectYourPartner.this, InvitePartnerActivity.class));

                }

        }
    });

        viewModel.onBackClick(). observe(this,new Observer<Boolean>() {
        @Override
        public void onChanged (Boolean click){
            finish();
        }
    });


//        if (mIntent.getBooleanExtra("fromProfile", false)) {
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//
//                    if (mIntent.getStringExtra("Selected").toLowerCase().equals("invite")) {
//                        viewModel.setInvite();
//                    } else {
//                        viewModel.setJoin();
//                    }
//                }
//            }, 200);
//
//
//        }
}


}
