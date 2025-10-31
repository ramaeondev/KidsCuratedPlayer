package com.kidscurated.player

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.kidscurated.player.data.Analytics
import com.kidscurated.player.data.VideoRepository
import com.kidscurated.player.player.PlayerManager
import com.kidscurated.player.player.VideoCache

class KidsCuratedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize VideoRepository with application context
        VideoRepository.init(this)
        
        // Initialize anonymous analytics (privacy-compliant)
        Analytics.init(this)

        // Initialize shared video cache (for remote video prefetch/fast start)
        VideoCache.initialize(this)

        // Initialize a warm ExoPlayer instance
        PlayerManager.initialize(this)

        // Configure a global Coil ImageLoader with tuned memory/disk cache
        val imageLoader = ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .memoryCache(
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // up to 25% of app memory for images
                    .build()
            )
            .diskCache(
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(150L * 1024L * 1024L) // 150MB
                    .build()
            )
            .build()
        Coil.setImageLoader(imageLoader)
    }
}
