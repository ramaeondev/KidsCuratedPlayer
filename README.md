# Kids Curated Player 📱👶

A YouTube-like Android app designed for children with curated, safe content. This app helps parents control what their children watch by providing a pre-configured feed of approved YouTube videos.

> **📚 NEW USER? Start with [INDEX.md](INDEX.md) for a complete guide to all documentation!**

> **⚡ QUICK START? Jump to [QUICK_START.md](QUICK_START.md) to build in 30 minutes!**

## Features

- **YouTube-like Interface**: Familiar UI similar to the official YouTube app
- **Curated Content**: Only shows videos you've pre-approved
- **No Login Required**: Simple, straightforward experience
- **Shorts Support**: Vertical short-form video feed
- **Safe Environment**: No comments, no recommendations, no ads
- **Offline-friendly**: Pre-configure videos for your child

## How to Configure Videos

1. Open `app/src/main/java/com/kidscurated/player/data/VideoRepository.kt`
2. Add your curated YouTube videos to the `curatedVideos` list
3. Add YouTube Shorts to the `curatedShorts` list

### Adding Videos

For each video, you need:
- **Video ID**: Extract from YouTube URL
  - From `https://www.youtube.com/watch?v=dQw4w9WgXcQ` → ID is `dQw4w9WgXcQ`
  - From `https://www.youtube.com/shorts/abc123` → ID is `abc123`
- **Title**: Video title
- **Channel Name**: Creator's channel name
- **Thumbnail URL**: Use `https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg`
- **Views, Upload Time, Duration**: Descriptive info

### Example:

```kotlin
Video(
    id = "dQw4w9WgXcQ",
    title = "Educational Video for Kids",
    channelName = "Kids Learning Channel",
    thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/maxresdefault.jpg",
    views = "1.2M views",
    uploadTime = "2 days ago",
    duration = "5:30",
    youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
)
```

## Building the APK

### Prerequisites
- Android Studio (latest version)
- JDK 8 or higher
- Android SDK

### Steps to Build

1. **Open Project in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `KidsCuratedPlayer` folder

2. **Sync Gradle**
   - Wait for Gradle to sync automatically
   - If prompted, accept any SDK licenses

3. **Build APK**
   - Click `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Or use terminal: `./gradlew assembleRelease`

4. **Find Your APK**
   - Location: `app/build/outputs/apk/debug/app-debug.apk`
   - For release: `app/build/outputs/apk/release/app-release.apk`

5. **Install on Device**
   - Enable "Install from Unknown Sources" on your Android device
   - Transfer the APK to your device
   - Open and install

## Customization

### Change App Icon
- Replace icons in `app/src/main/res/mipmap-*` folders
- Use Android Studio's Image Asset tool: Right-click `res` → `New` → `Image Asset`

### Change App Name
- Edit `app/src/main/res/values/strings.xml`
- Change the `app_name` value

### Modify UI Colors
- Edit `app/src/main/java/com/kidscurated/player/ui/theme/Color.kt`
- Edit `app/src/main/java/com/kidscurated/player/ui/theme/Theme.kt`

## Safety Features

- No login/authentication required
- No ability to browse or search YouTube directly
- No comments section
- No related video recommendations
- Only plays videos you've pre-approved
- Parental control through video curation

## Technical Details

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Video Player**: WebView with YouTube embed (no YouTube API key required)
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation Compose
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)

## Troubleshooting

### Videos won't play
- Ensure device has internet connection
- Check that video IDs are correct
- Some videos may have embedding disabled by creator

### App crashes on startup
- Check that all dependencies are properly synced
- Clean and rebuild project: `Build` → `Clean Project` then `Build` → `Rebuild Project`

### Can't install APK
- Enable "Install from Unknown Sources" in Android settings
- Check that the APK is not corrupted during transfer

## License

This is a personal project for private use. Please respect YouTube's Terms of Service when using this app.

## Disclaimer

This app embeds YouTube videos and is intended for personal, non-commercial use only. Make sure you have the right to show the content you curate. The app creator is not responsible for the content you choose to include.
