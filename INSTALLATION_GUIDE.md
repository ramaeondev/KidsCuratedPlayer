# 📱 Kids Curated Player v1.0.0 - Installation Guide

## Quick Install (3 Steps)

### 1️⃣ Download
Choose one of the APK files:

**Recommended for Installation**:
- **`KidsCuratedPlayer-v1.0.0-release.apk`** (12MB)
  - Smaller size
  - Better performance
  - Production-ready

**For Development/Testing**:
- **`KidsCuratedPlayer-v1.0.0-debug.apk`** (18MB)
  - Includes debug info
  - Larger file size
  - Better error messages

### 2️⃣ Enable Installation from Unknown Sources

**Android 8.0 and above**:
1. Go to **Settings** → **Apps & notifications**
2. Tap **Advanced** → **Special app access**
3. Tap **Install unknown apps**
4. Select your file manager or browser
5. Turn on **Allow from this source**

**Android 7.0-7.1**:
1. Go to **Settings** → **Security**
2. Enable **Unknown sources**
3. Tap **OK** to confirm

### 3️⃣ Install

1. Open the downloaded APK file
2. Tap **Install**
3. Wait for installation to complete
4. Tap **Open** or find the app icon on your home screen
5. **Grant storage permission** when prompted (required to access videos)

---

## ✅ Verification

After installation, you should see:
- **App Icon**: Red play button with gold star
- **App Name**: "Kids Curated Player"
- **First Launch**: Permission request for storage access

---

## 🎬 First Time Setup

### Grant Permission
On first launch, the app will ask for storage permission:
1. Tap **Allow** to grant access to photos and videos
2. This is required for the app to scan your gallery

### Adding Videos
The app automatically scans your device gallery:
- **Place videos** in any folder on your device
- The app will **auto-detect** and categorize them
- **Portrait videos** → Shorts tab
- **Landscape videos** → Home tab

### Manual Categorization (Optional)
Force a video into Shorts tab by renaming:
- `[Short] Video Name.mp4` → Always goes to Shorts
- Works regardless of video resolution

---

## 📂 Where to Place Videos

Videos can be in any of these locations:
- `/storage/emulated/0/DCIM/` (Camera folder)
- `/storage/emulated/0/Movies/` (Movies folder)
- `/storage/emulated/0/Download/` (Download folder)
- Any custom folder in your gallery

The app scans **ALL video files** on your device automatically.

---

## 🎯 Supported Video Formats

- ✅ MP4 (most common)
- ✅ MKV
- ✅ AVI
- ✅ MOV
- ✅ 3GP
- ✅ WebM

---

## 🔧 Troubleshooting

### Videos Not Showing
**Problem**: App shows no videos
**Solutions**:
1. Grant storage permission in Settings → Apps → Kids Curated Player → Permissions
2. Ensure videos are in a gallery-accessible folder
3. Force-stop app and reopen to trigger rescan
4. Check that video files are not corrupted

### Thumbnails Not Loading
**Problem**: Video cards show loading spinner forever
**Solutions**:
1. Wait 5-10 seconds for first-time generation
2. Check logcat: `adb logcat | grep ThumbnailGenerator`
3. Clear app cache: Settings → Apps → Kids Curated Player → Storage → Clear Cache
4. Restart the app

### Permission Denied
**Problem**: Storage permission keeps getting denied
**Solutions**:
1. Go to Settings → Apps → Kids Curated Player → Permissions
2. Manually enable Storage permission
3. On Android 11+, enable "All files access" if needed
4. Restart the app after granting permission

### App Crashes
**Problem**: App crashes on startup or during use
**Solutions**:
1. Uninstall and reinstall the app
2. Clear app data before reinstalling
3. Check Android version (minimum Android 7.0)
4. Report crash with logcat output

### Wrong Categorization
**Problem**: Landscape video goes to Shorts or vice versa
**Solutions**:
1. Rename file with `[Short]` prefix to force into Shorts
2. Check video resolution (portrait = shorts, landscape = home)
3. Some videos may have incorrect metadata

---

## 📱 System Requirements

### Minimum Requirements
- **Android Version**: 7.0 Nougat (API 24) or higher
- **Storage**: 50MB free space
- **RAM**: 2GB recommended
- **Screen**: Any size (optimized for phones)

### Recommended
- **Android Version**: 10.0 or higher
- **Storage**: 100MB+ (for thumbnail cache)
- **RAM**: 4GB+
- **Screen**: 5.5" or larger

---

## 🔒 Privacy & Security

### What the App Does
- ✅ Scans local videos from your gallery
- ✅ Generates and caches thumbnails
- ✅ Plays videos from local storage

### What the App Does NOT Do
- ❌ No internet connection required
- ❌ No data sent to external servers
- ❌ No analytics or tracking
- ❌ No personal information collected
- ❌ No access to contacts, calls, or messages

### Permissions Required
- **Storage (READ_EXTERNAL_STORAGE)**: To access and display your videos
  - This is the ONLY permission required
  - Cannot write or delete files
  - Only reads video files

---

## 🆕 What's New in v1.0.0

This is the first official beta release! Features include:

- 🎥 Local video playback from device gallery
- 🖼️ Automatic thumbnail generation with caching
- 📱 Vertical shorts scrolling (TikTok-style)
- 🎯 Smart categorization by aspect ratio
- 🎨 Professional YouTube-style launcher icon
- 🛡️ Safe environment with navigation blocking
- 📊 Material Design 3 UI

---

## 💡 Tips & Tricks

### For Best Experience
1. **Organize videos** in folders for easier management
2. **Name files clearly**: "Title - Channel Name.mp4"
3. **Use [Short] prefix** for manual categorization
4. **Keep videos under 50MB** for smooth playback
5. **Clear thumbnail cache** if it gets too large

### Managing Storage
- App cache: ~5-15KB per video thumbnail
- For 100 videos: ~0.5-1.5MB cache
- Clear cache: Settings → Apps → Kids Curated Player → Storage → Clear Cache
- Thumbnails regenerate automatically

### Content Management
- Add videos: Copy to device gallery
- Remove videos: Delete from gallery (app auto-updates)
- Favorites: Use device's favorite/star feature
- Playlists: Organize in gallery folders

---

## 📞 Support & Feedback

### Get Help
- **GitHub Issues**: https://github.com/ramaeondev/KidsCuratedPlayer/issues
- **Documentation**: See README.md in repository
- **Release Notes**: RELEASE_NOTES_v1.0.0.md

### Report Bugs
Include in your report:
1. Android version
2. Device model
3. Steps to reproduce
4. Error message (if any)
5. Screenshot (if applicable)

### Feature Requests
- **GitHub Discussions**: https://github.com/ramaeondev/KidsCuratedPlayer/discussions
- Describe your use case
- Explain why it would be useful

---

## ⚠️ Important Notes

### Beta Software
- This is BETA software and may contain bugs
- Test with non-critical content first
- Keep backups of important videos
- Report any issues you encounter

### Legal
- **Personal Use Only**: Not for commercial distribution
- **No Warranty**: Provided as-is
- **Content Responsibility**: You are responsible for content on your device
- **Open Source**: Code available on GitHub

---

## 🎉 Enjoy!

You're all set! Open the app and start watching videos safely.

**Questions?** Check the README.md or open an issue on GitHub.

**Happy Viewing! 🎬**

---

*Kids Curated Player v1.0.0 Beta*  
*October 26, 2025*
