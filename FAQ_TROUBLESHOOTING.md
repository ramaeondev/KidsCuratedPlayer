# ❓ FAQ & Troubleshooting Guide

Common questions and solutions for the Kids Curated Player app.

## 📱 General Questions

### Q: Do I need a YouTube account to use this app?
**A:** No! The app doesn't require any login or YouTube account. It embeds videos directly.

### Q: Is this legal?
**A:** Yes, for personal use. YouTube allows embedding videos. However, this is for private, non-commercial use only. Respect YouTube's Terms of Service and copyright laws.

### Q: Do I need an API key?
**A:** No! The app uses YouTube's embed feature via WebView, which doesn't require an API key.

### Q: Will this work offline?
**A:** No, the app requires an internet connection to stream videos from YouTube.

### Q: Can my child access the regular YouTube from this app?
**A:** No! The app only shows videos you've pre-configured. There's no way to browse or search YouTube directly.

### Q: Is this app on the Google Play Store?
**A:** No, this is a personal project template. You build and install it yourself as a private APK.

### Q: Can I distribute this app to others?
**A:** The code is for personal use. If you want to share, direct them to create their own instance with their own curated content.

---

## 🔧 Build & Installation Issues

### Problem: Gradle Sync Failed
**Symptoms:**
- "Gradle sync failed" error in Android Studio
- Red exclamation marks on project files

**Solutions:**
1. Check your internet connection
2. File → Invalidate Caches / Restart
3. Update Android Studio to the latest version
4. Check Java version: File → Project Structure → SDK Location
5. Try: Build → Clean Project, then sync again

### Problem: SDK Not Found
**Symptoms:**
- "Android SDK is not found"
- Build fails with SDK error

**Solutions:**
1. Open Android Studio → Preferences/Settings
2. Appearance & Behavior → System Settings → Android SDK
3. Install Android SDK Platform 34
4. Install Android SDK Build-Tools 34.0.0
5. Apply changes and restart Android Studio

### Problem: Build Takes Forever
**Symptoms:**
- Gradle build never completes
- Stuck at "Building" step

**Solutions:**
1. Check internet connection (downloads dependencies)
2. Increase Gradle memory in `gradle.properties`:
   ```
   org.gradle.jvmargs=-Xmx4096m
   ```
3. Disable parallel builds temporarily
4. Close other applications to free up RAM

### Problem: "Command Not Found: ./gradlew"
**Symptoms:**
- Terminal says gradlew not found
- macOS/Linux only

**Solution:**
```bash
chmod +x gradlew
./gradlew assembleDebug
```

### Problem: Can't Install APK on Device
**Symptoms:**
- "App not installed" message
- Installation blocked

**Solutions:**
1. Enable "Install from Unknown Sources":
   - Settings → Security → Unknown Sources → Enable
   - Or: Settings → Apps → Special Access → Install Unknown Apps
2. Check storage space (need at least 100MB free)
3. Uninstall any previous version first
4. Restart device and try again
5. Check APK isn't corrupted (re-copy from computer)

### Problem: App Crashes Immediately on Launch
**Symptoms:**
- App opens then closes instantly
- "App keeps stopping" message

**Solutions:**
1. Check Android version (needs Android 7.0+)
2. Clear app cache: Settings → Apps → Kids Curated Player → Clear Cache
3. Reinstall the app
4. Check device has internet connection
5. Look at logcat in Android Studio for error details

---

## 🎥 Video Playback Issues

### Problem: Videos Won't Play
**Symptoms:**
- Black screen where video should be
- Loading spinner never stops
- Error message

**Solutions:**

**Check Internet Connection:**
- Ensure device is connected to WiFi or mobile data
- Try opening a web browser to test connection

**Verify Video ID:**
- Video ID must be exactly correct
- Check for typos in `VideoRepository.kt`
- Test video URL in a regular browser first

**Check Video Embedding:**
- Some creators disable embedding
- Try the video URL in browser
- If it says "Video unavailable" or "Embedding disabled", remove it

**Clear App Data:**
- Settings → Apps → Kids Curated Player → Clear Data
- Reinstall app

### Problem: Thumbnails Don't Load
**Symptoms:**
- Gray boxes instead of video thumbnails
- Images not appearing

**Solutions:**
1. Check internet connection
2. Verify thumbnail URL format:
   ```kotlin
   thumbnailUrl = "https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg"
   ```
3. Replace VIDEO_ID with actual video ID
4. Try alternative thumbnail resolution:
   - `hqdefault.jpg` (high quality)
   - `mqdefault.jpg` (medium quality)
   - `default.jpg` (low quality)

### Problem: Video Plays But Screen is Tiny
**Symptoms:**
- Video player is very small
- Can't see video properly

