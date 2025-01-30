package com.amozeshgam.amozeshgam.service.bound

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.RepeatMode
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NotificationPlayerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import okhttp3.internal.notify
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class PodcastPlayerService : Service() {

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private var podcastTitle = ""


    inner class Binder : android.os.Binder() {
        @OptIn(UnstableApi::class)
        suspend fun loadUri(uri: String, podcastTitle: String): Boolean {
            this@PodcastPlayerService.podcastTitle = podcastTitle
            exoPlayer.setMediaItem(MediaItem.fromUri(uri))
            exoPlayer.prepare()
            while (!exoPlayer.isLoading) {
                delay(500)
            }
            return true
        }

        fun pausePodcast() {
            exoPlayer.pause()
        }

        fun playPodcast(): Long {
            exoPlayer.playWhenReady = true
            return exoPlayer.duration
        }

        fun repeatPodcast(state: Int = Player.REPEAT_MODE_OFF) {
            exoPlayer.repeatMode = state
        }

        fun seekTo(position: Long) {
            exoPlayer.seekTo(position)
        }

        fun resetPosition() {
            exoPlayer.seekToDefaultPosition()
        }

        fun getCurrentPosition(): Long {
            return exoPlayer.currentPosition
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }


    override fun onDestroy() {
        exoPlayer.release()
        super.onDestroy()
    }

}