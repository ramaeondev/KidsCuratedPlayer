# Local Video Setup Guide

## 📱 How to Add Your Own Videos

The app now plays videos directly from your device's storage - no internet required!

### 🎯 Quick Setup

1. **Connect Phone to Computer** (USB cable)
2. **Create the Folder**:
   - Navigate to: `Internal Storage/Movies/`
   - Create a new folder named: `KidsVideos`
3. **Copy Videos**: 
   - Copy your MP4 videos into the `KidsVideos` folder
4. **Name Your Files** (Important!):
   - Use this format: `Title - Channel Name.mp4`
   - For shorts: `[Short] Title - Channel Name.mp4`

### 📝 File Naming Examples

**Regular Videos:**
```
Happy Birthday Song - Kids Rhymes.mp4
ABC Learning - Educational Channel.mp4
Nursery Rhyme Collection - Baby Songs.mp4
```

**Short Videos:**
```
[Short] Quick Dance - Fun Kids.mp4
[Short] Animal Sounds - Learning Time.mp4
[Short] Color Song - Toddler Fun.mp4
```

### 📂 Folder Location

The app looks for videos in these locations (in order):

1. **Primary**: `/Internal Storage/Movies/KidsVideos/`
2. **Alternative**: `/Internal Storage/DCIM/KidsVideos/`
3. **Fallback**: App will scan all device videos with "rhyme", "song", or "[Short]" in filename

### 🎬 Supported Video Formats

- ✅ `.mp4` (Recommended - best compatibility)
- ✅ `.mkv`
- ✅ `.avi`
- ✅ `.mov`
- ✅ `.3gp`
- ✅ `.webm`

### 📊 How File Names Become Metadata

The app parses your filename to extract information:

| Filename | Title | Channel | Type |
|----------|-------|---------|------|
| `Happy Birthday - Kids Rhymes.mp4` | Happy Birthday | Kids Rhymes | Regular |
| `[Short] Dance Time - Fun Videos.mp4` | Dance Time | Fun Videos | Short |
| `MyVideo.mp4` | MyVideo | Local Videos | Regular |

### 🔄 Refreshing Video List

The app automatically scans for videos every 5 minutes. To force a refresh:
- Close and reopen the app
- Or wait 5 minutes after adding new videos

### ⚠️ Troubleshooting

**Videos not showing up?**
1. Check folder name is exactly `KidsVideos` (case-sensitive)
2. Verify files are `.mp4` format
3. Make sure storage permission is granted
4. Try renaming with the format: `Title - Channel.mp4`

**Permission Denied?**
- When you first open the app, grant "Files and Media" permission
- On Android 13+, you'll be asked to allow "Videos" access

**Videos showing but won't play?**
- Make sure video codec is H.264 (most compatible)
- Try converting video to MP4 format
- Check file isn't corrupted (try playing on computer first)

### 🎨 Tips for Best Experience

1. **Video Quality**:
   - 720p is good balance of quality and file size
   - 480p works fine for kids content
   - Avoid 4K (takes too much storage)

2. **File Size**:
   - Keep videos under 100MB each
   - Shorter videos (2-5 min) work best

3. **Thumbnails**:
   - App uses first frame of video as thumbnail
   - Make sure first frame looks good!

4. **Organization**:
   - Use consistent naming for easier management
   - Group similar videos with same channel name

### 📱 Storage Management

**Check Available Space:**
- Go to Settings > Storage
- Make sure you have at least 1GB free

**Example Storage Usage:**
- 10 videos (50MB each) = 500MB
- 50 videos (30MB each) = 1.5GB
- 100 videos (20MB each) = 2GB

### 🚀 Advanced: Using ADB (Wireless Transfer)

If you don't have a USB cable:

```bash
# Enable wireless debugging in Developer Options
# Get your phone's IP address from Settings > About Phone > Status

# Connect via ADB
adb connect <phone-ip>:5555

# Push videos to phone
adb push "Happy Birthday - Kids.mp4" /sdcard/Movies/KidsVideos/
```

### 🔒 Privacy & Security

✅ **All videos stay on your device** - nothing is uploaded
✅ **No internet required** after initial setup
✅ **No tracking** of what your child watches
✅ **Complete control** over content

### 🎯 Mixed Content (YouTube + Local)

Currently, the app is configured for **local videos only**. To add YouTube videos back:
1. Set up Supabase database (see SUPABASE_SETUP.md)
2. The app will show both local and YouTube videos together

---

## 📋 Quick Checklist

- [ ] Created `/Movies/KidsVideos/` folder on phone
- [ ] Copied video files to folder
- [ ] Named files with format: `Title - Channel.mp4`
- [ ] Granted storage permission to app
- [ ] Opened app and verified videos appear
- [ ] Tested video playback

## 🎉 You're All Set!

Your kids can now watch curated videos completely offline with zero YouTube tracking!
