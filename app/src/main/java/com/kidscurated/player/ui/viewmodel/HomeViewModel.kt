package com.kidscurated.player.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kidscurated.player.data.Result
import com.kidscurated.player.data.Video
import com.kidscurated.player.data.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for HomeScreen
 * 
 * This demonstrates the MVVM pattern:
 * - Manages UI state
 * - Handles business logic
 * - Provides reactive data through StateFlow
 * 
 * Usage in Compose:
 * ```
 * val viewModel: HomeViewModel = viewModel()
 * val uiState by viewModel.uiState.collectAsState()
 * 
 * when (uiState) {
 *     is HomeUiState.Loading -> LoadingScreen()
 *     is HomeUiState.Success -> VideoList(uiState.videos)
 *     is HomeUiState.Error -> ErrorScreen(uiState.message, onRetry = viewModel::loadVideos)
 * }
 * ```
 */
class HomeViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadVideos()
    }
    
    /**
     * Load videos from repository
     */
    fun loadVideos() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            
            when (val result = VideoRepository.getAllVideosResult()) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        _uiState.value = HomeUiState.Error(
                            message = "No videos found in your gallery.\n\nPlease add some videos to your device and reopen the app.",
                            isRetryable = true
                        )
                    } else {
                        _uiState.value = HomeUiState.Success(result.data)
                    }
                }
                is Result.Error -> {
                    _uiState.value = HomeUiState.Error(
                        message = result.message,
                        isRetryable = true
                    )
                }
                is Result.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
            }
        }
    }
    
    /**
     * Refresh videos (force rescan)
     */
    fun refreshVideos() {
        viewModelScope.launch {
            VideoRepository.refreshVideos()
            loadVideos()
        }
    }
}

/**
 * UI State for HomeScreen
 * 
 * Sealed class representing all possible states:
 * - Loading: Videos are being loaded
 * - Success: Videos loaded successfully
 * - Error: An error occurred with error message and retry option
 */
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val videos: List<Video>) : HomeUiState()
    data class Error(
        val message: String,
        val isRetryable: Boolean = true
    ) : HomeUiState()
}


