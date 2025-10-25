# ⚡ Quick Commands Reference

Fast reference for common terminal commands and actions.

## 🔨 Build Commands

### Build Debug APK (macOS/Linux)
```bash
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer
chmod +x gradlew
./gradlew assembleDebug
```

### Build Debug APK (Windows)
```cmd
cd C:\path\to\KidsCuratedPlayer
gradlew.bat assembleDebug
```

### Build Release APK
```bash
./gradlew assembleRelease
```

### Clean Build
```bash
./gradlew clean
./gradlew assembleDebug
```

### Check Build Tasks
```bash
./gradlew tasks
```

---

## 📱 APK Locations

### Debug APK
```
app/build/outputs/apk/debug/app-debug.apk
```

### Release APK
```
app/build/outputs/apk/release/app-release.apk
```

---

## 🔍 Find Files

### Find All Kotlin Files
```bash
find . -name "*.kt"
```

### Find VideoRepository
```bash
find . -name "VideoRepository.kt"
```

### Find All XML Resources
```bash
find ./app/src/main/res -name "*.xml"
```

---

## 📦 Gradle Commands

### Sync Gradle
```bash
./gradlew --refresh-dependencies
```

### Check Dependencies
```bash
./gradlew dependencies
```

### Build with Stack Trace
```bash
./gradlew assembleDebug --stacktrace
```

### Build with Info Logging
```bash
./gradlew assembleDebug --info
```

---

## 🔧 Android Studio Shortcuts

### macOS

| Action | Shortcut |
|--------|----------|
| Build Project | ⌘ + F9 |
| Build APK | - (use menu) |
| Run App | ⌃ + R |
| Open File | ⌘ + Shift + O |
| Find in Files | ⌘ + Shift + F |
| Save All | ⌘ + S |
| Rebuild Project | - (use menu) |
| Sync Gradle | - (use toolbar) |

### Windows/Linux

| Action | Shortcut |
|--------|----------|
| Build Project | Ctrl + F9 |
| Build APK | - (use menu) |
| Run App | Shift + F10 |
| Open File | Ctrl + Shift + N |
| Find in Files | Ctrl + Shift + F |
| Save All | Ctrl + S |
| Rebuild Project | - (use menu) |
| Sync Gradle | - (use toolbar) |

---

## 📂 Important File Paths

### Main Entry Point
```
app/src/main/java/com/kidscurated/player/MainActivity.kt
```

### Video Configuration (EDIT THIS)
```
app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
```

### App Name
```
app/src/main/res/values/strings.xml
```

### Colors
```
app/src/main/java/com/kidscurated/player/ui/theme/Color.kt
```

### Build Configuration
```
app/build.gradle
```

### App Manifest
```
app/src/main/AndroidManifest.xml
```

---

## 🎨 Edit Common Settings

### Change App Name
**File:** `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">YourAppName</string>
```

### Change Package Name
**File:** `app/build.gradle`
```gradle
android {
    namespace 'com.kidscurated.player'  // Change this
    defaultConfig {
        applicationId "com.kidscurated.player"  // And this
    }
}
```

### Change Min SDK Version
**File:** `app/build.gradle`
```gradle
defaultConfig {
    minSdk 24  // Change this number
}
```

### Change Primary Color
**File:** `app/src/main/java/com/kidscurated/player/ui/theme/Color.kt`
```kotlin
val YouTubeRed = Color(0xFFFF0000)  // Change hex code
```

---

## 🔑 Generate Keystore (for Release)

### Create New Keystore
```bash
keytool -genkey -v -keystore my-release-key.jks \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias my-key-alias
```

### Check Keystore Info
```bash
keytool -list -v -keystore my-release-key.jks
```

---

## 🐛 Debugging Commands

### Check Java Version
```bash
java -version
```

### Check Gradle Version
```bash
./gradlew --version
```

### List Connected Devices
```bash
adb devices
```

### Install APK via ADB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Uninstall App
```bash
adb uninstall com.kidscurated.player
```

### View Logs
```bash
adb logcat | grep KidsCurated
```

### Clear App Data
```bash
adb shell pm clear com.kidscurated.player
```

---

## 📱 Device Commands (via ADB)

### Push APK to Device
```bash
adb push app-debug.apk /sdcard/
```

### Pull File from Device
```bash
adb pull /sdcard/file.txt ./
```

### Take Screenshot
```bash
adb shell screencap /sdcard/screen.png
adb pull /sdcard/screen.png
```

### Record Screen
```bash
adb shell screenrecord /sdcard/video.mp4
# Press Ctrl+C to stop
adb pull /sdcard/video.mp4
```

---

## 🔄 Git Commands (if using version control)

### Initialize Git
```bash
git init
git add .
git commit -m "Initial commit"
```

### Check Status
```bash
git status
```

### Commit Changes
```bash
git add .
git commit -m "Added new videos"
```

### View History
```bash
git log --oneline
```

