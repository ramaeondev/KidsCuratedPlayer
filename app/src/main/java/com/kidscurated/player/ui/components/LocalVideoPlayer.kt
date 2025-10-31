package com.kidscurated.player.ui.components

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ui.PlayerView
import com.kidscurated.player.player.PlayerManager

/**
 * Lightweight ExoPlayer-based local video player with instant start.
 */
@Composable
fun LocalVideoPlayer(
    videoUri: String,
    modifier: Modifier = Modifier
) {
    val player = PlayerManager.get()

    DisposableEffect(videoUri) {
        PlayerManager.setMediaAndPrepare(videoUri)
        player.playWhenReady = true
        onDispose {
            player.playWhenReady = false
        }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                useController = true
                this.player = PlayerManager.get()
            }
        },
        modifier = modifier
    )
}
