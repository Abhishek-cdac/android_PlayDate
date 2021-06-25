package com.playdate.app.util.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.playdate.app.ui.dialogs.NoInternetDialog;
import com.playdate.app.util.MyApplication;
import com.playdate.app.util.session.SessionPref;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

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

    public Socket mSocket;


    private void connectWebSocket() {

        {
            try {
               MyApplication application= (MyApplication) getApplication();
                mSocket=application.getmSocket();
                checkConnect();
              //  mSocket.on("chat_message", onNewMessage);
             //   mSocket.on("Data", onNewMessage);


            } catch (Exception e) {
                Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    Handler mHandler;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(null!=mHandler){
                mHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isConnectedToSocket=false;
    void checkConnect() {
        if (!isConnectedToSocket) {
            mHandler = new Handler();
            mHandler.postDelayed(() -> {
                if (mSocket.connected()) {
                    try {
                        isConnectedToSocket=true;
                        //Toast.makeText(BaseActivity.this, "Connected to socket", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject();
                        SessionPref pref = SessionPref.getInstance(BaseActivity.this);
                        jsonObject.put("userId", pref.getStringVal(SessionPref.LoginUserID));
                        jsonObject.put("token", pref.getStringVal(SessionPref.LoginUsertoken));
                        mSocket.emit("online", jsonObject);
    //                    mSocket.emit("chat_message", "test messgae from ajit");
                    } catch (Exception e) {
                        e.printStackTrace();
                      //  Toast.makeText(BaseActivity.this, "emit"+e.toString(), Toast.LENGTH_SHORT).show();
                    }



                }
            }, 1500);
        }
    }


}
