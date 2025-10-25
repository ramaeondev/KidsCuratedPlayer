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
                val path = ThumbnailGenerator.getThumbnail(context, videoId, videoUri)
                thumbnailPath = path
            } catch (e: Exception) {
                println("❌ Error loading thumbnail: ${e.message}")
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
