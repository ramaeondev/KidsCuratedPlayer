package com.kidscurated.player.data

import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Scans local storage for videos in the KidsVideos folder
 * 
 * Expected file naming format:
 * - "Title - Channel Name.mp4" → Title and Channel extracted, resolution auto-detected
 * - "[Short] Title - Channel.mp4" → Forced into Shorts tab regardless of resolution
 * 
 * Auto-detection:
 * - Portrait/Vertical videos (height > width) → Automatically classified as Shorts
 * - Landscape/Horizontal videos (width > height) → Automatically classified as Regular videos
 * 
 * Examples:
 * - "Happy Birthday Song - Kids Rhymes.mp4" (1920x1080) → Regular video (Home tab)
 * - "Dance Time - Fun Kids.mp4" (1080x1920) → Short video (Shorts tab)
 * - "[Short] ABC Song - Learning.mp4" (1920x1080) → Forced Short (Shorts tab)
 */
object LocalVideoScanner {
    
    private const val KIDS_VIDEOS_FOLDER = "KidsVideos"
    
    /**
     * Scans the device for ALL videos in gallery
     */
    suspend fun scanLocalVideos(context: Context): List<Video> {
        return withContext(Dispatchers.IO) {
            try {
                val videos = scanAllDeviceVideos(context)
                
                // Generate thumbnails in background with progress tracking
                if (videos.isNotEmpty()) {
                    ThumbnailGenerator.generateThumbnailsWithProgress(context, videos)
                }
                
                videos
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    
    /**
     * Scans the Movies/KidsVideos folder specifically
     */
    private fun scanKidsVideosFolder(context: Context): List<Video> {
        val videos = mutableListOf<Video>()
        
        // Try to find KidsVideos folder in common locations
        val possiblePaths = listOf(
            File(android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_MOVIES), KIDS_VIDEOS_FOLDER),
            File(android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DCIM), KIDS_VIDEOS_FOLDER),
            File(context.getExternalFilesDir(android.os.Environment.DIRECTORY_MOVIES), KIDS_VIDEOS_FOLDER)
        )
        
        for (folder in possiblePaths) {
            if (folder.exists() && folder.isDirectory) {
                val files = folder.listFiles { file ->
                    file.isFile && isVideoFile(file.name)
                }
                
                files?.forEach { file ->
                    val video = parseVideoFromFile(file)
                    if (video != null) {
                        videos.add(video)
                    }
                }
                
                if (videos.isNotEmpty()) {
                    break // Found videos, no need to check other locations
                }
            }
        }
        
        return videos
    }
    
    /**
     * Scans all videos on device using MediaStore
     */
    private fun scanAllDeviceVideos(context: Context): List<Video> {
        val videos = mutableListOf<Video>()
        val contentResolver: ContentResolver = context.contentResolver
        
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATA
        )
        
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"
        
        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val path = cursor.getString(pathColumn)
                
                // Include ALL videos from device gallery
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                
                val video = parseVideoFromMediaStore(contentUri, name, duration, path)
                if (video != null) {
                    videos.add(video)
                }
            }
        }
        
        return videos
    }
    
    /**
     * Parse video metadata from file
     * Auto-detects if it's a short based on aspect ratio (portrait = short)
     * Expected format: "Title - Channel Name.mp4" or "[Short] Title - Channel Name.mp4"
     */
    private fun parseVideoFromFile(file: File): Video? {
        val fileName = file.nameWithoutExtension
        
        // Check if explicitly marked as short in filename
        val explicitlyMarkedShort = fileName.startsWith("[Short]", ignoreCase = true)
        
        // Remove [Short] prefix if present
        val cleanName = if (explicitlyMarkedShort) {
            fileName.removePrefix("[Short]").trim()
        } else {
            fileName
        }
        
        // Split by " - " to separate title and channel
        val parts = cleanName.split(" - ")
        val title = parts.getOrNull(0)?.trim() ?: file.nameWithoutExtension
        val channelName = parts.getOrNull(1)?.trim() ?: "Local Videos"
        
        val uri = Uri.fromFile(file)
        
        // Detect if video is short based on resolution (portrait/vertical = short)
        val isShort = explicitlyMarkedShort || isPortraitVideo(file.absolutePath)
        
        return Video(
            id = "local_${file.name.hashCode()}",
            title = title,
            channelName = channelName,
            thumbnailUrl = "", // No thumbnail for local videos
            views = "",
            uploadTime = "",
            duration = formatDuration(file.length()),
            youtubeUrl = uri.toString(),
            isShort = isShort
        )
    }
    
    /**
     * Parse video from MediaStore query result
     * Auto-detects if it's a short based on aspect ratio
     */
    private fun parseVideoFromMediaStore(uri: Uri, displayName: String, durationMs: Long, path: String): Video? {
        val fileName = displayName.substringBeforeLast('.')
        
        // Check if explicitly marked as short in filename
        val explicitlyMarkedShort = fileName.startsWith("[Short]", ignoreCase = true)
        
        val cleanName = if (explicitlyMarkedShort) {
            fileName.removePrefix("[Short]").trim()
        } else {
            fileName
        }
        
        val parts = cleanName.split(" - ")
        val title = parts.getOrNull(0)?.trim() ?: displayName.substringBeforeLast('.')
        val channelName = parts.getOrNull(1)?.trim() ?: "Gallery"
        
        // Detect if video is short based on resolution (use file path for better compatibility)
        val isShort = explicitlyMarkedShort || isPortraitVideo(path)
        
        return Video(
            id = "local_${uri.hashCode()}",
            title = title,
            channelName = channelName,
            thumbnailUrl = "",
            views = "",
            uploadTime = "",
            duration = formatDuration(durationMs),
            youtubeUrl = uri.toString(),
            isShort = isShort
        )
    }
    
    /**
     * Check if file is a video
     */
    private fun isVideoFile(fileName: String): Boolean {
        val videoExtensions = listOf(".mp4", ".mkv", ".avi", ".mov", ".3gp", ".webm")
        return videoExtensions.any { fileName.lowercase().endsWith(it) }
    }
    
    /**
     * Format duration from milliseconds or file size
     */
    private fun formatDuration(value: Long): String {
        // If value is likely duration in milliseconds (< 24 hours)
        return if (value < 86400000) {
            val seconds = value / 1000
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            if (minutes > 0) {
                String.format("%d:%02d", minutes, remainingSeconds)
            } else {
                String.format("0:%02d", remainingSeconds)
            }
        } else {
            // Estimate based on file size (very rough)
            "~5:00"
        }
    }
    
    /**
     * Check if video is portrait/vertical (aspect ratio < 1 = portrait = short)
     * Returns true if height > width
     */
    private fun isPortraitVideo(videoPath: String): Boolean {
        var retriever: MediaMetadataRetriever? = null
        try {
            retriever = MediaMetadataRetriever()
            
            // Handle both file paths and content URIs
            if (videoPath.startsWith("content://") || videoPath.startsWith("file://")) {
                retriever.setDataSource(videoPath)
            } else {
                retriever.setDataSource(videoPath)
            }
            
            val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull() ?: 0
            val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull() ?: 0
            val rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)?.toIntOrNull() ?: 0
            
            // Adjust for rotation (90 or 270 degrees means width/height are swapped)
            val actualWidth = if (rotation == 90 || rotation == 270) height else width
            val actualHeight = if (rotation == 90 || rotation == 270) width else height
            
            if (actualWidth > 0 && actualHeight > 0) {
                val aspectRatio = actualWidth.toFloat() / actualHeight.toFloat()
                val isPortrait = aspectRatio < 1.0f
                
                println("📐 Video resolution: ${actualWidth}x${actualHeight}, aspect ratio: ${"%.2f".format(aspectRatio)}, is ${if (isPortrait) "PORTRAIT (Short)" else "LANDSCAPE (Regular)"}")
                
                return isPortrait
            }
            
            println("⚠️ Could not determine video resolution for: $videoPath")
            return false // Default to landscape if can't determine
            
        } catch (e: Exception) {
            println("❌ Error reading video metadata: ${e.message}")
            return false // Default to landscape on error
        } finally {
            try {
                retriever?.release()
            } catch (e: Exception) {
                // Ignore release errors
            }
        }
    }
}
