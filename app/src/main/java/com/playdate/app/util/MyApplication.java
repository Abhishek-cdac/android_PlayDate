package com.playdate.app.util;

import android.app.Application;
import android.widget.Toast;

import com.playdate.app.R;
import com.playdate.app.videocall.util.QBResRequestExecutor;
import com.quickblox.auth.session.QBSettings;

import io.socket.client.IO;
import io.socket.client.Socket;

import static com.playdate.app.data.api.RetrofitClientInstance.SOCKET_URL;

public class MyApplication extends Application {
    private Socket mSocket;
    //App credentials
    private static final String APPLICATION_ID = "92011";
    private static final String AUTH_KEY = "j2xeeLOjteFW2rz";
    private static final String AUTH_SECRET = "Cw9jhgVQVFfgc83";
    private static final String ACCOUNT_KEY = "96V86ZgFvqge8yQcsvNv";
    public static final String USER_DEFAULT_PASSWORD = "quickblox";

    private static MyApplication instance;
    private QBResRequestExecutor qbResRequestExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        checkAppCredentials();
        initCredentials();
        connectWebSocket();
    }

//    private void checkAppCredentials() {
//        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
//            throw new AssertionError(getString(R.string.error_credentials_empty));
//        }
//    }

    private void initCredentials() {
        QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void connectWebSocket() {

        {
            try {
                mSocket = IO.socket(SOCKET_URL);
                mSocket.connect();
            } catch (Exception e) {
//                Toast.makeText(this, "Error connecting socket" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public Socket getmSocket() {
        return mSocket;
    }

}
