# Kids Curated Player 📱👶

A YouTube-like Android app designed for children with **local video playback** from your device gallery. This app helps parents control what their children watch by automatically organizing videos from the gallery into regular videos and shorts based on resolution.

> **📚 NEW USER? Start with [INDEX.md](INDEX.md) for a complete guide to all documentation!**

> **⚡ QUICK START? Jump to [QUICK_START.md](QUICK_START.md) to build in 30 minutes!**

## ✨ Features

### 🎥 Video Management
- **Gallery-Wide Scanning**: Automatically scans ALL videos from device gallery
- **Smart Categorization**: Auto-detects shorts vs regular videos by aspect ratio
  - Portrait/Vertical videos (height > width) → Shorts tab
  - Landscape/Horizontal videos (width > height) → Home tab
- **Automatic Thumbnails**: Generates and caches thumbnails from video frames
- **Local Playback**: All videos play from device storage (no internet needed)

### 📱 User Interface
- **YouTube-like Interface**: Familiar UI similar to the official YouTube app
- **Vertical Shorts Scrolling**: TikTok-style swipe up/down for shorts navigation
- **Three Tabs**: Home (regular videos), Shorts, Library
- **Thumbnail Caching**: Fast loading with intelligent cache system
- **Page Indicators**: Shows position when browsing shorts (1/10, 2/10, etc.)

### 🛡️ Safety & Control
- **Curated Content**: Only shows videos from your device gallery
- **No Internet Browsing**: Cannot search or browse YouTube
- **No Login Required**: Simple, straightforward experience
- **Safe Environment**: No comments, no recommendations, no ads
- **Parental Control**: You control which videos are on the device

## 📥 Download & Install

