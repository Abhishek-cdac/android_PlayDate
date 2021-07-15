package com.playdate.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.playdate.app.R
import com.playdate.app.ui.dashboard.DashboardActivity
import com.playdate.app.ui.date.DateBaseActivity
import com.playdate.app.util.session.SessionPref
import com.playdate.app.util.session.SessionPref.LoginUserFCMID
import java.lang.Math.random
import kotlin.random.Random
import kotlin.random.nextInt


class FcmMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e(TAG, "****Refreshed token: $token")
        val pref = SessionPref.getInstance(this)
        pref.saveStringKeyVal(LoginUserFCMID, token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        val extras = Bundle()
        for ((key, value) in remoteMessage.data) {
            extras.putString(key, value)
        }

        if (extras.containsKey("userMessage") && !extras.getString("userMessage").isNullOrBlank() ||
            extras.containsKey("notificationType") && !extras.getString("notificationType")
                .isNullOrBlank()
        ) {
            sendNotification(
                extras.getString("userMessage")!!,
                extras.getString("notificationType")!!
            )
        }


    }

    fun rand(s: Int, e: Int) = Random.nextInt(s, e + 1)
    private fun sendNotification(messageBody: String, messageType: String) {

        Log.e("messageBody", "" + messageBody);
        Log.e("messageType", "" + messageType);

        val rand = rand(1, 3000)

        val pendingIntent: PendingIntent
        val intent: Intent

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder: NotificationCompat.Builder? = null
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                packageName,
                packageName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = packageName
            notificationManager.createNotificationChannel(channel)
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        } else {
            if (notificationBuilder == null) {
                notificationBuilder = NotificationCompat.Builder(application, packageName)
            }
        }
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        when (messageType) {
            "FRIEND_REQUEST" -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand /* ID of notification */,
                    notificationBuilder.build()
                )
            }
            "MATCH_REQUEST" -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0  /*Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand  /*ID of notification */,
                    notificationBuilder.build()
                )
            }
            "DATE_REQUEST" -> {
                val intent = Intent(this, DateBaseActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0  /*Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand /* ID of notification*/,
                    notificationBuilder.build()
                )


            }
            "RELATIONSHIP_REQUEST" -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0 /* Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand  /*ID of notification */,
                    notificationBuilder.build()
                )
            }
            "POST_TAGGED" -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0  /*Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand  /*ID of notification*/,
                    notificationBuilder.build()
                )
            }
            "POST_LIKED" -> {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0  /*Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand /* ID of notification*/,
                    notificationBuilder.build()
                )

            }
            "POST_COMMENT" -> {
                intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                pendingIntent = PendingIntent.getActivity(
                    this, 0  /*Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand /* ID of notification*/,
                    notificationBuilder.build()
                )
            }
            "CHAT_REQUEST" -> {
                intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("noti", true)
                pendingIntent = PendingIntent.getActivity(
                    this, 0 /* Request code*/, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
                notificationBuilder.setContentIntent(pendingIntent)
                notificationManager.notify(
                    rand  /*ID of notification*/,
                    notificationBuilder.build()
                )
            }
        }

    }

    companion object {
        private const val TAG = "FcmMessageService"
    }
}