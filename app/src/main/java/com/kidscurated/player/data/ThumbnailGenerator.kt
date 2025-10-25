package com.kidscurated.player.data

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Generates and caches video thumbnails
 */
object ThumbnailGenerator {
    
    private const val THUMBNAIL_WIDTH = 320
    private const val THUMBNAIL_HEIGHT = 180
    private const val THUMBNAIL_QUALITY = 80 // JPEG quality 0-100
    
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
                    println("✅ Thumbnail cache hit: ${thumbnailFile.absolutePath}")
                    return@withContext thumbnailFile.absolutePath
                }
                
                // Generate new thumbnail
                println("🎬 Generating thumbnail for video: $videoId")
                val bitmap = extractThumbnailFromVideo(videoUri)
                
                if (bitmap != null) {
                    // Save to cache
                    saveThumbnail(thumbnailFile, bitmap)
                    bitmap.recycle() // Free memory
                    println("✅ Thumbnail saved: ${thumbnailFile.absolutePath}")
                    return@withContext thumbnailFile.absolutePath
                } else {
                    println("❌ Failed to extract thumbnail from video")
                    return@withContext null
                }
                
            } catch (e: Exception) {
                println("❌ Error generating thumbnail: ${e.message}")
                e.printStackTrace()
                return@withContext null
            }
        }
    }
    
    /**
     * Extract thumbnail bitmap from video at first frame
     */
    private fun extractThumbnailFromVideo(videoUri: String): Bitmap? {
        var retriever: MediaMetadataRetriever? = null
        try {
            retriever = MediaMetadataRetriever()
            retriever.setDataSource(videoUri)
            
            // Get frame at 1 second (better than first frame which might be black)
            val bitmap = retriever.getFrameAtTime(
                1_000_000, // 1 second in microseconds
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC
            )
            
            // Resize to thumbnail dimensions
            return bitmap?.let { resizeBitmap(it, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT) }
            
        } catch (e: Exception) {
            println("❌ Error extracting frame: ${e.message}")
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
                    println("🗑️ Cleared ${cacheDir.listFiles()?.size ?: 0} thumbnails from cache")
                }
            } catch (e: Exception) {
                println("❌ Error clearing thumbnail cache: ${e.message}")
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
