package com.amozeshgam.amozeshgam.service.started

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.amozeshgam.amozeshgam.handler.DownloadServiceState
import dagger.hilt.android.AndroidEntryPoint
import com.amozeshgam.amozeshgam.R
import com.kdownloader.KDownloader

@AndroidEntryPoint
class UpdateService : Service() {
    private val kDownloader = KDownloader.create(this@UpdateService)
    private val amozeshgamTag = "amozeshgamTag"
    private val notificationChannel = "amozeshgamDownloaderNotificationChannel"
    private val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val request = kDownloader.newRequestBuilder(
            "https://app.amozeshgam.com/update/last.apk", this.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS
            )!!.path + "/amozeshgam", "amozeshgam.apk"
        ).tag(amozeshgamTag).build()
        val download = kDownloader.enqueue(
            request,
            onStart = {
                onStart()
            },
            onError = {},
            onProgress = {},
            onCompleted = {}
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannel, notificationChannel,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        if (intent?.action.toString() == DownloadServiceState.START.toString()) {
            kDownloader.resume(download)
        } else if (intent?.action.toString() == DownloadServiceState.PAUSE.toString()) {
            kDownloader.pause(download)
        } else {
            kDownloader.cancel(amozeshgamTag)
        }
        return super.onStartCommand(intent, flags, startId)
    }
    private fun onStart() {
        val notification = NotificationCompat.Builder(this@UpdateService, notificationChannel)
            .setSmallIcon(R.mipmap.icon_round)
            .setContentTitle("دانلود")
            .setContentText("دانلود بروزرسانی جدید")
        notification.addAction(
            R.drawable.ic_play, "ادامه", PendingIntent.getService(
                this@UpdateService, 0,
                Intent(
                    this,
                    UpdateService::class.java
                ).setAction(DownloadServiceState.START.toString()),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        notification.addAction(
            R.drawable.ic_stop, "توقف", PendingIntent.getService(
                this@UpdateService, 0,
                Intent(
                    this,
                    UpdateService::class.java
                ).setAction(DownloadServiceState.PAUSE.toString()),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        notification.addAction(
            R.drawable.ic_close, "لغو", PendingIntent.getService(
                this@UpdateService, 0,
                Intent(
                    this,
                    UpdateService::class.java
                ).setAction(DownloadServiceState.CANCEL.toString()),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        startForeground(200, notification.build())
    }
}