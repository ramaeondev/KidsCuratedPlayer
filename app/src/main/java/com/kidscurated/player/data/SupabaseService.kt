package com.kidscurated.player.data

import retrofit2.http.GET

interface SupabaseService {
    
    @GET("videos?select=*")
    suspend fun getVideos(): List<SupabaseVideo>
    
    @GET("videos?select=*&isShort=eq.false")
    suspend fun getRegularVideos(): List<SupabaseVideo>
    
    @GET("videos?select=*&isShort=eq.true")
    suspend fun getShorts(): List<SupabaseVideo>
}

data class SupabaseVideo(
    val id: String,
    val title: String,
    val channelName: String,
    val thumbnailUrl: String,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val youtubeUrl: String,
    val isShort: Boolean
)