**Solution:**
This is a WebView rendering issue. The video should auto-adjust, but if not:
1. Rotate device to landscape and back
2. Close and reopen the app
3. Check if issue persists across all videos (may be code issue)

### Problem: Can't Exit Video Player
**Symptoms:**
- Back button doesn't work
- Stuck on video screen

**Solution:**
- Try device back button (not on-screen)
- Force close app and reopen
- Check if back button in code is working (top-left arrow)

---

## 💻 Code & Configuration Issues

### Problem: Syntax Error in VideoRepository.kt
**Symptoms:**
- Red squiggly lines in code
- "Cannot resolve symbol" errors
- Build fails

**Common Mistakes:**

**Missing Comma:**
```kotlin
// WRONG - Missing comma after first video
Video(...),
Video(...)  // ← Need comma here!
Video(...)
```

**Correct:**
```kotlin
Video(...),
Video(...),  // ← Comma here
Video(...)   // Last one doesn't need comma
```

**Missing Quotes:**
```kotlin
// WRONG
title = Video Title

// CORRECT
title = "Video Title"
```

**Wrong Parentheses:**
```kotlin
// WRONG
Video[
    id = "123"
]

// CORRECT
Video(
    id = "123"
)
```

### Problem: App Shows No Videos
**Symptoms:**
- App opens but feed is empty
- "No videos available" message

**Solutions:**
1. Check `VideoRepository.kt` has videos in the lists
2. Verify you're looking at correct screen (Home vs Shorts)
3. Check for syntax errors in video definitions
4. Rebuild project: Build → Rebuild Project
5. Check logcat for errors

### Problem: Videos I Added Don't Appear
**Symptoms:**
- Added videos but still see old ones
- Changes not reflected

**Solutions:**
1. Save the file (Ctrl+S / Cmd+S)
2. Build → Rebuild Project
3. Uninstall app from device
4. Build new APK
5. Reinstall app

---

## 🎨 UI & Display Issues

### Problem: App Looks Different Than Expected
**Symptoms:**
- Colors are wrong
- Layout is broken
- Text overlapping

**Solutions:**
1. Check device Android version (needs 7.0+)
2. Test on different device/emulator
3. Check for code modifications that broke layout
4. Restore Color.kt and Theme.kt to defaults

### Problem: Bottom Navigation Bar Missing
**Symptoms:**
- Can't switch between Home/Shorts/Library
- Navigation bar doesn't show

**Solution:**
This means you're in the video player screen. Press the back button to return to main screens.

### Problem: Text is Too Small/Large
**Symptoms:**
- Can't read text easily
- Text size inappropriate

**Solution:**
Modify `Type.kt`:
```kotlin
bodyLarge = TextStyle(
    fontSize = 18.sp,  // Increase this number
    // ...
)
```

---

## 📱 Device-Specific Issues

### Problem: App Won't Run on Older Device
**Symptoms:**
- "App not compatible" message
- Installation fails on old Android

**Solution:**
The app requires Android 7.0 (API 24) minimum. To support older devices:

1. Open `app/build.gradle`
2. Change `minSdk`:
   ```gradle
   minSdk 21  // Instead of 24
   ```
3. Rebuild project
4. Note: Some features may not work on very old devices

### Problem: Different Behavior on Different Devices
**Symptoms:**
- Works on one device but not another
- Inconsistent behavior

**Possible Causes:**
- Different Android versions
- Different screen sizes
- Different manufacturers (Samsung vs Google vs others)
- Device-specific customizations

**Solutions:**
- Test on multiple devices
- Check Android version compatibility
- Use Android Studio emulators to test various configurations

---

## 🔐 Permission Issues

### Problem: "Network Permission Denied"
**Symptoms:**
- Can't load videos
- Network error

**Solution:**
Check `AndroidManifest.xml` has:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Problem: App Wants Unnecessary Permissions
**Symptoms:**
- App asks for camera, location, etc.

**Solution:**
The app should ONLY ask for internet permission. If it asks for more:
- Check you haven't added extra permissions
- Check dependencies aren't requesting permissions
- Review `AndroidManifest.xml`

---

## 🚀 Performance Issues

### Problem: App is Slow or Laggy
**Symptoms:**
- Scrolling is choppy
- UI freezes
- Takes long time to respond

**Solutions:**
1. Close other apps running in background
2. Restart device
3. Check available storage (need at least 500MB free)
4. Clear app cache
5. Test on device with better specs

### Problem: Battery Drains Quickly
**Symptoms:**
- Device gets hot
- Battery percentage drops fast

**Solutions:**
1. This is normal when streaming video
2. Lower screen brightness
3. Use WiFi instead of mobile data
4. Close app when not in use
5. Set screen timeout

### Problem: Uses Too Much Data
**Symptoms:**
- High mobile data usage
- Data cap exceeded

