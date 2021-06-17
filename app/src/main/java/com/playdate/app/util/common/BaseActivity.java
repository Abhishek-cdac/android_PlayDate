package com.playdate.app.util.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.ui.dialogs.NoInternetDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

public class BaseActivity extends AppCompatActivity {


    public Picasso picasso;
    public SessionPref pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picasso = Picasso.get();
        pref = SessionPref.getInstance(this);
        ConnectionBroadcastReceiver receiver = new ConnectionBroadcastReceiver();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        this.registerReceiver(receiver, filter);
    }

    private NoInternetDialog dialog;

    class ConnectionBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == dialog) {
                dialog = new NoInternetDialog(BaseActivity.this);
            }

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                    dialog.show();
                }
            }
        }
    }
}
