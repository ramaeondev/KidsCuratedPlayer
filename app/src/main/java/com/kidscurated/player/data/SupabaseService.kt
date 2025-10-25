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
    val channelname: String,  // Changed from channelName to match DB column
    val thumbnailurl: String,  // Changed from thumbnailUrl to match DB column
    val views: String,
    val uploadtime: String,  // Changed from uploadTime to match DB column
    val duration: String,
    val youtubeurl: String,  // Changed from youtubeUrl to match DB column
    val isshort: Boolean  // Changed from isShort to match DB column
)
