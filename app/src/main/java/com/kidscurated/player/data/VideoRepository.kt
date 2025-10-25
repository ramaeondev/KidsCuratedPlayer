package com.kidscurated.player.data

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
    val youtubeUrl: String,
    val isShort: Boolean = false
)

object VideoRepository {
    // Cache for fetched videos
    private var cachedVideos: List<Video>? = null
    private var cachedShorts: List<Video>? = null
    private var lastFetchTime: Long = 0
    private const val CACHE_DURATION = 5 * 60 * 1000 // 5 minutes
    
    // Fetch videos from Supabase
    suspend fun fetchVideosFromSupabase(): List<Video> {
        return withContext(Dispatchers.IO) {
            try {
                val supabaseVideos = RetrofitClient.supabaseService.getVideos()
                supabaseVideos.map { it.toVideo() }
            } catch (e: Exception) {
                e.printStackTrace()
                // Return empty list if API fails
                emptyList()
            }
        }
    }
    
    // Fetch only regular videos
    suspend fun fetchRegularVideosFromSupabase(): List<Video> {
        return withContext(Dispatchers.IO) {
            try {
                val supabaseVideos = RetrofitClient.supabaseService.getRegularVideos()
                supabaseVideos.map { it.toVideo() }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    
    // Fetch only shorts
    suspend fun fetchShortsFromSupabase(): List<Video> {
        return withContext(Dispatchers.IO) {
            try {
                val supabaseVideos = RetrofitClient.supabaseService.getShorts()
                supabaseVideos.map { it.toVideo() }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    
    // Get all videos with caching
    suspend fun getAllVideos(): List<Video> {
        val currentTime = System.currentTimeMillis()
        if (cachedVideos == null || currentTime - lastFetchTime > CACHE_DURATION) {
            cachedVideos = fetchRegularVideosFromSupabase()
            lastFetchTime = currentTime
        }
        return cachedVideos ?: emptyList()
    }
    
    // Get all shorts with caching
    suspend fun getAllShorts(): List<Video> {
        val currentTime = System.currentTimeMillis()
        if (cachedShorts == null || currentTime - lastFetchTime > CACHE_DURATION) {
            cachedShorts = fetchShortsFromSupabase()
            lastFetchTime = currentTime
        }
        return cachedShorts ?: emptyList()
    }
    
    // Get video by ID
    fun getVideoById(id: String): Video? {
        return cachedVideos?.find { it.id == id }
            ?: cachedShorts?.find { it.id == id }
    }
    
    // Clear cache (useful for refresh)
    fun clearCache() {
        cachedVideos = null
        cachedShorts = null
        lastFetchTime = 0
    }
}

// Extension function to convert Supabase model to app model
fun SupabaseVideo.toVideo(): Video {
    return Video(
        id = this.id,
        title = this.title,
        channelName = this.channelName,
        thumbnailUrl = this.thumbnailUrl,
        views = this.views,
        uploadTime = this.uploadTime,
        duration = this.duration,
        youtubeUrl = this.youtubeUrl,
        isShort = this.isShort
    )
}
