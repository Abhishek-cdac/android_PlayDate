package com.playdate.app.couple.ui.register.invitepartner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.playdate.app.R;
import com.playdate.app.couple.ui.register.invitesent.InviteSentActivity;
import com.playdate.app.databinding.ActivityInvitePartnerBinding;
import com.playdate.app.model.LoginUserDetails;
import com.playdate.app.util.common.CommonClass;
import com.playdate.app.util.session.SessionPref;

public class InvitePartnerActivity extends AppCompatActivity {
    InvitepartnerViewModel viewModel;
    ActivityInvitePartnerBinding binding;
    ImageView iv_next;
    ImageView iv_share_code;
    boolean once = false;
    int selectedinvitePartner = -1;
    CommonClass clsCommon;
    Intent mIntent;
    LoginUserDetails loginUserDetails;
    String inviteLink = null;
    String inviteLink1;

    ImageView messengerSend, textSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new InvitepartnerViewModel();
        mIntent = getIntent();
        SessionPref pref = SessionPref.getInstance(this);
        loginUserDetails = new LoginUserDetails();
        inviteLink = pref.getStringVal(SessionPref.LoginUserInviteLink);
        inviteLink1 = loginUserDetails.getInviteLink();

        Log.e("InvitePartnerActivity1", "" + inviteLink1);
        Log.e("InvitePartnerActivity", "" + inviteLink);


        clsCommon = CommonClass.getInstance();
        binding = DataBindingUtil.setContentView(InvitePartnerActivity.this, R.layout.activity_invite_partner);
        binding.setLifecycleOwner(this);
        binding.setInvitepartnerViewModel(viewModel);
        iv_share_code = findViewById(R.id.iv_share_code);
        messengerSend = findViewById(R.id.messengerSend);
        textSend = findViewById(R.id.textSend);

        messengerSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharingToSocialMedia("com.facebook.katana");
                Intent fbIntent = new Intent(Intent.ACTION_SEND);
                fbIntent.setType("text/plain");
                fbIntent.setPackage("com.facebook.katana");
                fbIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
                try {
                    startActivity(fbIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        textSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl();
            }
        });

        iv_share_code.setOnClickListener(v -> {
            shareTextUrl();
        });

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


    }

    // Method to share either text or URL.
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, inviteLink);
        startActivity(Intent.createChooser(share, "PlayDate InviteLink!"));
    }


}
