package com.amozeshgam.amozeshgam.handler.exoplayerHandler.view

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoLink: String = "",
    controller: (exoPlayer: ExoPlayer) -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    exoPlayer.setMediaItem(MediaItem.fromUri(videoLink.toUri()))
    val playerView = PlayerView(context)
    playerView.player = exoPlayer
    playerView.controllerHideOnTouch = true
    controller(exoPlayer)
    AndroidView(modifier = modifier, factory = {
        playerView
    })
}
