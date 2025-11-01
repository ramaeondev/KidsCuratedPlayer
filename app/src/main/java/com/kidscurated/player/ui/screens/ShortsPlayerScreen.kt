package com.kidscurated.player.ui.screens
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.kidscurated.player.data.VideoRepository
import com.kidscurated.player.ui.components.LocalVideoPlayer
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
                if (isLocalVideo) {
                    LocalVideoPlayer(
                        videoUri = videoUrl,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Non-local sources not supported
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Only local videos are supported",
                            color = Color.White
                        )
                    }
                }
                
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
