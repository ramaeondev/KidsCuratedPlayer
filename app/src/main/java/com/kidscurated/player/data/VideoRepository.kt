package com.kidscurated.player.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
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
    
    // Gallery observer for auto-refresh
    private var galleryObserver: GalleryObserver? = null
    
    fun init(context: Context) {
        appContext = context.applicationContext
        startObservingGallery()
    }
    
    private fun startObservingGallery() {
        val context = appContext ?: return
        
        galleryObserver = GalleryObserver(context) {
            // Auto-refresh when gallery changes
            refreshVideos()
        }
        galleryObserver?.register()
    }
    
    fun stopObservingGallery() {
        galleryObserver?.unregister()
        galleryObserver = null
    }
    
    // Force refresh videos (clears cache and rescans)
    suspend fun refreshVideos() {
        clearCache()
        scanLocalVideos()
    }
    
    // Scan local storage for videos
    suspend fun scanLocalVideos(): List<Video> {
        return when (val result = scanLocalVideosResult()) {
            is Result.Success -> result.data
            is Result.Error -> emptyList() // Backward compatibility
            is Result.Loading -> emptyList()
        }
    }
    
    // Scan local storage for videos with proper error handling
    suspend fun scanLocalVideosResult(): Result<List<Video>> {
        val context = appContext ?: return Result.Error(
            message = "VideoRepository not initialized. Call init() first.",
            errorCode = -1
        )
        return withContext(Dispatchers.IO) {
            try {
                val videos = LocalVideoScanner.scanLocalVideos(context)
                // Update cache immediately after scan
                cachedVideos = videos.filter { !it.isShort }
                cachedShorts = videos.filter { it.isShort }
                lastScanTime = System.currentTimeMillis()
                Result.Success(videos)
            } catch (e: SecurityException) {
                Result.Error(
                    message = "Storage permission denied. Please grant permission to access videos.",
                    throwable = e,
                    errorCode = 403
                )
            } catch (e: Exception) {
                Result.Error(
                    message = "Failed to scan videos: ${e.message ?: "Unknown error"}",
                    throwable = e,
                    errorCode = -1
                )
            }
        }
    }
    
    // Get all videos with caching
    suspend fun getAllVideos(): List<Video> {
        return when (val result = getAllVideosResult()) {
            is Result.Success -> result.data
            is Result.Error -> emptyList() // Backward compatibility
            is Result.Loading -> emptyList()
        }
    }
    
    // Get all videos with proper error handling
    suspend fun getAllVideosResult(): Result<List<Video>> {
        val currentTime = System.currentTimeMillis()
        if (cachedVideos == null || currentTime - lastScanTime > CACHE_DURATION) {
            return scanLocalVideosResult().also { result ->
                if (result is Result.Success) {
                    val allVideos = result.data
                    cachedVideos = allVideos.filter { !it.isShort }
                    cachedShorts = allVideos.filter { it.isShort }
                    lastScanTime = currentTime
                }
            }
        }
        return Result.Success(cachedVideos ?: emptyList())
    }
    
    // Progressive video loading - emits videos as they're found
    fun getAllVideosProgressively(): Flow<List<Video>> = callbackFlow {
        val context = appContext ?: throw IllegalStateException("VideoRepository not initialized")
        
        // Emit cached videos immediately if available
        cachedVideos?.let { 
            trySend(it)
        }
        
        // Then scan for new videos progressively
        val videos = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            LocalVideoScanner.scanLocalVideosProgressively(context) { video ->
                if (!video.isShort) {
                    videos.add(video)
                    trySend(videos.toList()) // Emit updated list immediately
                }
            }
        }
        // Update cache after scan completes and close the channel
        cachedVideos = videos
        lastScanTime = System.currentTimeMillis()
        close()
        awaitClose { }
    }.flowOn(Dispatchers.IO)
    
    // Get all shorts with caching
    suspend fun getAllShorts(): List<Video> {
        return when (val result = getAllShortsResult()) {
            is Result.Success -> result.data
            is Result.Error -> emptyList() // Backward compatibility
            is Result.Loading -> emptyList()
        }
    }
    
    // Get all shorts with proper error handling
    suspend fun getAllShortsResult(): Result<List<Video>> {
        val currentTime = System.currentTimeMillis()
        if (cachedShorts == null || currentTime - lastScanTime > CACHE_DURATION) {
            return scanLocalVideosResult().also { result ->
                if (result is Result.Success) {
                    val allVideos = result.data
                    cachedVideos = allVideos.filter { !it.isShort }
                    cachedShorts = allVideos.filter { it.isShort }
                    lastScanTime = currentTime
                }
            }
        }
        return Result.Success(cachedShorts ?: emptyList())
    }
    
    // Progressive shorts loading - emits videos as they're found
    fun getAllShortsProgressively(): Flow<List<Video>> = callbackFlow {
        val context = appContext ?: throw IllegalStateException("VideoRepository not initialized")
        
        // Emit cached shorts immediately if available
        cachedShorts?.let { 
            trySend(it)
        }
        
        // Then scan for new videos progressively
        val shorts = mutableListOf<Video>()
        withContext(Dispatchers.IO) {
            LocalVideoScanner.scanLocalVideosProgressively(context) { video ->
                if (video.isShort) {
                    shorts.add(video)
                    trySend(shorts.toList()) // Emit updated list immediately
                }
            }
        }
        // Update cache after scan completes and close the channel
        cachedShorts = shorts
        lastScanTime = System.currentTimeMillis()
        close()
        awaitClose { }
    }.flowOn(Dispatchers.IO)
    
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