### Create Branch
```bash
git checkout -b feature-name
```

---

## 🧹 Cleanup Commands

### Clean Gradle Cache
```bash
./gradlew clean
rm -rf ~/.gradle/caches/
```

### Remove Build Directories
```bash
rm -rf app/build
rm -rf build
```

### Clean Android Studio Cache
From Android Studio:
```
File → Invalidate Caches / Restart → Invalidate and Restart
```

---

## 📊 Project Statistics

### Count Lines of Code
```bash
find ./app/src/main/java -name "*.kt" | xargs wc -l
```

### Count Files
```bash
find ./app/src/main -type f | wc -l
```

### Project Size
```bash
du -sh .
```

### APK Size
```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

---

## 🔍 Search Commands

### Find Text in Kotlin Files
```bash
grep -r "VideoRepository" app/src/main/java/
```

### Find Video IDs in Code
```bash
grep -r 'id = "' app/src/main/java/
```

### Find TODO Comments
```bash
grep -r "TODO" app/src/main/java/
```

---

## ⚡ Quick Actions

### One-Line Build
```bash
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer && ./gradlew assembleDebug
```

### Build and Install
```bash
./gradlew installDebug
```

### Clean and Build
```bash
./gradlew clean assembleDebug
```

### Build Both Debug and Release
```bash
./gradlew assemble
```

---

## 🎯 Most Common Commands

### Daily Development
```bash
# 1. Open project in Android Studio
open -a "Android Studio" /Users/ramu/Documents/GitHub/KidsCuratedPlayer

# 2. Edit videos
code app/src/main/java/com/kidscurated/player/data/VideoRepository.kt

# 3. Build APK
./gradlew assembleDebug

# 4. Install on device
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 🆘 Emergency Commands

### If Build Fails
```bash
./gradlew clean
./gradlew --stop
./gradlew assembleDebug --stacktrace
```

### If Gradle is Stuck
```bash
killall -9 java
./gradlew --stop
rm -rf ~/.gradle/caches/
./gradlew assembleDebug
```

### Reset Android Studio
```
File → Invalidate Caches / Restart
Build → Clean Project
Build → Rebuild Project
```

---

## 📝 Quick Edits via Terminal

### Open in VS Code
```bash
code app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
```

### Open in TextEdit (macOS)
```bash
open -e app/src/main/res/values/strings.xml
```

### Open in Nano
```bash
nano app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
```

---

## 🎨 Asset Commands

### Convert Image to Different Sizes
```bash
# Requires ImageMagick
convert icon.png -resize 48x48 mipmap-mdpi/ic_launcher.png
convert icon.png -resize 72x72 mipmap-hdpi/ic_launcher.png
convert icon.png -resize 96x96 mipmap-xhdpi/ic_launcher.png
convert icon.png -resize 144x144 mipmap-xxhdpi/ic_launcher.png
convert icon.png -resize 192x192 mipmap-xxxhdpi/ic_launcher.png
```

---

## 📦 Backup Commands

### Backup Important Files
```bash
# Backup VideoRepository
cp app/src/main/java/com/kidscurated/player/data/VideoRepository.kt VideoRepository.backup.kt

# Backup entire project
tar -czf KidsCuratedPlayer-backup-$(date +%Y%m%d).tar.gz KidsCuratedPlayer/
```

### Restore from Backup
```bash
cp VideoRepository.backup.kt app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
```

---

## 🎯 Productivity Tips

### Create Alias (macOS/Linux)
Add to `~/.zshrc` or `~/.bashrc`:
```bash
alias kcbuild='cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer && ./gradlew assembleDebug'
alias kcclean='cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer && ./gradlew clean'
alias kcedit='code /Users/ramu/Documents/GitHub/KidsCuratedPlayer/app/src/main/java/com/kidscurated/player/data/VideoRepository.kt'
```

Then use:
```bash
kcbuild   # Builds the APK
kcclean   # Cleans project
kcedit    # Opens VideoRepository in editor
```

---

## 📱 Quick Install Methods

### Via USB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Via WiFi (device on same network)
```bash
# First connect via USB, then:
adb tcpip 5555
adb connect 192.168.1.xxx:5555
adb install app-debug.apk
```

### Via QR Code
1. Upload APK to cloud storage
2. Generate QR code for download link
3. Scan with device camera
4. Download and install

---

## 🎯 Quick Reference Card

**Most Used Commands:**
```bash
# Build
./gradlew assembleDebug

# Clean + Build
./gradlew clean assembleDebug

# Install
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Edit Videos
code app/src/main/java/com/kidscurated/player/data/VideoRepository.kt

# Check Devices
adb devices

# View Logs
adb logcat
```

---

**For detailed instructions, see:**
- Full build guide → [BUILD_GUIDE.md](BUILD_GUIDE.md)
- All commands explained → [FAQ_TROUBLESHOOTING.md](FAQ_TROUBLESHOOTING.md)
- Complete documentation → [INDEX.md](INDEX.md)
