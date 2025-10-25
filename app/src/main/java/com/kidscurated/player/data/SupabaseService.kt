package com.kidscurated.player.data

import retrofit2.http.GET

interface SupabaseService {
    
    @GET("videos?select=*&isskipped=eq.false")  // Only get non-skipped videos
    suspend fun getVideos(): List<SupabaseVideo>
    
    @GET("videos?select=*&isshort=eq.false&isskipped=eq.false")  // Only non-skipped regular videos
    suspend fun getRegularVideos(): List<SupabaseVideo>
    
    @GET("videos?select=*&isshort=eq.true&isskipped=eq.false")  // Only non-skipped shorts
    suspend fun getShorts(): List<SupabaseVideo>
}

data class SupabaseVideo(
    val id: String,
    val title: String,
    val channelname: String,
    val thumbnailurl: String,
    val views: String,
    val uploadtime: String,
    val duration: String,
    val youtubeurl: String,
    val isshort: Boolean,
    val isskipped: Boolean = false  // Added isskipped field
)
