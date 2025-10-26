package com.kidscurated.player.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kidscurated.player.data.Video
import com.kidscurated.player.data.VideoRepository
import kotlinx.coroutines.launch

@Composable
fun LibraryScreen(navController: NavController? = null) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Videos", "Shorts")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header
        Text(
            text = "Library",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
        
        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Black,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color.Red
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { 
                        Text(
                            text = title,
                            color = if (selectedTab == index) Color.White else Color.Gray
                        ) 
                    }
                )
            }
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> VideosLibrary(navController)
            1 -> ShortsLibrary(navController)
        }
    }
}

@Composable
fun VideosLibrary(navController: NavController?) {
    var videos by remember { mutableStateOf<List<Video>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // Track thumbnail generation progress
    val thumbnailProgress by com.kidscurated.player.data.ThumbnailGenerator.progress.collectAsState()
    
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                videos = VideoRepository.getAllVideos()
                if (videos.isEmpty()) {
                    errorMessage = "No videos found in your gallery"
                }
            } catch (e: Exception) {
                errorMessage = "Unable to load videos"
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
                    text = errorMessage ?: "",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
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
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(videos) { video ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController?.navigate("video_player/${video.id}")
                    }
            ) {
                // Thumbnail with play button
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray)
                ) {
                    com.kidscurated.player.ui.components.VideoThumbnail(
                        videoId = video.id,
                        videoUri = video.youtubeUrl,
                        contentDescription = video.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Play button overlay
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp),
                        color = Color.Red.copy(alpha = 0.9f),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                    
                    // Duration
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp),
                        color = Color.Black.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(
                            text = video.duration,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Video info
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(90.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = video.title,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = video.channelName,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = "${video.views} • ${video.uploadTime}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
                }
            }
        }
    }
}

@Composable
fun ShortsLibrary(navController: NavController?) {
    var shorts by remember { mutableStateOf<List<Video>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // Track thumbnail generation progress
    val thumbnailProgress by com.kidscurated.player.data.ThumbnailGenerator.progress.collectAsState()
    
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                shorts = VideoRepository.getAllShorts()
                if (shorts.isEmpty()) {
                    errorMessage = "No shorts found in your gallery"
                }
            } catch (e: Exception) {
                errorMessage = "Unable to load shorts"
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
                    text = errorMessage ?: "",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
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
                    
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shorts) { short ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        navController?.navigate("video_player/${short.id}")
                    }
            ) {
                com.kidscurated.player.ui.components.VideoThumbnail(
                    videoId = short.id,
                    videoUri = short.youtubeUrl,
                    contentDescription = short.title,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Play button
                Surface(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(56.dp),
                    color = Color.Red.copy(alpha = 0.9f),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp)
                    )
                }
                
                // Title at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = short.title,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = short.views,
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
                }
            }
        }
    }
}
