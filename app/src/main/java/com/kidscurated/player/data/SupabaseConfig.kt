package com.kidscurated.player.data

import com.kidscurated.player.BuildConfig

/**
 * Supabase configuration
 * 
 * NOTE: Credentials are loaded from BuildConfig (compiled into APK)
 * Supabase anon keys are public by design - safe to be in client apps
 * Defaults are in build.gradle, can override via local.properties for development
 */
object SupabaseConfig {
    // Supabase project credentials (loaded from BuildConfig - compiled into APK)
    val SUPABASE_URL: String = BuildConfig.SUPABASE_URL
    val SUPABASE_ANON_KEY: String = BuildConfig.SUPABASE_ANON_KEY
    
    // Check if Supabase is configured
    val isConfigured: Boolean
        get() = SUPABASE_URL.isNotEmpty() && SUPABASE_ANON_KEY.isNotEmpty()
    
    // Table name in Supabase
    const val TABLE_NAME = "videos"
}
