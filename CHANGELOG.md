# YouKids Changelog

All notable changes to YouKids will be documented in this file.

## [2.0.0] - 2025-11-01

### 🚀 Major Changes

- Local videos only: removed YouTube/WebView/iframes entirely
- ExoPlayer-only playback with hardware acceleration enabled by default
- Progressive loading: videos and thumbnails appear as scanning progresses
- Smarter caching for thumbnails and video data
- Unified app icon/logo across app and metadata

### 🔧 Technical Notes

- Removed YouTube Android Player dependency and WebView-based playback
- Added global ExoPlayer with SimpleCache and OkHttp extension
- Coil configured with video frame extraction for thumbnails
- BuildConfig fields for Supabase moved to Gradle with safe defaults; guarded usage
- Version bump: versionCode 4, versionName 2.0.0

### 📄 Docs & CI

- Release notes template updated to reflect local-only playback
- Parent Safety Guide updated: internet not required for local playback

### ⚠️ Compatibility

- Only local file/content URIs are playable; remote links/YouTube are no longer supported


## [1.2.0] - 2024-10-28

### 🎯 Major Features

#### Supabase Analytics Backend
- **Integrated Supabase** for centralized analytics storage
- Anonymous user tracking with privacy compliance (COPPA/GDPR/CCPA)
- Real-time data collection to cloud database
- Tracks: unique installs, daily active users (DAU), monthly active users (MAU)
- Device information collection (legal data only):
  - Device type (e.g., "Samsung Galaxy S21")
  - Android version (e.g., "Android 13")
  - App version
  - Country code (from locale, not GPS)
- Database schema with 3 tables:
  - `user_installs` - Unique app installations
  - `app_opens` - DAU/MAU tracking
  - `analytics_events` - Custom event tracking
- Pre-built SQL views for easy analytics queries
- Row Level Security (RLS) for data protection

#### Fixed Permission Grant UX
- **Immediate video scan** after permission granted
- No need to close and reopen app anymore
- Better first-time user experience
- Automatic video loading on permission approval

#### Improved Video Categorization
- **Default unknown videos to regular videos** (not shorts)
- All videos now included in app
- No videos left behind due to aspect ratio detection issues
- Graceful fallback when metadata cannot be read

#### Auto-Refresh on Gallery Changes
- **Real-time detection** of new/deleted videos
- ContentObserver watches MediaStore for changes
- Automatic re-scan when gallery updated
- 2-second debounce to prevent excessive rescans
- No manual refresh needed

### 📝 Documentation

- Added `SUPABASE_SCHEMA.sql` - Complete database schema
- Added `SUPABASE_SETUP.md` - Step-by-step setup guide
- Includes sample analytics queries
- Security best practices documented
- Cost information (Free tier limits)

### 🔧 Technical Improvements

- Added Supabase Kotlin SDK dependencies
- Added Ktor client for HTTP requests
- Created `GalleryObserver.kt` for MediaStore monitoring
- Updated `Analytics.kt` with backend integration
- Coroutine scope for non-blocking analytics
- Improved error handling with silent failures

### 📦 Build Info

- **Version Code**: 3 (was 2)
- **Version Name**: 1.2.0 (was 1.1.0)
- **Debug APK**: 21MB (was 18MB) - +3MB due to Supabase SDK
- **Release APK**: 14MB (was 12MB) - +2MB due to Supabase SDK
- Build successful with Java 21 / Gradle 8.13

### 🔒 Privacy & Legal

- All data collection remains **COPPA/GDPR/CCPA compliant**
- Only legally obtainable data collected
- No personal information tracked
- No precise location (GPS) tracking
- No behavioral analytics
- User can request data deletion
- Privacy policy already in place

### 📊 New Capabilities

**For Developers:**
- Track unique user count
- Monitor daily/monthly active users
- View country distribution
- Analyze Android version usage
- Custom event tracking support

**Example Custom Events:**
```kotlin
// Track video play
Analytics.sendEvent("video_played", mapOf(
    "video_id" to video.id,
    "duration" to video.duration
))

// Track feature usage
Analytics.sendEvent("feature_used", mapOf(
    "feature_name" to "video_player"
))
```

### 🚀 Setup Required

**Important:** To enable analytics backend:
1. Create Supabase account (free tier)
2. Run `SUPABASE_SCHEMA.sql` in SQL Editor
3. Copy Project URL and anon key
4. Update `Analytics.kt` with your credentials
5. See `SUPABASE_SETUP.md` for full instructions

**Note:** App works without Supabase setup (local storage only)

---

## [1.1.0] - 2024-10-28

### 🎨 Major Rebrand

#### New Name: YouKids
- Renamed from "KidsCuratedPlayer" to "YouKids"
- More memorable and kid-friendly name
- YouTube-inspired branding

