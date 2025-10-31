package com.kidscurated.player.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ShortsScreen(navController: NavController) {
    var shorts by remember { mutableStateOf<List<Video>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // Track thumbnail generation progress
    val thumbnailProgress by com.kidscurated.player.data.ThumbnailGenerator.progress.collectAsState()
    
    // Progressive shorts loading - appear immediately as they're found
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                VideoRepository.getAllShortsProgressively().collect { shortsList ->
                    shorts = shortsList
                    errorMessage = null
                }
                
                // After flow completes, check if we actually got any shorts
                if (shorts.isEmpty()) {
                    errorMessage = "No shorts found in your gallery.\n\nAdd some portrait/vertical videos to your device."
                }
            } catch (e: Exception) {
                errorMessage = "Unable to load shorts.\n\nPlease ensure storage permission is granted."
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when {
            errorMessage != null && shorts.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎬",
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
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(shorts) { short ->
                        ShortItem(video = short, onClick = {
                            navController.navigate("shorts_player/${short.id}")
                        })
                    }
                }
                }
            }
        }
    }
}

@Composable
fun ShortItem(video: Video, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .clickable(onClick = onClick)
            .background(Color.Black)
    ) {
        // Thumbnail
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
                .size(80.dp),
            color = Color.Red.copy(alpha = 0.9f),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }
        
        // Gradient overlay at bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )
        
        // Right side action buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Like button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.DarkGray.copy(alpha = 0.5f)
                ) {
                    IconButton(onClick = { /* Like */ }) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Like",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = video.views.split(" ")[0],
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            
            // Dislike button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.DarkGray.copy(alpha = 0.5f)
                ) {
                    IconButton(onClick = { /* Dislike */ }) {
                        Icon(
                            imageVector = Icons.Default.ThumbDown,
                            contentDescription = "Dislike",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = "Dislike",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            
            // Comment button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.DarkGray.copy(alpha = 0.5f)
                ) {
                    IconButton(onClick = { /* Comments */ }) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = "Comment",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = "Comments",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            
            // Share button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.DarkGray.copy(alpha = 0.5f)
                ) {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = "Share",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        
        // Bottom video info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Red
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = video.channelName,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { /* Subscribe */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Subscribe", style = MaterialTheme.typography.labelMedium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = video.title,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
