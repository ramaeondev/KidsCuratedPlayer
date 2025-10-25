package com.kidscurated.player.ui.screens

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.kidscurated.player.data.VideoRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShortsPlayerScreen(videoId: String, navController: NavController) {
    val context = LocalContext.current
    var allShorts by remember { mutableStateOf<List<com.kidscurated.player.data.Video>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    
    // Load all shorts
    LaunchedEffect(Unit) {
        scope.launch {
            allShorts = VideoRepository.getAllShorts()
            isLoading = false
        }
    }
    
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        }
        return
    }
    
    // Get current index
    val currentIndex = remember(allShorts) { 
        allShorts.indexOfFirst { it.id == videoId }.coerceAtLeast(0)
    }
    
    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { allShorts.size }
    )
    
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Back button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        
        // Vertical pager for shorts
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val short = allShorts[page]
            val videoUrl = short.youtubeUrl
            val isLocalVideo = videoUrl.startsWith("file://") || videoUrl.startsWith("content://")
            
            Box(modifier = Modifier.fillMaxSize()) {
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
                            settings.userAgentString = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"
                            
                            webChromeClient = WebChromeClient()
                            
                            // Block navigation to other videos
                            webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(
                                    view: WebView?,
                                    request: android.webkit.WebResourceRequest?
                                ): Boolean {
                                    val url = request?.url?.toString() ?: return false
                                    
                                    // For local videos, allow
                                    if (url.startsWith("file://") || url.startsWith("content://")) {
                                        return false
                                    }
                                    
                                    // For YouTube, only allow current video
                                    if (url.contains("youtube.com/shorts/${short.id}") ||
                                        url.contains("youtube.com/embed/${short.id}") ||
                                        url.contains("youtube.com/watch?v=${short.id}")) {
                                        return false
                                    }
                                    
                                    // Block any other navigation
                                    return true
                                }
                            }
                            
                            // Load video
                            if (isLocalVideo) {
                                // Local video - HTML5 player
                                val html = """
                                    <!DOCTYPE html>
                                    <html>
                                    <head>
                                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                        <style>
                                            * { margin: 0; padding: 0; }
                                            body { 
                                                background: black; 
                                                display: flex;
                                                align-items: center;
                                                justify-content: center;
                                                height: 100vh;
                                            }
                                            video { 
                                                width: 100%; 
                                                height: 100%; 
                                                object-fit: contain;
                                            }
                                        </style>
                                    </head>
                                    <body>
                                        <video controls autoplay playsinline controlsList="nodownload">
                                            <source src="$videoUrl" type="video/mp4">
                                        </video>
                                    </body>
                                    </html>
                                """.trimIndent()
                                loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
                            } else {
                                // YouTube short
                                loadUrl("https://m.youtube.com/shorts/${short.id}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                
                // Video info overlay at bottom
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = short.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        maxLines = 2
                    )
                    Text(
                        text = short.channelName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                
                // Page indicator
                Text(
                    text = "${page + 1} / ${allShorts.size}",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                )
            }
        }
    }
}