#### Professional Launcher Icon
- YouTube-style rounded rectangle design
- Red background (#FF0000)
- White play triangle (proper proportions)
- Gold "Y" accent (top-right corner)
- Small gold star (bottom-right corner)
- Consistent across all screen densities

#### Cleaner UI
- Removed search icon (not needed)
- Removed notifications icon (not applicable)
- Removed "More options" menu from video items
- Simplified top app bar
- Focus on core video playback experience

### 📊 Analytics Implementation

#### Local Analytics Tracking
- Anonymous user tracking (privacy-compliant)
- Uses Android ID + UUID for unique identification
- Tracks:
  - Install time
  - App opens count
  - Last open timestamp
- Resets on factory reset (privacy-friendly)
- No personal information collected
- COPPA/GDPR/CCPA compliant

### 📄 Privacy Policy

- Created comprehensive `PRIVACY_POLICY.md`
- Transparent about data collection
- User rights clearly stated
- COPPA compliance for children's app
- GDPR compliance for EU users
- CCPA compliance for California users

### 🐛 Bug Fixes

- **Fixed release APK installation** - APKs now install properly
- **Fixed Supabase errors** - Removed all Supabase error messages
- **Better error messages** - "No videos found in your gallery" (user-friendly)

### 🎯 Previous Improvements (from 1.0.0)

#### High-Quality Thumbnails
- Increased resolution from 320x180 to 480x270 (50% larger)
- Increased JPEG quality from 80% to 95%
- Better frame extraction algorithm
- Multiple fallback time points (2s, 1s, 0s)

#### Progress Indicators
- Real-time progress bars during thumbnail generation
- Shows percentage completed
- Added to all screens: Home, Shorts, Library
- Immediate thumbnail display (no waiting)

#### Performance Improvements
- Removed excessive logging
- Cleaned up debug statements
- Reduced console noise
- Better error handling

### 📦 Build Info

- **Version Code**: 2 (was 1)
- **Version Name**: 1.1.0 (was 1.0.0)
- **Debug APK**: 18MB
- **Release APK**: 12MB
- Both APKs signed with debug key (for testing)

---

## [1.0.0] - 2024-10-27

### 🎉 Initial Release

#### Core Features

- **Local Video Playback**
  - Scans all videos from device gallery
  - Automatic categorization (videos vs shorts)
  - Portrait videos → Shorts tab
  - Landscape videos → Home tab

- **Video Categorization**
  - Aspect ratio detection
  - Manual tagging with `[Short]` prefix
  - Metadata extraction (resolution, rotation)

- **Three Tabs**
  - **Home**: Regular landscape videos
  - **Shorts**: Vertical/portrait videos
  - **Library**: Both categories with filter tabs

- **Thumbnail Generation**
  - Automatic thumbnail creation
  - Cached to internal storage
  - Fast loading with Coil image library

- **Video Player**
  - ExoPlayer for local videos
  - YouTube player for YouTube videos (future)
  - Full-screen support
  - Standard playback controls

#### Technical Stack

- **Language**: Kotlin 1.9.0
- **UI**: Jetpack Compose + Material Design 3
- **Build**: Gradle 8.13
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Video Player**: ExoPlayer 2.19.1
- **Image Loading**: Coil 2.5.0
- **Coroutines**: kotlinx-coroutines 1.7.3

#### Permissions

- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_EXTERNAL_STORAGE` (Android 12 and below)

#### File Naming Convention

Supports two formats:
1. `Title - Channel Name.mp4`
2. `[Short] Title - Channel.mp4` (forced short)

#### Known Limitations

- No YouTube API integration yet
- Local videos only
- No cloud sync
- No favorites/playlists
- No search functionality

---

## Version History Summary

- **v1.2.0** (Current) - Supabase analytics + UX improvements
- **v1.1.0** - YouKids rebrand + local analytics
- **v1.0.0** - Initial release with local video playback

## Upgrade Path

### From 1.1.0 to 1.2.0

**What's New:**
- Supabase backend for analytics
- Immediate scan after permission
- Better video categorization
- Auto-refresh on gallery changes

**Breaking Changes:** None

**Migration Steps:** None required

### From 1.0.0 to 1.1.0

**What Changed:**
- App renamed to YouKids
- New launcher icon
- Simpler UI

**Breaking Changes:**
- App package name unchanged (no reinstall needed)
- All local data preserved

**Migration Steps:** None required

## Future Roadmap

### Planned for v1.3.0
- YouTube API integration
- Online video playback
- Search functionality
- Favorites/playlists
- Video download manager

### Planned for v2.0.0
- User accounts (optional)
- Cloud sync
- Parental controls
- Content filtering
- Watch history
- Recommendations

## Support

- **Issues**: https://github.com/ramaeondev/KidsCuratedPlayer/issues
- **Email**: [Your email]
- **Privacy**: See PRIVACY_POLICY.md
- **Setup**: See SUPABASE_SETUP.md (for analytics)
