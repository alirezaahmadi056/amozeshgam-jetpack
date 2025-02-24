package com.amozeshgam.amozeshgam.service.bound

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class PodcastPlayerService : Service() {

    @Inject
    lateinit var exoPlayer: ExoPlayer

    private var podcastTitle = ""


    inner class Binder : android.os.Binder() {
        @OptIn(UnstableApi::class)
        suspend fun loadUri(uri: String, podcastTitle: String): Boolean {
            return suspendCoroutine { continuation ->
                this@PodcastPlayerService.podcastTitle = podcastTitle
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

        fun releasePlayer() {
            exoPlayer.release()
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }


}