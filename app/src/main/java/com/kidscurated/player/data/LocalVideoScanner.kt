package com.kidscurated.player.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Scans local storage for videos in the KidsVideos folder
 * 
 * Expected file naming format:
 * - Regular videos: "Title - Channel Name.mp4"
 * - Short videos: "[Short] Title - Channel Name.mp4"
 * 
 * Example:
 * - "Happy Birthday Song - Kids Rhymes.mp4" -> Regular video
 * - "[Short] ABC Song - Kids Learning.mp4" -> Short video
 */
object LocalVideoScanner {
    
    private const val KIDS_VIDEOS_FOLDER = "KidsVideos"
    
    /**
     * Scans the device for videos in specific folders
     */
    suspend fun scanLocalVideos(context: Context): List<Video> {
        return withContext(Dispatchers.IO) {
            try {
                println("📁 Scanning local storage for videos...")
                val videos = mutableListOf<Video>()
                
                // Method 1: Scan specific folder if exists
                val folderVideos = scanKidsVideosFolder(context)
                videos.addAll(folderVideos)
                
                // Method 2: Query MediaStore for all videos (fallback)
                if (videos.isEmpty()) {
                    println("📁 No videos in KidsVideos folder, scanning all device videos...")
                    val allVideos = scanAllDeviceVideos(context)
                    videos.addAll(allVideos)
                }
                
                println("✅ Found ${videos.size} local videos")
                videos
            } catch (e: Exception) {
                println("❌ Error scanning local videos: ${e.message}")
                e.printStackTrace()
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
                println("📂 Found KidsVideos folder at: ${folder.absolutePath}")
                val files = folder.listFiles { file ->
                    file.isFile && isVideoFile(file.name)
                }
                
                files?.forEach { file ->
                    val video = parseVideoFromFile(file)
                    if (video != null) {
                        videos.add(video)
                        println("  ✓ Added: ${video.title}")
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
                
                // Only include videos in KidsVideos folder or with specific naming
                if (path.contains(KIDS_VIDEOS_FOLDER, ignoreCase = true) || 
                    name.matches(Regex(".*\\[Short\\].*|.*\\d+.*rhyme.*|.*song.*", RegexOption.IGNORE_CASE))) {
                    
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    
                    val video = parseVideoFromMediaStore(contentUri, name, duration)
                    if (video != null) {
                        videos.add(video)
                        println("  ✓ Added from MediaStore: ${video.title}")
                    }
                }
            }
        }
        
        return videos
    }
    
    /**
     * Parse video metadata from file
     * Expected format: "[Short] Title - Channel Name.mp4"
     */
    private fun parseVideoFromFile(file: File): Video? {
        val fileName = file.nameWithoutExtension
        val isShort = fileName.startsWith("[Short]", ignoreCase = true)
        
        // Remove [Short] prefix if present
        val cleanName = if (isShort) {
            fileName.removePrefix("[Short]").trim()
        } else {
            fileName
        }
        
        // Split by " - " to separate title and channel
        val parts = cleanName.split(" - ")
        val title = parts.getOrNull(0)?.trim() ?: file.nameWithoutExtension
        val channelName = parts.getOrNull(1)?.trim() ?: "Local Videos"
        
        val uri = Uri.fromFile(file)
        
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
     */
    private fun parseVideoFromMediaStore(uri: Uri, displayName: String, durationMs: Long): Video? {
        val fileName = displayName.substringBeforeLast('.')
        val isShort = fileName.startsWith("[Short]", ignoreCase = true)
        
        val cleanName = if (isShort) {
            fileName.removePrefix("[Short]").trim()
        } else {
            fileName
        }
        
        val parts = cleanName.split(" - ")
        val title = parts.getOrNull(0)?.trim() ?: displayName
        val channelName = parts.getOrNull(1)?.trim() ?: "Local Videos"
        
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
}
