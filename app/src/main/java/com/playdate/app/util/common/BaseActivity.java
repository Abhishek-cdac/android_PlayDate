package com.playdate.app.util.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.ui.dialogs.NoInternetDialog;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;

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
        connectWebSocket();
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

    WebSocketClient mWebSocketClient;
    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("http://139.59.0.106:3000/chat");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
//                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        TextView textView = (TextView)findViewById(R.id.messages);
//                        textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClosing(int code, String reason, boolean remote) {
                super.onClosing(code, reason, remote);
                Log.i("Websocket", "onClosing " + reason);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        try {
            mWebSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
