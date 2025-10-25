# 🚀 QUICK START GUIDE

Get your Kids Curated Player up and running in 5 simple steps!

## Step 1: Add Your Videos (5 minutes)

1. Open this file in any text editor:
   ```
   app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
   ```

2. Find a YouTube video you want to add (example: `https://www.youtube.com/watch?v=dQw4w9WgXcQ`)

3. Extract the video ID: `dQw4w9WgXcQ` (the part after `v=`)

4. Add to the `curatedVideos` list:
   ```kotlin
   Video(
       id = "dQw4w9WgXcQ",
       title = "My First Video",
       channelName = "Cool Channel",
       thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/maxresdefault.jpg",
       views = "1M views",
       uploadTime = "1 day ago",
       duration = "5:30",
       youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
   ),
   ```

5. Add as many videos as you want!

## Step 2: Install Android Studio (15 minutes)

1. Download from: https://developer.android.com/studio
2. Install with default settings
3. Launch Android Studio
4. Wait for initial setup to complete

## Step 3: Open the Project (2 minutes)

1. In Android Studio: **File → Open**
2. Navigate to and select the `KidsCuratedPlayer` folder
3. Click **Open**
4. Wait for Gradle sync to complete (progress bar at bottom)

## Step 4: Build the APK (3 minutes)

1. Click: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for "BUILD SUCCESSFUL" message
3. Click **locate** in the notification that appears

OR find your APK here:
```
KidsCuratedPlayer/app/build/outputs/apk/debug/app-debug.apk
```

## Step 5: Install on Your Device (5 minutes)

### Option A: Direct Install via USB
1. Connect Android device to computer via USB
2. Enable USB Debugging on device:
   - Settings → About Phone → Tap "Build Number" 7 times
   - Settings → Developer Options → Enable USB Debugging
3. In Android Studio, click the green **Run** button (▶)
4. Select your device from the list
5. App will install and launch automatically!

### Option B: Manual APK Install
1. Copy `app-debug.apk` to your Android device (via USB, email, cloud, etc.)
2. On device: Settings → Security → Enable "Install from Unknown Sources"
3. Open the APK file using a file manager
4. Tap **Install**
5. Launch the app!

---

## 🎉 Done! Your App is Ready!

Open the app and you'll see your curated videos ready to play.

---

## 🔧 Quick Customizations

### Change App Name
**File:** `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">MyKidsTube</string>
```

### Change App Icon
1. Right-click `app/src/main/res` folder
2. **New → Image Asset**
3. Select your icon image
4. Click **Next** → **Finish**

### Add More Videos
Just edit `VideoRepository.kt` and rebuild!

---

## ⚡ Super Quick Build (Terminal)

If you prefer command line:

```bash
# Navigate to project
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer

# Make gradlew executable (Mac/Linux only)
chmod +x gradlew

# Build APK
./gradlew assembleDebug

# APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 🆘 Quick Troubleshooting

**Problem:** Gradle sync fails  
**Solution:** Check internet connection, restart Android Studio

**Problem:** Build fails  
**Solution:** Build → Clean Project, then Build → Rebuild Project

**Problem:** Can't install APK  
**Solution:** Enable "Install from Unknown Sources" in device Settings → Security

**Problem:** Video won't play  
**Solution:** Check internet connection, verify video ID is correct

---

## 📚 Need More Help?

- **Full Documentation:** See `README.md`
- **Detailed Build Guide:** See `BUILD_GUIDE.md`
- **Video Configuration:** See `VIDEO_CONFIGURATION_GUIDE.md`
- **Project Overview:** See `PROJECT_SUMMARY.md`

---

## ✅ Checklist

- [ ] Added at least 3-5 videos to VideoRepository.kt
- [ ] Opened project in Android Studio
- [ ] Gradle sync completed successfully
- [ ] Built APK (no errors)
- [ ] Installed on Android device
- [ ] Launched app successfully
- [ ] Videos play correctly

**Enjoy your safe, curated YouTube experience for kids! 🎈📱**
