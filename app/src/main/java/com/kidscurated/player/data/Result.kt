package com.kidscurated.player.data

/**
 * Sealed class representing the result of an operation
 * 
 * Used throughout the app for proper error handling instead of returning
 * empty lists or null values.
 * 
 * Example usage:
 * ```
 * when (val result = videoRepository.getAllVideos()) {
 *     is Result.Success -> showVideos(result.data)
 *     is Result.Error -> showError(result.message)
 *     is Result.Loading -> showLoading()
 * }
 * ```
 */
sealed class Result<out T> {
    /**
     * Represents a successful operation with data
     */
    data class Success<out T>(val data: T) : Result<T>()
    
    /**
     * Represents a failed operation with error information
     */
    data class Error(
        val message: String,
        val throwable: Throwable? = null,
        val errorCode: Int? = null
    ) : Result<Nothing>()
    
    /**
     * Represents an operation in progress
     */
    object Loading : Result<Nothing>()
    
    /**
     * Checks if result is successful
     */
    val isSuccess: Boolean
        get() = this is Result.Success
    
    /**
     * Checks if result is an error
     */
    val isError: Boolean
        get() = this is Result.Error
    
    /**
     * Checks if result is loading
     */
    val isLoading: Boolean
        get() = this is Result.Loading
}

/**
 * Extension function to get data from Result or null
 */
fun <T> Result<T>.getDataOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

/**
 * Extension function to get error message or null
 */
fun <T> Result<T>.getErrorOrNull(): String? {
    return when (this) {
        is Result.Error -> message
        else -> null
    }
}


