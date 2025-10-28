package com.kidscurated.player.data

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Observes changes to the MediaStore video database
 * Automatically triggers video re-scan when videos are added or removed
 */
class GalleryObserver(
    private val context: Context,
    private val onGalleryChanged: suspend () -> Unit
) : ContentObserver(Handler(Looper.getMainLooper())) {
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var lastChangeTime = 0L
    private val DEBOUNCE_DELAY = 2000L // Wait 2 seconds before rescanning
    
    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        
        val currentTime = System.currentTimeMillis()
        
        // Debounce rapid changes (e.g., multiple videos added at once)
        if (currentTime - lastChangeTime < DEBOUNCE_DELAY) {
            return
        }
        
        lastChangeTime = currentTime
        
        println("📸 Gallery change detected: $uri")
        
        // Trigger rescan after a short delay to let MediaStore finish updating
        scope.launch {
            delay(1000) // Wait 1 second for MediaStore to fully update
            println("🔄 Rescanning videos due to gallery change...")
            onGalleryChanged()
        }
    }
    
    fun register() {
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            this
        )
        println("👁️ Gallery observer registered - watching for video changes")
    }
    
    fun unregister() {
        context.contentResolver.unregisterContentObserver(this)
        println("👁️ Gallery observer unregistered")
    }
}
