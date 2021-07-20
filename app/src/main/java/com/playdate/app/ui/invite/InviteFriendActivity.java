package com.playdate.app.ui.invite;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

import java.util.List;

public class InviteFriendActivity extends AppCompatActivity implements View.OnClickListener {
    String inviteCode, inviteLink;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        inviteCode = getIntent().getStringExtra("inviteCode");
        inviteLink = getIntent().getStringExtra("inviteLink");
        Log.e("inviteCode...", "" + inviteCode);
        Log.e("inviteLink...", "" + inviteLink);
        ImageView whtasapp_coupan = findViewById(R.id.whtasapp_coupan);
        ImageView facebook_coupan = findViewById(R.id.facebook_coupan);
        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView message_coupan = findViewById(R.id.message_coupan);
        ImageView instagram_coupan = findViewById(R.id.instagram_coupan);
        facebook_coupan.setOnClickListener(this);
        instagram_coupan.setOnClickListener(this);
        whtasapp_coupan.setOnClickListener(this);
        message_coupan.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.facebook_coupan) {
            Intent share=new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, inviteLink);
            share.setPackage("com.facebook.katana"); //Facebook App package
            startActivity(Intent.createChooser(share, "Playdate"));
   /*         // SharingToSocialMedia("com.facebook.katana");
            Intent fbIntent = new Intent(Intent.ACTION_SEND);
            fbIntent.setType("text/plain");
            fbIntent.setPackage("com.facebook.katana");
            fbIntent.putExtra(Intent.EXTRA_STREAM, inviteLink);
            try {
                startActivity(fbIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

            }*/
        }
        if (id == R.id.message_coupan) {

            shareTextUrl();


        } else if (id == R.id.instagram_coupan) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.instagram.android");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Instagram have not been installed.", Toast.LENGTH_SHORT).show();

            }

        } else if (id == R.id.whtasapp_coupan) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, inviteLink);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();

            }

        } else if (id == R.id.iv_back) {
            finish();
        }
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
