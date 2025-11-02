package com.kidscurated.player.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import com.kidscurated.player.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.util.UUID

/**
 * Anonymous user tracking with Supabase backend
 * 
 * PRIVACY COMPLIANT - LEGAL DATA ONLY:
 * ✅ Anonymous User ID (Android ID + UUID)
 * ✅ Install timestamp
 * ✅ App opens count
 * ✅ Device type (Manufacturer + Model, e.g., "Samsung Galaxy S21")
 * ✅ Android version (e.g., "Android 13")
 * ✅ App version (e.g., "1.1.0")
 * ✅ Country code (from system locale, e.g., "US")
 * 
 * ❌ NO personal information
 * ❌ NO precise location (GPS)
 * ❌ NO behavioral tracking
 * ❌ NO contact list access
 * ❌ NO COPPA violations
 */
object Analytics {
    
    private const val PREFS_NAME = "youkids_analytics"
    private const val KEY_USER_ID = "anonymous_user_id"
    private const val KEY_INSTALL_TIME = "install_time"
    private const val KEY_APP_OPENS = "app_opens_count"
    private const val KEY_LAST_OPEN = "last_open_time"
    
    // Credentials loaded from BuildConfig (compiled into APK)
    // Supabase anon keys are public by design - safe to be in client apps
    private val SUPABASE_URL: String = BuildConfig.SUPABASE_URL
    private val SUPABASE_KEY: String = BuildConfig.SUPABASE_ANON_KEY
    
    private var prefs: SharedPreferences? = null
    private var appContext: Context? = null
    
    // Coroutine scope for analytics operations
    private val analyticsScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    // Lazy initialization of Supabase client (only if credentials are configured)
    private val supabase by lazy {
        if (SUPABASE_URL.isNotEmpty() && SUPABASE_KEY.isNotEmpty()) {
            createSupabaseClient(
                supabaseUrl = SUPABASE_URL,
                supabaseKey = SUPABASE_KEY
            ) {
                install(Postgrest)
            }
        } else {
            null // Analytics disabled if credentials not configured
        }
    }
    