**Solutions:**
1. Use WiFi for video streaming
2. Limit video watching time
3. Lower video quality (built into YouTube player)
4. Monitor data usage in device settings

---

## 🎯 Expected Behavior vs Bugs

### This is NORMAL:
✅ Requires internet connection  
✅ First video load may take a few seconds  
✅ Thumbnails load progressively  
✅ Some videos don't embed (creator choice)  
✅ Video quality adjusts based on connection speed  
✅ Can't comment or see comments (by design)  
✅ Can't browse or search (by design)  
✅ Subscribe button doesn't work (by design)  

### This is a BUG:
❌ App crashes repeatedly  
❌ No videos show with working internet  
❌ Can access YouTube directly  
❌ Videos play with no sound  
❌ UI is completely broken  
❌ Can't navigate between screens  
❌ Back button completely non-functional  

---

## 🔍 Debugging Steps

### How to Find Error Details

**Method 1: Android Studio Logcat**
1. Connect device via USB
2. Run app from Android Studio
3. Open Logcat (bottom toolbar)
4. Look for red error messages
5. Search for your app package name

**Method 2: Device System Info**
1. Install app on device
2. When it crashes, Android saves error log
3. Settings → About Phone → Send feedback
4. Look for crash reports

**Method 3: Test Build**
1. File → Build → Analyze APK
2. Check for missing resources
3. Verify all dependencies included

---

## 💡 Prevention Tips

### Before Building:
1. ✅ All code has correct syntax
2. ✅ Video IDs are verified
3. ✅ Thumbnail URLs are correct
4. ✅ No placeholder values left
5. ✅ File is saved before building

### Before Installing:
1. ✅ APK built successfully
2. ✅ No errors in build output
3. ✅ File size looks reasonable (15-30MB)
4. ✅ Device meets requirements

### After Installing:
1. ✅ Open app before giving to child
2. ✅ Test at least 3 videos
3. ✅ Test navigation
4. ✅ Check performance
5. ✅ Verify all content is appropriate

---

## 🆘 Still Having Issues?

### Check These Resources:
1. **README.md** - Overview and features
2. **BUILD_GUIDE.md** - Detailed build instructions
3. **VIDEO_CONFIGURATION_GUIDE.md** - How to add videos
4. **PROJECT_SUMMARY.md** - Technical details

### Search for Solutions:
1. Google the exact error message
2. Check Stack Overflow for Android issues
3. Search Android Studio documentation
4. Look for YouTube embed issues

### Common Search Terms:
- "Android WebView YouTube embed not playing"
- "Gradle sync failed Android Studio"
- "Can't install APK on Android device"
- "[Your specific error message]"

---

## 📝 Reporting Pattern

If you need to search for help, provide:
1. **What you were trying to do**: "Building APK"
2. **What happened**: "Build failed with error"
3. **Error message**: Exact text of error
4. **Environment**: Android Studio version, OS, device
5. **What you've tried**: Solutions already attempted

---

## ✅ Final Checklist

If nothing works, try these steps in order:

- [ ] Restart Android Studio
- [ ] Build → Clean Project
- [ ] Build → Rebuild Project
- [ ] File → Invalidate Caches / Restart
- [ ] Close Android Studio completely
- [ ] Restart computer
- [ ] Open project fresh
- [ ] Try building again

If still failing:
- [ ] Update Android Studio
- [ ] Update Gradle
- [ ] Check Java version
- [ ] Reinstall Android Studio (last resort)

---

## 🎓 Learning Resources

Want to understand the code better?

**Kotlin Basics:**
- https://kotlinlang.org/docs/getting-started.html

**Jetpack Compose:**
- https://developer.android.com/jetpack/compose/tutorial

**Android Development:**
- https://developer.android.com/courses

**YouTube Embed:**
- https://developers.google.com/youtube/player_parameters

---

## 💬 Common Error Messages Explained

### "Gradle DSL method not found: 'google()'"
**Meaning:** Gradle version too old  
**Fix:** Update `build.gradle` and Gradle wrapper

### "Manifest merger failed"
**Meaning:** Conflicting settings in AndroidManifest.xml  
**Fix:** Check for duplicate permissions or activities

### "Cannot resolve symbol 'R'"
**Meaning:** Resource compilation failed  
**Fix:** Build → Clean Project → Rebuild Project

### "java.lang.OutOfMemoryError"
**Meaning:** Not enough memory for build  
**Fix:** Increase heap size in gradle.properties

### "INSTALL_FAILED_INSUFFICIENT_STORAGE"
**Meaning:** Device doesn't have enough space  
**Fix:** Free up storage on device

---

**Still stuck? Don't give up! Most issues are simple configuration problems. Take a break and try again with fresh eyes. 🌟**
