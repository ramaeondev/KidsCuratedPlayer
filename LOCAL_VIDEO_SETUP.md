# Local Video Setup Guide

## 📱 How to Add Your Own Videos

The app plays **ALL videos from your device gallery** - no special folder needed!

### 🎯 Super Simple Setup

1. **Add videos to your phone** (any method):
   - Record with camera
   - Download from anywhere
   - Transfer via USB, Bluetooth, email, etc.
   - Already have videos? They'll show automatically!

2. **Install the app**
   - Download APK from GitHub releases

3. **Grant permission**
   - First launch asks for "Videos" access
   - Tap **Allow**

4. **Done!** 
   - App automatically shows ALL your videos
   - Portrait videos → Shorts tab
   - Landscape videos → Home tab

**No folders to create. No file renaming required!**

### 📝 Optional: Better Organization

Want to organize your videos with custom names? Use this format:

**Format:** `Title - Channel Name.mp4`

**Examples:**
```
Happy Birthday Song - Kids Rhymes.mp4
ABC Learning - Educational Channel.mp4
Dance Time - Fun Kids.mp4
```

The app will extract:
- **Title**: Shows as video title
- **Channel**: Groups videos by channel name

**Simple names work too:**
```
MyVideo.mp4
Birthday.mp4
```
These will show with channel name as "Gallery"

### 🎯 **Automatic Detection**

The app **automatically categorizes ALL your videos**:
- **Portrait/Vertical** (height > width, e.g., 1080x1920) → **Shorts tab**
- **Landscape/Horizontal** (width > height, e.g., 1920x1080) → **Home tab**

**Works with:**
- ✅ Videos you recorded on your phone
- ✅ Downloaded videos
- ✅ WhatsApp videos
- ✅ Instagram/TikTok downloaded content
- ✅ Any video in your gallery

**No folders. No renaming. Just works!**

### 📂 Where Videos Come From

The app scans **your entire device gallery** using Android MediaStore:
- ✅ Camera recordings
- ✅ Downloads folder
- ✅ WhatsApp/Telegram videos
- ✅ Any video accessible in Gallery app
- ✅ All folders with videos

**No specific folder required!**

### 🎬 Supported Video Formats

- ✅ `.mp4` (Recommended - best compatibility)
- ✅ `.mkv`
- ✅ `.avi`
- ✅ `.mov`
- ✅ `.3gp`
- ✅ `.webm`

### 📊 How Videos Are Categorized

The app automatically reads video properties:

| Video File | Resolution | Title | Channel | Goes To |
|------------|-----------|-------|---------|---------|
| `Happy Birthday - Kids.mp4` | 1920x1080 | Happy Birthday | Kids | Home (landscape) |
| `Dance Time - Fun.mp4` | 1080x1920 | Dance Time | Fun | Shorts (portrait) |
| `VID_20231015.mp4` | 1280x720 | VID_20231015 | Gallery | Home (landscape) |
| `MyVideo.mp4` | 1080x1920 | MyVideo | Gallery | Shorts (portrait) |

**It's all automatic - no manual work needed!**

### 🔄 Refreshing Video List

The app automatically scans for videos every 5 minutes. To force a refresh:
- Close and reopen the app
- Or wait 5 minutes after adding new videos

### ⚠️ Troubleshooting

**Videos not showing up?**
1. Make sure storage permission is granted
2. Check that videos play in your Gallery app first
3. Try restarting the app
4. Wait 5 minutes (cache refresh)

**Permission Denied?**
- When you first open the app, grant "Videos" or "Files and Media" permission
- On Android 13+: Settings > Apps > Kids Curated Player > Permissions > Videos → Allow

**Videos showing but won't play?**
- Make sure video codec is H.264 (most compatible)
- Try converting video to MP4 format
- Check file isn't corrupted

### 🎨 Tips for Best Experience

1. **Video Quality**:
   - 720p is good balance of quality and file size
   - 480p works fine for kids content
   - Phone camera videos work perfectly

2. **File Size**:
   - Keep videos under 100MB each for smooth playback
   - Shorter videos (2-5 min) work best for kids

3. **Organization** (optional):
   - Use `Title - Channel.mp4` naming for better organization
   - Group similar content with same channel name
   - Simple names like `birthday.mp4` work fine too

4. **Automatic Categorization**:
   - Portrait videos (selfie-style) → Shorts
   - Landscape videos (normal) → Home
   - No manual tagging needed!

### 📱 Storage Management

**Check Available Space:**
- Go to Settings > Storage
- The app shows ALL videos already in your gallery
- No extra storage needed!

**Example:**
- 10 videos already on phone = 0 MB extra
- Just install the app and it works with existing videos

###  Privacy & Security

✅ **All videos stay on your device** - nothing is uploaded
✅ **No internet required** - works 100% offline
✅ **No tracking** of what your child watches
✅ **Complete control** - only shows videos you already have
✅ **No cloud sync** - everything is local

---

## 📋 Quick Checklist

- [ ] Have some videos on your phone (camera, downloads, etc.)
- [ ] Installed the app
- [ ] Granted "Videos" permission
- [ ] Opened app and verified videos appear
- [ ] Tested video playback

## 🎉 You're All Set!

Your kids can now watch ALL your device videos in a clean, ad-free interface with automatic shorts/regular categorization!
