package com.kidscurated.player.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kidscurated.player.data.Video
import com.kidscurated.player.data.VideoRepository
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    var videos by remember { mutableStateOf<List<Video>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // Track thumbnail generation progress
    val thumbnailProgress by com.kidscurated.player.data.ThumbnailGenerator.progress.collectAsState()
    
    // Progressive video loading - videos appear immediately as they're found
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                VideoRepository.getAllVideosProgressively().collect { videoList ->
                    videos = videoList
                    isLoading = videoList.isEmpty() // Only show loading if no videos yet
                    errorMessage = null
                }
                
                // After flow completes, check if we actually got any videos
                if (videos.isEmpty()) {
                    errorMessage = "No videos found in your gallery.\n\nPlease add some videos to your device and reopen the app."
                }
            } catch (e: Exception) {
                errorMessage = "Unable to load videos.\n\nPlease ensure storage permission is granted."
                isLoading = false
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when {
            errorMessage != null && videos.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📱",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            else -> {
                Column {
                    // Show thumbnail generation progress if not complete
                    if (!thumbnailProgress.isComplete && thumbnailProgress.total > 0) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.DarkGray
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.Red,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Generating thumbnails... ${thumbnailProgress.percentage}%",
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = thumbnailProgress.percentage / 100f,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Red,
                                    trackColor = Color.Gray
                                )
                            }
                        }
                    }
                    
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(videos) { video ->
                        VideoItem(video = video, onClick = {
                            navController.navigate("video_player/${video.id}")
                        })
                    }
                }
                }
            }
        }
    }
}

@Composable
fun VideoItem(video: Video, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(bottom = 16.dp)
    ) {
        // Thumbnail with Play Button Overlay (YouTube Style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.DarkGray)
        ) {
            com.kidscurated.player.ui.components.VideoThumbnail(
                videoId = video.id,
                videoUri = video.youtubeUrl,
                contentDescription = video.title,
                modifier = Modifier.fillMaxSize()
            )
            
            // YouTube-style Play Button in Center
            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp),
                color = Color.Red.copy(alpha = 0.9f),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            
            // Duration badge
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = Color.Black.copy(alpha = 0.8f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = video.duration,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
        
        // Video details
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Channel avatar placeholder
            Surface(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(18.dp)),
                color = Color.Red
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Video info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = video.title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${video.channelName} • ${video.views} • ${video.uploadTime}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
