package com.kidscurated.player.player

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.source.ProgressiveMediaSource

/**
 * Singleton ExoPlayer manager with tuned LoadControl and DataSource for
 * fast startup and hardware-accelerated playback.
 */
object PlayerManager {
    @Volatile private var player: ExoPlayer? = null

    fun initialize(context: Context) {
        if (player != null) return
        synchronized(this) {
            if (player == null) {
                val loadControl = DefaultLoadControl.Builder()
                    .setBufferDurationsMs(
                        /* minBufferMs = */ 5_000,
                        /* maxBufferMs = */ 15_000,
                        /* bufferForPlaybackMs = */ 250,
                        /* bufferForPlaybackAfterRebufferMs = */ 500
                    )
                    .build()

                val trackSelector = DefaultTrackSelector(context)

                // HTTP data source with user agent
                val httpFactory = DefaultHttpDataSource.Factory()
                    .setUserAgent(Util.getUserAgent(context, "YouKids"))
                    .setAllowCrossProtocolRedirects(true)

                // DataSource that handles file/content/http(s)
                val dataSourceFactory = DefaultDataSourceFactory(
                    context,
                    httpFactory
                )

                val mediaSourceFactory = ProgressiveMediaSource.Factory(
                    dataSourceFactory
                )

                player = ExoPlayer.Builder(context)
                    .setLoadControl(loadControl)
                    .setTrackSelector(trackSelector)
                    .setMediaSourceFactory(mediaSourceFactory)
                    .build()
            }
        }
    }

    fun get(): ExoPlayer {
        return checkNotNull(player) { "PlayerManager not initialized" }
    }

    fun setMediaAndPrepare(uri: String) {
        val p = get()
        val item = MediaItem.fromUri(uri)
        p.setMediaItem(item)
        p.prepare()
    }
}
