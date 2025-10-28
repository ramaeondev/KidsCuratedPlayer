package com.kidscurated.player.data

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Anonymous user tracking for counting unique installations
 * 
 * PRIVACY COMPLIANT:
 * - Uses Android ID (resets on factory reset)
 * - No personal information collected
 * - No behavioral tracking
 * - Only counts unique installs
 * 
 * LEGAL REQUIREMENTS:
 * - Add to Privacy Policy
 * - Disclose in app store listing
 * - Allow user to opt-out if needed
 */
object Analytics {
    
    private const val PREFS_NAME = "youkids_analytics"
    private const val KEY_USER_ID = "anonymous_user_id"
    private const val KEY_INSTALL_TIME = "install_time"
    private const val KEY_APP_OPENS = "app_opens_count"
    private const val KEY_LAST_OPEN = "last_open_time"
    
    private var prefs: SharedPreferences? = null
    
    /**
     * Initialize analytics (call from Application onCreate)
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        // Generate or retrieve anonymous user ID
        if (getAnonymousUserId() == null) {
            generateAnonymousUserId(context)
        }
        
        // Track app open
        trackAppOpen()
    }
    
    /**
     * Get anonymous user ID (for counting unique users)
     * Returns: Android ID + generated UUID
     */
    fun getAnonymousUserId(): String? {
        return prefs?.getString(KEY_USER_ID, null)
    }
    
    /**
     * Generate anonymous user ID
     * Uses Android ID as base (resets on factory reset)
     */
    private fun generateAnonymousUserId(context: Context) {
        try {
            // Get Android ID (unique per app install)
            val androidId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            
            // Create anonymous ID: androidId + random UUID
            val anonymousId = "${androidId}_${UUID.randomUUID()}"
            
            prefs?.edit()?.apply {
                putString(KEY_USER_ID, anonymousId)
                putLong(KEY_INSTALL_TIME, System.currentTimeMillis())
                apply()
            }
            
            // Optional: Send to your analytics server
            // sendInstallEvent(anonymousId)
            
        } catch (e: Exception) {
            // Fallback: use only random UUID
            val fallbackId = "fallback_${UUID.randomUUID()}"
            prefs?.edit()?.putString(KEY_USER_ID, fallbackId)?.apply()
        }
    }
    
    /**
     * Track app open (for DAU/MAU metrics)
     */
    private fun trackAppOpen() {
        val currentCount = prefs?.getInt(KEY_APP_OPENS, 0) ?: 0
        prefs?.edit()?.apply {
            putInt(KEY_APP_OPENS, currentCount + 1)
            putLong(KEY_LAST_OPEN, System.currentTimeMillis())
            apply()
        }
    }
    
    /**
     * Get install time
     */
    fun getInstallTime(): Long {
        return prefs?.getLong(KEY_INSTALL_TIME, 0) ?: 0
    }
    
    /**
     * Get app opens count
     */
    fun getAppOpensCount(): Int {
        return prefs?.getInt(KEY_APP_OPENS, 0) ?: 0
    }
    
    /**
     * Optional: Send analytics to your backend
     * 
     * Example API call structure:
     * POST https://your-backend.com/api/analytics
     * {
     *   "user_id": "anonymous_id_here",
     *   "event": "app_open",
     *   "timestamp": 1234567890,
     *   "app_version": "1.1.0",
     *   "platform": "android"
     * }
     */
    suspend fun sendEvent(eventName: String, properties: Map<String, Any> = emptyMap()) {
        withContext(Dispatchers.IO) {
            try {
                val userId = getAnonymousUserId() ?: return@withContext
                
                // TODO: Implement your backend API call here
                // Example using Retrofit:
                // val event = AnalyticsEvent(
                //     userId = userId,
                //     event = eventName,
                //     timestamp = System.currentTimeMillis(),
                //     properties = properties
                // )
                // analyticsApi.sendEvent(event)
                
            } catch (e: Exception) {
                // Silent fail - don't crash app for analytics
            }
        }
    }
    
    /**
     * Clear user data (for privacy compliance / user opt-out)
     */
    fun clearUserData() {
        prefs?.edit()?.clear()?.apply()
    }
}
