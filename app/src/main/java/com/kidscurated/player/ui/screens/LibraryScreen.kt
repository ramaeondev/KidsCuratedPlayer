package com.kidscurated.player.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun LibraryScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Library",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(libraryItems) { item ->
            LibraryItem(icon = item.icon, title = item.title, subtitle = item.subtitle)
        }
    }
}

@Composable
fun LibraryItem(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go",
            tint = Color.Gray
        )
    }
}

data class LibraryItemData(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

val libraryItems = listOf(
    LibraryItemData(Icons.Default.VideoLibrary, "History", "Watch history"),
    LibraryItemData(Icons.Default.WatchLater, "Watch Later", "Saved videos"),
    LibraryItemData(Icons.Default.ThumbUp, "Liked Videos", "Your liked videos"),
    LibraryItemData(Icons.Default.Download, "Downloads", "Downloaded videos")
)
