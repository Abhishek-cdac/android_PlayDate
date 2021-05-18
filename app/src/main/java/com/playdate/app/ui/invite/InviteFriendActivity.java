package com.playdate.app.ui.invite;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.R;

public class InviteFriendActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ImageView facebook_coupan = findViewById(R.id.facebook_coupan);
        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView message_coupan = findViewById(R.id.message_coupan);
        facebook_coupan.setOnClickListener(this);
        message_coupan.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.facebook_coupan) {
            SharingToSocialMedia("com.facebook.katana");

        }else if(id==R.id.message_coupan){
            try {
//                Intent sendIntent = new Intent(Intent.ACTION_MAIN);
//                sendIntent.putExtra("sms_body", "text");
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/*");
//                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"YOUR TEXT TO SHARE IN INSTAGRAM");
                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(id==R.id.iv_back){
            finish();
        }
    }


    public void SharingToSocialMedia(String application) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Code is 1144");
//        intent.putExtra(Intent.EXTRA_STREAM, bmpUri);

        boolean installed = isPackageInstalled(application,getPackageManager());
        if (installed) {
            intent.setPackage(application);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Installed application first", Toast.LENGTH_LONG).show();
        }

    }


    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
