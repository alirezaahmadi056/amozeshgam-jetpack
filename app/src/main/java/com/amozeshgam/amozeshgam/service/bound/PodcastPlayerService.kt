package com.amozeshgam.amozeshgam.service.bound

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NotificationPlayerState
import com.amozeshgam.amozeshgam.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class PodcastPlayerService : MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private var podcastTitle = ""
    private var podcastOwner = ""
    private lateinit var notificationManager: NotificationManager
    private val binder = Binder()

    companion object {
        const val NOTIFICATION_CHANEL_ID = "AmozeshgamMediaSource"
        const val NOTIFICATION_CHANEL_ID_NUMBER = 11

    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mediaSession = MediaSession.Builder(this, exoPlayer).build()

    }

    override fun onBind(intent: Intent?): IBinder {
        super.onBind(intent)
        return binder
    }

    @OptIn(UnstableApi::class)
    private fun createNotification(): Notification {
        val playPauseAction = NotificationCompat.Action(
            if (exoPlayer.isPlaying) R.drawable.ic_stop else R.drawable.ic_play,
            "PlayPause",
            createPendingIntent()
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_app_logo)
            .setContentTitle(podcastTitle)
            .setContentText(podcastOwner)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setDeleteIntent(getDeleteIntent())
            .addAction(playPauseAction)
            .setContentIntent(createMainActivityPendingIntent())
            .build()
    }

    private fun getDeleteIntent(): PendingIntent {
        val intent = Intent(this, PodcastPlayerService::class.java).apply {
            action = NotificationPlayerState.DESTROY.name
        }
        return PendingIntent.getService(
            this,
            NotificationPlayerState.DESTROY.ordinal,
            intent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
    }

    private fun createMainActivityPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, PodcastPlayerService::class.java).apply {
            this.action = NotificationPlayerState.PLAY_AND_PAUSE.name
        }
        return PendingIntent.getService(
            this,
            NotificationPlayerState.PLAY_AND_PAUSE.ordinal,
            intent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
    }


    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANEL_ID,
                NOTIFICATION_CHANEL_ID,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = createNotification()
        notificationManager.notify(NOTIFICATION_CHANEL_ID_NUMBER, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mediaSession.release()
        notificationManager.cancelAll()
        stopSelf()
    }


    inner class Binder : android.os.Binder() {
        @OptIn(UnstableApi::class)
        suspend fun loadUri(uri: String, podcastTitle: String, podcastOwner: String): Boolean {
            return suspendCoroutine { continuation ->
                this@PodcastPlayerService.podcastTitle = podcastTitle
                this@PodcastPlayerService.podcastOwner = podcastOwner
                exoPlayer.setMediaItem(MediaItem.fromUri(uri))
                exoPlayer.prepare()

                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            continuation.resume(true)
                            exoPlayer.removeListener(this)
                        } else if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED) {
                            continuation.resume(false)
                            exoPlayer.removeListener(this)
                        }
                    }
                })
            }
        }


        fun pausePodcast() {
            showNotification()
            exoPlayer.pause()
        }

        fun playPodcast(): Long {
            showNotification()
            exoPlayer.playWhenReady = true
            return exoPlayer.duration
        }

        fun repeatPodcast(state: Int = Player.REPEAT_MODE_OFF) {
            exoPlayer.repeatMode = state
        }

        fun seekTo(position: Long) {
            showNotification()
            exoPlayer.seekTo(position)
        }

        fun resetPosition() {
            showNotification()
            exoPlayer.seekToDefaultPosition()
        }

        fun getCurrentPosition(): Long {
            return exoPlayer.currentPosition
        }

        fun releasePlayer() {
            exoPlayer.release()
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            NotificationPlayerState.PLAY_AND_PAUSE.name -> {
                if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                showNotification()
            }

            NotificationPlayerState.DESTROY.name -> {
                exoPlayer.pause()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return this.mediaSession
    }
}