package com.kidscurated.player.player

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

/**
 * Singleton SimpleCache for media caching (HTTP video prefetch / fast start)
 */
object VideoCache {
    @Volatile private var cache: SimpleCache? = null

    fun initialize(context: Context) {
        if (cache != null) return
        synchronized(this) {
            if (cache == null) {
                val cacheDir = File(context.cacheDir, "media_cache")
                val evictor = LeastRecentlyUsedCacheEvictor(100L * 1024L * 1024L) // 100MB
                val dbProvider = ExoDatabaseProvider(context)
                cache = SimpleCache(cacheDir, evictor, dbProvider)
            }
        }
    }

    fun get(): SimpleCache {
        return checkNotNull(cache) { "VideoCache not initialized" }
    }
}
