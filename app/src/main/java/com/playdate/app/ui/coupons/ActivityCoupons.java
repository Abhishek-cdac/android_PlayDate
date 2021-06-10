package com.playdate.app.ui.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.playdate.app.R;
import com.playdate.app.ui.coupons.adapters.FrequentlyQuestionAdapter;

public class ActivityCoupons extends AppCompatActivity implements View.OnClickListener{
    String CouponId, CouponCode, CouponPoints;
    TextView tv_code, txt_points, refer, every_time;
    RelativeLayout rl_getcode,rl_code;
    ImageView facebook_coupan, instagram_coupan, message_coupan, whatsup_coupan;
    String inviteCode, inviteLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_coupan_code);
        inviteCode = getIntent().getStringExtra("inviteCode");
        inviteLink = getIntent().getStringExtra("inviteLink");
        Log.e("inviteCode...", "" + inviteCode);
        Log.e("inviteLink...", "" + inviteLink);

        whatsup_coupan = findViewById(R.id.whatsup_coupan);
        message_coupan = findViewById(R.id.message_coupan);
        instagram_coupan = findViewById(R.id.instagram_coupan);
        facebook_coupan = findViewById(R.id.facebook_coupan);
        tv_code = findViewById(R.id.tv_code);
        txt_points = findViewById(R.id.points);
        refer = findViewById(R.id.refer);
        every_time = findViewById(R.id.every_time);
        rl_getcode = findViewById(R.id.rl_getcode);
        rl_code = findViewById(R.id.rl_code);

        /*value fetch from FragCouponStore*/
        CouponId = getIntent().getStringExtra("Coupon_id");
        CouponCode = getIntent().getStringExtra("Coupon_code");
        CouponPoints = getIntent().getStringExtra("Coupon_points");
        tv_code.setText(CouponCode);
      //  txt_points.setText(CouponPoints + " Points");
        rl_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_getcode.setVisibility(View.GONE);
                rl_code.setVisibility(View.VISIBLE);

            }
        });
//        facebook_coupan.setOnClickListener(this);
//        instagram_coupan.setOnClickListener(this);
//        whatsup_coupan.setOnClickListener(this);
//        message_coupan.setOnClickListener(this);
        RecyclerView rv_frequently = findViewById(R.id.rv_frequently);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_frequently.setLayoutManager(manager);
        FrequentlyQuestionAdapter adapter = new FrequentlyQuestionAdapter();
        rv_frequently.setAdapter(adapter);
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
