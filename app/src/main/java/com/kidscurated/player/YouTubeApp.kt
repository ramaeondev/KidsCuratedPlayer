package com.kidscurated.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kidscurated.player.ui.screens.HomeScreen
import com.kidscurated.player.ui.screens.ShortsScreen
import com.kidscurated.player.ui.screens.ShortsPlayerScreen
import com.kidscurated.player.ui.screens.LibraryScreen
import com.kidscurated.player.ui.screens.VideoPlayerScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Shorts : Screen("shorts", "Shorts", Icons.Default.PlayArrow)
    object Library : Screen("library", "Library", Icons.Default.VideoLibrary)
    object VideoPlayer : Screen("video_player/{videoId}", "Video Player", Icons.Default.PlayArrow)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Shorts, Screen.Library)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != null && !currentRoute.startsWith("video_player")) {
                TopAppBar(
                    title = { 
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "YouTube",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "KidsTube",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { /* Search action */ }) {
                            Icon(Icons.Default.Search, "Search", tint = Color.White)
                        }
                        IconButton(onClick = { /* Notifications */ }) {
                            Icon(Icons.Default.Notifications, "Notifications", tint = Color.White)
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("video_player") && !currentRoute.startsWith("shorts_player")) {
                NavigationBar(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.DarkGray
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Shorts.route) {
                ShortsScreen(navController)
            }
            composable(Screen.Library.route) {
                LibraryScreen(navController)
            }
            composable("video_player/{videoId}") { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId")
                VideoPlayerScreen(videoId = videoId ?: "", navController = navController)
            }
            composable("shorts_player/{videoId}") { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId")
                ShortsPlayerScreen(videoId = videoId ?: "", navController = navController)
            }
        }
    }
}
