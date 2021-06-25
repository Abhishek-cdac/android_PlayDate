package com.playdate.app.util;

import android.app.Application;
import android.widget.Toast;

import io.socket.client.IO;
import io.socket.client.Socket;

import static com.playdate.app.data.api.RetrofitClientInstance.SOCKET_URL;

public class MyApplication extends Application {
    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        connectWebSocket();
    }

    private void connectWebSocket() {

        {
            try {
                mSocket = IO.socket(SOCKET_URL);
                mSocket.connect();
            } catch (Exception e) {
                Toast.makeText(this, "Error connecting socket" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public Socket getmSocket() {
        return mSocket;
    }

}
