package com.amozeshgam.amozeshgam.service.started

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.core.app.NotificationCompat
import com.amozeshgam.amozeshgam.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FireBaseService : FirebaseMessagingService() {
    private val notificationChannel = "amozeshgamChannel"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("jjj", "onMessageReceived: ${message.data.size}")
        message.notification?.let {
            showNotification(
                title = message.notification?.title.toString(),
                message = message.notification?.body.toString(),
                vibrator = message.notification?.vibrateTimings ?: longArrayOf(500, 500, 500)
            )
        }
        super.onMessageReceived(message)
    }

    private fun showNotification(title: String, message: String, vibrator: LongArray) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannel,
                    notificationChannel,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        val notification = NotificationCompat.Builder(this@FireBaseService, notificationChannel)
            .setSmallIcon(R.mipmap.icon_round)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(vibrator)
            .setAutoCancel(true)
        notificationManager.notify(100, notification.build())
    }
}