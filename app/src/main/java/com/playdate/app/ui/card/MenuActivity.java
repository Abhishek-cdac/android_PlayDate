package com.playdate.app.ui.card;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.playdate.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.card.payment.CardIOActivity;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.scan_temp);
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanning();
            }
        });

    }
final  int REQUEST_SCAN=100;
    // method within MyActivity from previous step
    public void startScanning() {
        Intent intent = new Intent(this, CardIOActivity.class)
            .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false);
        startActivityForResult(intent, REQUEST_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Scan", Toast.LENGTH_SHORT).show();
        if ((requestCode == REQUEST_SCAN ) && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            String resultDisplayStr="";
            if (data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                Toast.makeText(this, ""+CardIOActivity.EXTRA_SCAN_RESULT.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }



}