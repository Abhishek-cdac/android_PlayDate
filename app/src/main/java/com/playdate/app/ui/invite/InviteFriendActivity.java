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
            // SharingToSocialMedia("com.facebook.katana");
            Intent fbIntent = new Intent(Intent.ACTION_SEND);
            fbIntent.setType("text/plain");
            fbIntent.setPackage("com.facebook.katana");
            fbIntent.putExtra(Intent.EXTRA_STREAM, inviteLink);
            try {
                startActivity(fbIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(),
                        "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

            }
        }
        if (id == R.id.message_coupan) {

            shareTextUrl();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//          //  intent.setType(Telephony.Sms.getDefaultSmsPackage(this));
//            intent.setType("vnd.android-dir/mms-sms");
//            intent.putExtra(Intent.EXTRA_TEXT, inviteLink);
//            startActivity(intent);


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


    @Nullable
    public String getDefaultSmsAppPackageName(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            try {
                return Telephony.Sms.getDefaultSmsPackage(context);
            } catch (final Throwable e) {
            }
        final Intent intent = new Intent(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms");
        intent.putExtra(Intent.EXTRA_TEXT, inviteLink);
        final List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!resolveInfoList.isEmpty())
            return resolveInfoList.get(0).activityInfo.packageName;
        return null;
    }

}
