# 🎬 Kids Curated Player - Quick Start

## ✅ What Changed

**Your app now works 100% offline with local videos from your device!**

- ❌ No more YouTube dependency
- ❌ No more Supabase database
- ❌ No more internet required
- ✅ Videos stored on your phone
- ✅ Complete privacy and control
- ✅ Works offline forever

---

## 📱 Setup in 3 Steps

### Step 1: Create Folder
On your phone, create:
```
/Internal Storage/Movies/KidsVideos/
```

### Step 2: Add Videos
Copy MP4 videos to that folder with this naming:
```
Happy Birthday Song - Kids Channel.mp4
[Short] Dance Time - Fun Videos.mp4
```

### Step 3: Install App
Download the latest APK from GitHub releases and install.

**That's it!** 🎉

---

## 📝 File Naming Rules

| Format | Example | Resolution | Result |
|--------|---------|------------|--------|
| `Title - Channel.mp4` | `ABC Song - Learning.mp4` | 1920x1080 (landscape) | Home tab |
| `Title - Channel.mp4` | `Dance Time - Kids.mp4` | 1080x1920 (portrait) | Shorts tab |
| `[Short] Title - Channel.mp4` | `[Short] ABC - Kids.mp4` | Any resolution | Forces Shorts tab |
| `Just Title.mp4` | `MyVideo.mp4` | Any | Auto-detected by resolution |

### 🎯 **Smart Detection**

The app **automatically detects shorts** based on video resolution:
- **Portrait/Vertical** (9:16, 9:18, etc.) → Shorts tab
- **Landscape/Horizontal** (16:9, 4:3, etc.) → Home tab

You don't need to manually mark videos as shorts - just name them normally!

---

## 🔑 First Launch

When you first open the app:
1. You'll see permission request for "Videos"
2. Tap **Allow** 
3. App scans your KidsVideos folder
4. Videos appear in Home and Shorts tabs

---

## 📂 Where to Put Videos

**Best location:**
```
/Internal Storage/Movies/KidsVideos/
```

**Alternative locations** (app checks these too):
```
/Internal Storage/DCIM/KidsVideos/
/Android/data/com.kidscurated.player/files/Movies/KidsVideos/
```

**Or anywhere** with these in filename:
- "rhyme"
- "song"
- "[Short]"

---

## 🎥 Supported Formats

- ✅ `.mp4` (Best - recommended)
- ✅ `.mkv`
- ✅ `.avi`
- ✅ `.mov`
- ✅ `.webm`
- ✅ `.3gp`

**Recommended settings:**
- Resolution: 720p or 480p
- Format: MP4 (H.264 codec)
- File size: 20-50MB per video
- Duration: 2-5 minutes

---

## 🔧 Troubleshooting

### "No videos found"
- Check folder is named exactly: `KidsVideos`
- Make sure files are `.mp4` format
- Grant storage permission in Settings > Apps > Kids Curated Player > Permissions
- Try restarting the app

### Videos won't play
- Convert to MP4 format (use VLC or Handbrake)
- Make sure codec is H.264
- Test file plays on computer first

### Permission denied
- Go to Settings > Apps > Kids Curated Player
- Tap Permissions
- Enable "Files and Media" or "Videos"

---

## 💡 Pro Tips

1. **Organize by channel name** - Use consistent channel names for easier browsing
2. **Keep videos short** - 2-5 minutes works best for kids
3. **Use 720p quality** - Good balance of quality and file size
4. **Test on computer first** - Make sure video plays before copying
5. **Batch convert** - Use Handbrake to convert many videos at once

---

## 📊 Example Setup

```
/Internal Storage/Movies/KidsVideos/
├── ABC Song - Learning Time.mp4 (1920x1080, 45MB, 3:20) → Home
├── Happy Birthday - Rhymes.mp4 (1280x720, 38MB, 2:45) → Home
├── Dance Moves - Kids Fun.mp4 (1080x1920, 12MB, 0:45) → Shorts (portrait)
├── Color Song - Toddlers.mp4 (1080x1920, 15MB, 1:00) → Shorts (portrait)
├── Nursery Rhymes Collection - Baby Songs.mp4 (1920x1080, 82MB, 8:15) → Home
└── Animal Sounds - Educational.mp4 (1280x720, 55MB, 4:30) → Home
```

App will show:
- **Home tab**: 4 regular videos (all landscape)
- **Shorts tab**: 2 short videos (all portrait)

**Auto-detection based on aspect ratio!**

---

## 🎯 Why This is Better

| Old Way (YouTube/DB) | New Way (Local Files) |
|---------------------|----------------------|
| Internet required | Works offline |
| YouTube tracking | Zero tracking |
| Hosting costs | Free forever |
| Limited control | Complete control |
| Can break anytime | Always works |
| Complex setup | Simple file copy |

---

## 📦 Download APK

Latest version: https://github.com/ramaeondev/KidsCuratedPlayer/releases/download/latest/app-debug-latest.apk

GitHub repo: https://github.com/ramaeondev/KidsCuratedPlayer

---

## ❓ Need Help?

Check the detailed guide: `LOCAL_VIDEO_SETUP.md`

Or open an issue on GitHub: https://github.com/ramaeondev/KidsCuratedPlayer/issues

---

**Enjoy your ad-free, tracking-free, offline kids video player! 🎉**