    /**
     * Initialize analytics (call from Application onCreate)
     */
    fun init(context: Context) {
        appContext = context.applicationContext
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        // Ensure we have a device-bound anonymous user ID.
        // If app data was restored from another device (Android Auto Backup),
        // the stored ID may belong to a different device. In that case,
        // regenerate using this device's ANDROID_ID so each device is unique.
        ensureDeviceBoundAnonymousId(context)
        
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
            
            val installTime = System.currentTimeMillis()
            
            prefs?.edit()?.apply {
                putString(KEY_USER_ID, anonymousId)
                putLong(KEY_INSTALL_TIME, installTime)
                apply()
            }
            
            // Send install event to Supabase
            analyticsScope.launch {
                sendInstallEvent(anonymousId, installTime)
            }
            
        } catch (e: Exception) {
            // Fallback: use only random UUID
            val fallbackId = "fallback_${UUID.randomUUID()}"
            prefs?.edit()?.putString(KEY_USER_ID, fallbackId)?.apply()
        }
    }

    /**
     * Ensure stored anonymous ID matches the current device's ANDROID_ID prefix.
     * If preferences were restored from a different device, the stored ID will
     * have another device's ANDROID_ID. Regenerate in that case so each device
     * contributes a distinct user_id in Supabase.
     */
    private fun ensureDeviceBoundAnonymousId(context: Context) {
        val stored = getAnonymousUserId()
        val currentAndroidId = try {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: ""
        } catch (e: Exception) {
            ""
        }

        // If no ID yet, or restored ID doesn't belong to this device, generate anew
        val expectedPrefix = if (currentAndroidId.isNotEmpty()) "${currentAndroidId}_" else null
        val needsRegeneration = when {
            stored == null -> true
            expectedPrefix == null -> false // can't validate, keep existing
            !stored.startsWith(expectedPrefix) -> true
            else -> false
        }

        if (needsRegeneration) {
            generateAnonymousUserId(context)
        }
    }
    
    /**
     * Track app open (for DAU/MAU metrics)
     */
    private fun trackAppOpen() {
        val currentCount = prefs?.getInt(KEY_APP_OPENS, 0) ?: 0
        val newCount = currentCount + 1
        
        prefs?.edit()?.apply {
            putInt(KEY_APP_OPENS, newCount)
            putLong(KEY_LAST_OPEN, System.currentTimeMillis())
            apply()
        }
        
        // Send app open event to Supabase
        analyticsScope.launch {
            sendAppOpenEvent(newCount)
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
     * Get device information (legally obtainable data only)
     */
    private fun getDeviceInfo(): DeviceInfo {
        val context = appContext ?: throw IllegalStateException("Analytics not initialized")
        
        return DeviceInfo(
            deviceType = "${Build.MANUFACTURER} ${Build.MODEL}",
            androidVersion = "Android ${Build.VERSION.RELEASE}",
            appVersion = BuildConfig.APP_VERSION_NAME,
            countryCode = context.resources.configuration.locales[0].country
        )
    }
    
    /**
     * Send install event to Supabase
     */
    private suspend fun sendInstallEvent(userId: String, installTime: Long) {
        withContext(Dispatchers.IO) {
            try {
                if (supabase == null || SUPABASE_URL.isEmpty() || SUPABASE_KEY.isEmpty()) {
                    println("⚠️ Supabase not configured - skipping analytics upload")
                    return@withContext
                }
                
                val deviceInfo = getDeviceInfo()
                
                val installData = UserInstall(
                    userId = userId,
                    installTime = installTime,
                    deviceType = deviceInfo.deviceType,
                    androidVersion = deviceInfo.androidVersion,
                    appVersion = deviceInfo.appVersion,
                    countryCode = deviceInfo.countryCode
                )
                
                supabase?.from("user_installs")?.insert(installData)
                println("✅ Install event sent to Supabase")
                
            } catch (e: Exception) {
                println("❌ Error sending install event: ${e.message}")
            }
        }
    }
    
    /**
     * Send app open event to Supabase
     */
    private suspend fun sendAppOpenEvent(openCount: Int) {
        withContext(Dispatchers.IO) {
            try {
                if (supabase == null || SUPABASE_URL.isEmpty() || SUPABASE_KEY.isEmpty()) {
                    return@withContext
                }
                
                val userId = getAnonymousUserId() ?: return@withContext
                val deviceInfo = getDeviceInfo()
                
                val openData = AppOpen(
                    userId = userId,
                    timestamp = System.currentTimeMillis(),
                    openCount = openCount,
                    deviceType = deviceInfo.deviceType,
                    androidVersion = deviceInfo.androidVersion,
                    appVersion = deviceInfo.appVersion
                )
                
                supabase?.from("app_opens")?.insert(openData)
                
            } catch (e: Exception) {
                println("❌ Error sending app open event: ${e.message}")
            }
        }
    }
    
    /**
     * Send custom analytics event to Supabase
     * 
     * Example usage:
     * Analytics.sendEvent("video_played", mapOf("video_id" to "123", "duration" to 120))
     */
    suspend fun sendEvent(eventName: String, properties: Map<String, String> = emptyMap()) {
        withContext(Dispatchers.IO) {
            try {
                if (supabase == null || SUPABASE_URL.isEmpty() || SUPABASE_KEY.isEmpty()) {
                    return@withContext
                }
                
                val userId = getAnonymousUserId() ?: return@withContext
                val deviceInfo = getDeviceInfo()
                
                val eventData = AnalyticsEvent(
                    userId = userId,
                    eventName = eventName,
                    timestamp = System.currentTimeMillis(),
                    properties = properties,
                    deviceType = deviceInfo.deviceType,
                    androidVersion = deviceInfo.androidVersion,
                    appVersion = deviceInfo.appVersion
                )
                
                supabase?.from("analytics_events")?.insert(eventData)
                
            } catch (e: Exception) {
                println("❌ Error sending event: ${e.message}")
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

// Data models for Supabase
@Serializable
private data class DeviceInfo(
    val deviceType: String,
    val androidVersion: String,
    val appVersion: String,
    val countryCode: String
)

@Serializable
private data class UserInstall(
    @SerialName("user_id") val userId: String,
    @SerialName("install_time") val installTime: Long,
    @SerialName("device_type") val deviceType: String,
    @SerialName("android_version") val androidVersion: String,
    @SerialName("app_version") val appVersion: String,
    @SerialName("country_code") val countryCode: String
)

@Serializable
private data class AppOpen(
    @SerialName("user_id") val userId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("open_count") val openCount: Int,
    @SerialName("device_type") val deviceType: String,
    @SerialName("android_version") val androidVersion: String,
    @SerialName("app_version") val appVersion: String
)

@Serializable
private data class AnalyticsEvent(
    @SerialName("user_id") val userId: String,
    @SerialName("event_name") val eventName: String,
    @SerialName("timestamp") val timestamp: Long,
    val properties: Map<String, String>,
    @SerialName("device_type") val deviceType: String,
    @SerialName("android_version") val androidVersion: String,
    @SerialName("app_version") val appVersion: String
)
