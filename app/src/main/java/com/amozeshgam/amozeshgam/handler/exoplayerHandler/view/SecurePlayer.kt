package com.amozeshgam.amozeshgam.handler.exoplayerHandler.view

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.amozeshgam.amozeshgam.handler.exoplayerHandler.datasource.AmozeshgamDataSourceFactory

@OptIn(UnstableApi::class)
@Composable
fun ViewSecurePlayer(
    modifier: Modifier = Modifier,
    videoLink: String = "",
    key: ByteArray,
    iv: ByteArray,
    controller: (exoPlayer: ExoPlayer) -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val dataSourceFactory = AmozeshgamDataSourceFactory(key = key, iv = iv)
    val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(MediaItem.fromUri(videoLink))
    exoPlayer.setMediaSource(mediaSource)
    val playerView = PlayerView(context)
    playerView.player = exoPlayer
    playerView.controllerHideOnTouch = true
    controller(exoPlayer)
    AndroidView(modifier = modifier, factory = {
        playerView
    })
}