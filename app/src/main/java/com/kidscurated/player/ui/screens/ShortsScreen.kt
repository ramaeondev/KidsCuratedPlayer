package com.kidscurated.player.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun ShortsScreen(navController: NavController) {
    val shorts = VideoRepository.getAllShorts()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(shorts) { short ->
            ShortItem(video = short, onClick = {
                navController.navigate("video_player/${short.id}")
            })
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
        AsyncImage(
            model = video.thumbnailUrl,
            contentDescription = video.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
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
