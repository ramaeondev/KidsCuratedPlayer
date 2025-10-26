package com.kidscurated.player.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Video(
    val id: String,
    val title: String,
    val channelName: String,
    val thumbnailUrl: String,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val youtubeUrl: String, // For local videos, this will be the file URI
    val isShort: Boolean = false
)

object VideoRepository {
    // Cache for scanned videos
    private var cachedVideos: List<Video>? = null
    private var cachedShorts: List<Video>? = null
    private var lastScanTime: Long = 0
    private const val CACHE_DURATION = 5 * 60 * 1000 // 5 minutes
    
    // Application context (set from Application class)
    private var appContext: Context? = null
    
    fun init(context: Context) {
        appContext = context.applicationContext
    }
    
    // Scan local storage for videos
    suspend fun scanLocalVideos(): List<Video> {
        val context = appContext ?: throw IllegalStateException("VideoRepository not initialized")
        return withContext(Dispatchers.IO) {
            try {
                val videos = LocalVideoScanner.scanLocalVideos(context)
                videos
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    
    // Get all videos with caching
    suspend fun getAllVideos(): List<Video> {
        val currentTime = System.currentTimeMillis()
        if (cachedVideos == null || currentTime - lastScanTime > CACHE_DURATION) {
            val allVideos = scanLocalVideos()
            cachedVideos = allVideos.filter { !it.isShort }
            cachedShorts = allVideos.filter { it.isShort }
            lastScanTime = currentTime
        }
        return cachedVideos ?: emptyList()
    }
    
    // Get all shorts with caching
    suspend fun getAllShorts(): List<Video> {
        val currentTime = System.currentTimeMillis()
        if (cachedShorts == null || currentTime - lastScanTime > CACHE_DURATION) {
            val allVideos = scanLocalVideos()
            cachedVideos = allVideos.filter { !it.isShort }
            cachedShorts = allVideos.filter { it.isShort }
            lastScanTime = currentTime
        }
        return cachedShorts ?: emptyList()
    }
    
    // Get video by ID
    fun getVideoById(videoId: String): Video? {
        val allVideos = (cachedVideos ?: emptyList()) + (cachedShorts ?: emptyList())
        return allVideos.find { it.id == videoId }
    }
    
    // Clear cache to force rescan
    fun clearCache() {
        cachedVideos = null
        cachedShorts = null
        lastScanTime = 0
    }
}
