package com.kidscurated.player

import android.app.Application
import com.kidscurated.player.data.VideoRepository

class KidsCuratedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize VideoRepository with application context
        VideoRepository.init(this)
    }
}
