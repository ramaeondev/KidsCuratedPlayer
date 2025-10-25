package com.kidscurated.player.ui.screens

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.kidscurated.player.data.VideoRepository

@Composable
fun VideoPlayerScreen(videoId: String, navController: NavController) {
    val video = VideoRepository.getVideoById(videoId)
    val context = LocalContext.current
    val isShort = video?.isShort == true
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Back button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        
        // YouTube Player - Full screen for shorts, 16:9 for regular videos
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isShort) {
                        Modifier.fillMaxHeight()  // Full screen for shorts
                    } else {
                        Modifier.aspectRatio(16f / 9f)  // 16:9 for regular videos
                    }
                )
                .background(Color.Black)
        ) {
            AndroidView(
                factory = {
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.mediaPlaybackRequiresUserGesture = false
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        
                        // Use mobile user agent for all videos to fix playback issues
                        settings.userAgentString = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"
                        
                        webChromeClient = WebChromeClient()
                        
                        // Custom WebViewClient to prevent navigation away from our video
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: android.webkit.WebResourceRequest?
                            ): Boolean {
                                val url = request?.url?.toString() ?: return false
                                
                                // Allow loading our specific video URLs
                                if (url.contains("youtube.com/shorts/$videoId") ||
                                    url.contains("youtube.com/embed/$videoId") ||
                                    url.contains("youtube.com/watch?v=$videoId")) {
                                    return false // Allow loading
                                }
                                
                                // Block any other navigation (prevents scrolling to other YouTube videos)
                                return true // Block loading
                            }
                        }
                        
                        // For shorts, use the mobile YouTube URL; for videos, try mobile watch URL
                        if (isShort) {
                            loadUrl("https://m.youtube.com/shorts/$videoId")
                        } else {
                            // Use mobile watch URL instead of embed to fix Error Code 15
                            loadUrl("https://m.youtube.com/watch?v=$videoId")
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Video details - Only show for regular videos, not shorts
        if (!isShort) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black)
            ) {
                item {
                    // Video title and info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = video?.title ?: "Video Title",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "${video?.views ?: "0 views"} • ${video?.uploadTime ?: ""}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionButton(icon = Icons.Default.ThumbUp, text = "Like")
                        ActionButton(icon = Icons.Default.ThumbDown, text = "Dislike")
                        ActionButton(icon = Icons.Default.Share, text = "Share")
                        ActionButton(icon = Icons.Default.Download, text = "Download")
                    }
                    
                    Divider(
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    // Channel info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                            color = Color.Red
                        ) {
                            Box(modifier = Modifier.fillMaxSize())
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = video?.channelName ?: "Channel Name",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "1M subscribers",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        
                        Button(
                            onClick = { /* Subscribe */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text("Subscribe")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Description
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.DarkGray.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Description",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "This is a curated educational video for kids. Safe and age-appropriate content.",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Comments are disabled for safety",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            }
        }
    }
}

@Composable
fun ActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { /* Action */ }) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.White
            )
        }
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
