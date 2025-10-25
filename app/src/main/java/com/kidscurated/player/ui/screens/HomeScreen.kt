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
    
    // Fetch videos on first composition
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                println("🏠 HomeScreen: Fetching videos...")
                videos = VideoRepository.getAllVideos()
                println("🏠 HomeScreen: Received ${videos.size} videos")
                if (videos.isEmpty()) {
                    errorMessage = "No videos found. Please check:\n1. Supabase table created?\n2. RLS policies enabled?\n3. Data inserted?"
                } else {
                    errorMessage = null
                }
            } catch (e: Exception) {
                println("🏠 HomeScreen: Error - ${e.message}")
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
            errorMessage != null -> {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
            else -> {
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
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = video.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
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
            
            IconButton(onClick = { /* Menu */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
        }
    }
}