### Latest Release (v1.0.0 Beta)
**Download APK**: 
- **Release** (Recommended): [KidsCuratedPlayer-v1.0.0-release.apk](KidsCuratedPlayer-v1.0.0-release.apk) (12MB)
- **Debug**: [KidsCuratedPlayer-v1.0.0-debug.apk](KidsCuratedPlayer-v1.0.0-debug.apk) (18MB)
- **Auto-Update**: [KidsCuratedPlayer-latest.apk](https://github.com/ramaeondev/KidsCuratedPlayer/releases/download/latest/KidsCuratedPlayer-latest.apk)

**What's New in v1.0.0**:
- ✅ First official beta release
- ✅ Professional launcher icon (red play button + gold star)
- ✅ Automatic thumbnail generation from video frames
- ✅ Vertical shorts scrolling (swipe up/down like TikTok)
- ✅ Thumbnail caching for instant loading
- ✅ Smart content:// URI handling for gallery videos
- ✅ Improved error handling and logging

**See**: [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) for detailed setup instructions

### Installation Steps:
1. Download the APK from the link above
2. Enable "Install from Unknown Sources" on your Android device
3. Open the downloaded APK and install
4. Grant storage permission when prompted
5. Videos from your gallery will appear automatically!

## 🎬 How It Works

### Automatic Video Detection
1. **App scans your entire gallery** using Android MediaStore
2. **Analyzes each video's resolution**:
   - Portrait (1080x1920, 720x1280, etc.) → Goes to Shorts
   - Landscape (1920x1080, 1280x720, etc.) → Goes to Home
3. **Generates thumbnails** from video frame at 1 second
4. **Caches thumbnails** for instant loading next time

### Manual Categorization (Optional)
Prefix video filename with `[Short]` to force it into Shorts tab:
- `[Short] Dance Video.mp4` → Always goes to Shorts
- `Learning ABC.mp4` → Auto-detected by resolution

### Thumbnail Generation
- Extracts frame at 1 second mark
- Resizes to 320x180 (maintains aspect ratio)
- Saves as JPEG in app cache (~5-15KB per thumbnail)
- Cache hit = instant loading (<10ms)
- First generation = ~100-200ms per video

## 🔧 Building the APK

### Prerequisites
- Android Studio (latest version recommended)
- JDK 21 or higher
- Android SDK (API 24-34)

### Quick Build Steps

1. **Clone Repository**
   ```bash
   git clone https://github.com/ramaeondev/KidsCuratedPlayer.git
   cd KidsCuratedPlayer
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `KidsCuratedPlayer` folder

3. **Sync Gradle**
   - Wait for Gradle to sync automatically
   - If prompted, accept any SDK licenses

4. **Build APK**
   ```bash
   ./gradlew assembleDebug
   ```
   Or click `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)` in Android Studio

5. **Find Your APK**
   - Location: `app/build/outputs/apk/debug/KidsCuratedPlayer-v11.0-debug.apk`
   - The APK is automatically named with version number

6. **Install on Device**
   - Enable "Install from Unknown Sources" on your Android device
   - Transfer the APK to your device via USB, email, or cloud storage
   - Open and install
   - Grant storage permission when app opens

### Automated Builds
GitHub Actions automatically builds and releases APKs on every push to main:
- **Debug APK**: Available as artifact in Actions
- **Public Release**: https://github.com/ramaeondev/KidsCuratedPlayer/releases/download/latest/KidsCuratedPlayer-latest.apk

## 🎨 Customization

### Change App Icon
The app now uses a YouTube-style red play button icon with a gold star (for kids).
To customize:
- Edit `app/src/main/res/drawable/ic_launcher_foreground.xml`
- Edit `app/src/main/res/drawable/ic_launcher_background.xml`
- Or use Android Studio's Image Asset tool: Right-click `res` → `New` → `Image Asset`

### Change App Name
- Edit `app/src/main/res/values/strings.xml`
- Change the `app_name` value to your preferred name

### Modify UI Colors
- Edit `app/src/main/java/com/kidscurated/player/ui/theme/Color.kt`
- Edit `app/src/main/java/com/kidscurated/player/ui/theme/Theme.kt`
- Current theme uses YouTube-like black background with red accents

### Video File Naming (Optional)
For better organization, name your video files:
- `Title - Channel Name.mp4` → Extracts title and channel
- `[Short] Dance Video - Fun Channel.mp4` → Forces into Shorts tab
- Otherwise, filename is used as title and "Gallery" as channel name

## 🛡️ Safety Features

- ✅ **No Internet Browsing**: Cannot search or browse YouTube/web
- ✅ **Gallery-Only Content**: Only plays videos from device storage
- ✅ **No Login Required**: No account, no data collection
- ✅ **No Comments**: No comment section or social features
- ✅ **No Recommendations**: No algorithm suggesting random videos
- ✅ **No Ads**: No advertisements or in-app purchases
- ✅ **Parental Control**: You control which videos are on the device
- ✅ **Navigation Blocking**: WebView blocks navigation to other videos

## 📊 Technical Details

### Core Technologies
- **Language**: Kotlin 1.9.0
- **UI Framework**: Jetpack Compose with Material Design 3
- **Video Player**: WebView with HTML5 video player (for local files)
- **Image Loading**: Coil 2.5.0
- **Thumbnail Generation**: MediaMetadataRetriever
- **Navigation**: Jetpack Navigation Compose
- **Gallery Access**: Android MediaStore API
- **Async Operations**: Kotlinx Coroutines

### System Requirements
- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Permissions**: READ_EXTERNAL_STORAGE (for gallery access)
- **Storage**: ~10-50MB (depends on number of videos and thumbnail cache)

### Architecture
- **MVVM Pattern**: ViewModel + Repository pattern
- **Pure Local**: No database, no network calls (except for thumbnail generation)
- **Caching**: Thumbnail cache in app cache directory
- **5-minute Cache**: Video list refreshes every 5 minutes for new gallery videos

## 🐛 Troubleshooting

### Thumbnails Not Showing
- **First time**: Wait 5-10 seconds for thumbnail generation
- **Check logs**: Run `adb logcat | grep ThumbnailGenerator` to see progress
- **Permission**: Ensure storage permission is granted
- **Cache**: Clear app cache if thumbnails are corrupted

### Videos Won't Play
- **Check permission**: App needs storage permission to access gallery
- **File format**: Supported formats: MP4, MKV, AVI, MOV, 3GP, WebM
- **Corrupted files**: Some video files may be corrupted or unsupported

### No Videos Showing
- **Empty gallery**: Add video files to your device gallery
- **Permission denied**: Grant storage permission in Android settings
- **Cache issue**: Force-stop app and reopen to trigger rescan

### App Crashes on Startup
- **Clean rebuild**: In Android Studio: `Build` → `Clean Project` → `Rebuild Project`
- **Clear data**: Uninstall and reinstall the app
- **Check logs**: Run `adb logcat | grep AndroidRuntime` to see crash logs

### Wrong Categorization (Shorts vs Regular)
- **Manual override**: Rename file with `[Short]` prefix
- **Check resolution**: Portrait videos go to Shorts, landscape to Home
- **Example**: `[Short] Video.mp4` forces into Shorts regardless of resolution

### Can't Install APK
- **Enable Unknown Sources**: Settings → Security → Install from Unknown Sources
- **File corrupted**: Re-download the APK
- **Insufficient storage**: Free up device space
- **Old version**: Uninstall old version first if updating

### Performance Issues
- **Large library**: 100+ videos may take time to scan and generate thumbnails
- **Cache size**: Check cache: Settings → Apps → Kids Curated Player → Storage
- **Clear cache**: Delete app cache to free space (thumbnails will regenerate)

## 📝 Version History

- **v1.0.0** (Current - Beta): First official beta release with all core features
- **v11.0** (Previous): Thumbnail generation + vertical shorts scrolling
- **v10.0**: Custom APK naming with app name
- **v9.0**: Resolution-based auto-detection for shorts
- **v8.0**: Gallery-wide scanning (removed folder restrictions)
- **v7.0**: Pure local video implementation
- **v6.0**: YouTube mobile URLs for playback
- **v5.0**: Navigation blocking to prevent YouTube leaks
- **v4.0**: Supabase integration (deprecated)
- **v3.0**: Initial local video support
- **v2.0**: Shorts tab implementation
- **v1.0**: Initial release with YouTube embeds

## 🤝 Contributing

This is a personal project for keeping kids away from YouTube Shorts addiction. If you'd like to contribute:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This is a personal project for private use. Please respect YouTube's Terms of Service and local copyright laws when using this app.

## ⚠️ Disclaimer

This app plays local video files from your device gallery. It's intended for personal, non-commercial use only. The app creator is not responsible for the content you choose to play. Make sure you have the right to use any content you store on your device.

## 🙏 Credits

Built to help parents provide a safe, controlled video experience for their children by using local videos instead of unrestricted YouTube access.

**Purpose**: Reduce YouTube Shorts addiction in children by providing a curated, offline video experience.

---

**⭐ Star this repo if you find it useful!**

**🐛 Found a bug?** [Open an issue](https://github.com/ramaeondev/KidsCuratedPlayer/issues)

**💬 Have questions?** [Start a discussion](https://github.com/ramaeondev/KidsCuratedPlayer/discussions)
