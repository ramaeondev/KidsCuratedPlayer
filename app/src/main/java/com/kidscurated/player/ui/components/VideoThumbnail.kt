package com.kidscurated.player.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kidscurated.player.data.ThumbnailGenerator
import kotlinx.coroutines.launch
import java.io.File

/**
 * Video thumbnail loader that automatically generates and caches thumbnails
 * for local videos using ThumbnailGenerator
 */
@Composable
fun VideoThumbnail(
    videoId: String,
    videoUri: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    var thumbnailPath by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    
    // Load or generate thumbnail
    LaunchedEffect(videoId, videoUri) {
        scope.launch {
            isLoading = true
            try {
                println("🖼️ VideoThumbnail: Loading thumbnail for $videoId")
                val path = ThumbnailGenerator.getThumbnail(context, videoId, videoUri)
                if (path != null) {
                    println("✅ VideoThumbnail: Got thumbnail path: $path")
                    val file = File(path)
                    if (file.exists()) {
                        println("✅ VideoThumbnail: File exists: ${file.length()} bytes")
                        thumbnailPath = path
                    } else {
                        println("❌ VideoThumbnail: File doesn't exist: $path")
                    }
                } else {
                    println("❌ VideoThumbnail: No thumbnail path returned")
                }
            } catch (e: Exception) {
                println("❌ VideoThumbnail: Error loading thumbnail: ${e.message}")
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
    
    Box(
        modifier = modifier.background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            thumbnailPath != null -> {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(File(thumbnailPath!!))
                        .crossfade(true)
                        .build(),
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }
            else -> {
                // Placeholder if thumbnail generation failed
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                )
            }
        }
    }
}
