package com.playdate.app.util.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.playdate.app.ui.dialogs.NoInternetDialog
import com.playdate.app.util.session.SessionPref
import com.squareup.picasso.Picasso
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException


open class BaseActivity : AppCompatActivity() {
    @JvmField
    var picasso: Picasso? = null

    @JvmField
    var pref: SessionPref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        picasso = Picasso.get()
        pref = SessionPref.getInstance(this)
        val receiver = ConnectionBroadcastReceiver()
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(receiver, filter)
        createWebSocketClient(this)
    }

    private var dialog: NoInternetDialog? = null

    internal inner class ConnectionBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (null == dialog) {
                dialog = NoInternetDialog(this@BaseActivity)
            }
            if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val networkInfo =
                    intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
                if (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    if (dialog!!.isShowing) {
                        dialog!!.dismiss()
                    }
                } else if (networkInfo != null && networkInfo.detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
                    dialog!!.show()
                }
            }
        }
    }

    private var webSocketClient: WebSocketClient? = null
    fun createWebSocketClient(baseActivity: BaseActivity) {
        val uri: URI
        try {//http://139.59.0.106:3000/chat
            // Connect to local host
            uri = URI("ws://139.59.0.106:3000/chat")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen() {
                Log.i("WebSocket", "Session is starting")
                webSocketClient?.send("Hello World!")
            }

            override fun onTextReceived(s: String) {
                Log.i("WebSocket", "Message received")
                runOnUiThread {
                    try {
//                        val textView = findViewById<TextView>(R.id.animalSound)
//                        textView.text = s
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onBinaryReceived(data: ByteArray) {}
            override fun onPingReceived(data: ByteArray) {}
            override fun onPongReceived(data: ByteArray) {}
            override fun onException(e: Exception) {
                Log.d("****", e.toString())
            }

            override fun onCloseReceived() {
                Log.i("WebSocket", "Closed ")
                println("onCloseReceived")
            }
        }
        webSocketClient?.setConnectTimeout(10000)
        webSocketClient?.setReadTimeout(60000)
        webSocketClient?.enableAutomaticReconnection(5000)
        webSocketClient?.connect()
    }


}