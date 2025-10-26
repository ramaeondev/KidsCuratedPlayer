package com.kidscurated.player.data

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Generates and caches video thumbnails with progress tracking
 */
object ThumbnailGenerator {
    
    private const val THUMBNAIL_WIDTH = 480 // Increased from 320
    private const val THUMBNAIL_HEIGHT = 270 // Increased from 180
    private const val THUMBNAIL_QUALITY = 95 // Increased from 80 for better quality
    
    // Progress tracking
    data class GenerationProgress(
        val total: Int = 0,
        val completed: Int = 0,
        val currentVideoId: String = "",
        val isComplete: Boolean = false
    ) {
        val percentage: Int get() = if (total > 0) (completed * 100 / total) else 0
    }
    
    private val _progress = MutableStateFlow(GenerationProgress())
    val progress: StateFlow<GenerationProgress> = _progress
    
    fun resetProgress() {
        _progress.value = GenerationProgress()
    }
    
    /**
     * Get or generate thumbnail for a video
     * Returns file path to cached thumbnail or null if failed
     */
    suspend fun getThumbnail(context: Context, videoId: String, videoUri: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Check if thumbnail already exists in cache
                val thumbnailFile = getThumbnailFile(context, videoId)
                if (thumbnailFile.exists()) {
                    return@withContext thumbnailFile.absolutePath
                }
                
                // Generate new thumbnail
                val bitmap = extractThumbnailFromVideo(context, videoUri)
                
                if (bitmap != null) {
                    // Save to cache
                    saveThumbnail(thumbnailFile, bitmap)
                    bitmap.recycle() // Free memory
                    return@withContext thumbnailFile.absolutePath
                } else {
                    return@withContext null
                }
                
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
    
    /**
     * Generate thumbnails for multiple videos with progress tracking
     */
    suspend fun generateThumbnailsWithProgress(
        context: Context,
        videos: List<Video>,
        onProgress: ((Int, Int) -> Unit)? = null
    ) {
        withContext(Dispatchers.IO) {
            // Initialize progress with total count
            _progress.value = GenerationProgress(total = videos.size, completed = 0)
            
            videos.forEachIndexed { index, video ->
                try {
                    // Update progress before processing
                    _progress.value = _progress.value.copy(
                        completed = index,
                        currentVideoId = video.id,
                        isComplete = false
                    )
                    onProgress?.invoke(index, videos.size)
                    
                    // Check if thumbnail already exists
                    val thumbnailFile = getThumbnailFile(context, video.id)
                    if (!thumbnailFile.exists()) {
                        // Only generate if not cached
                        getThumbnail(context, video.id, video.youtubeUrl)
                    }
                    // If cached, just skip (counts as completed)
                    
                } catch (e: Exception) {
                    // Continue with next video even if one fails
                }
            }
            
            // Mark as complete
            _progress.value = _progress.value.copy(
                completed = videos.size,
                isComplete = true
            )
            onProgress?.invoke(videos.size, videos.size)
        }
    }
    
    /**
     * Extract thumbnail bitmap from video at specified time
     */
    private fun extractThumbnailFromVideo(context: Context, videoUri: String): Bitmap? {
        var retriever: MediaMetadataRetriever? = null
        try {
            retriever = MediaMetadataRetriever()
            
            // Handle both content:// URIs and file:// paths
            if (videoUri.startsWith("content://")) {
                retriever.setDataSource(context, Uri.parse(videoUri))
            } else {
                retriever.setDataSource(videoUri)
            }
            
            // Try multiple time points to get the best frame
            // 1. Try at 2 seconds (better quality than first second)
            // 2. Try at 1 second as fallback
            // 3. Try at beginning as last resort
            val timePoints = listOf(2_000_000L, 1_000_000L, 0L) // In microseconds
            
            for (timePoint in timePoints) {
                val bitmap = retriever.getFrameAtTime(
                    timePoint,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                )
                
                if (bitmap != null) {
                    // Resize to thumbnail dimensions
                    return resizeBitmap(bitmap, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
                }
            }
            
            return null
            
        } catch (e: Exception) {
            return null
        } finally {
            try {
                retriever?.release()
            } catch (e: Exception) {
                // Ignore release errors
            }
        }
    }
    
    /**
     * Resize bitmap maintaining aspect ratio
     */
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        
        val aspectRatio = width.toFloat() / height.toFloat()
        val targetWidth: Int
        val targetHeight: Int
        
        if (width > height) {
            targetWidth = maxWidth
            targetHeight = (maxWidth / aspectRatio).toInt()
        } else {
            targetHeight = maxHeight
            targetWidth = (maxHeight * aspectRatio).toInt()
        }
        
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }
    
    /**
     * Save bitmap to file as JPEG
     */
    private fun saveThumbnail(file: File, bitmap: Bitmap) {
        file.parentFile?.mkdirs()
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, THUMBNAIL_QUALITY, out)
            out.flush()
        }
    }
    
    /**
     * Get thumbnail file path for a video ID
     */
    private fun getThumbnailFile(context: Context, videoId: String): File {
        val cacheDir = File(context.cacheDir, "thumbnails")
        cacheDir.mkdirs()
        // Use hash of video ID to avoid filename issues
        val fileName = "thumb_${videoId.hashCode()}.jpg"
        return File(cacheDir, fileName)
    }
    
    /**
     * Clear all cached thumbnails (for cleanup)
     */
    suspend fun clearCache(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val cacheDir = File(context.cacheDir, "thumbnails")
                if (cacheDir.exists()) {
                    cacheDir.listFiles()?.forEach { it.delete() }
                }
            } catch (e: Exception) {
                // Ignore errors
            }
        }
    }
    
    /**
     * Get cache size in MB
     */
    fun getCacheSize(context: Context): Float {
        val cacheDir = File(context.cacheDir, "thumbnails")
        if (!cacheDir.exists()) return 0f
        
        val totalSize = cacheDir.listFiles()?.sumOf { it.length() } ?: 0L
        return totalSize / (1024f * 1024f) // Convert to MB
    }
}
