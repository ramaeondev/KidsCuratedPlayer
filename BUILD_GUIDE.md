# Building Your APK - Step by Step Guide

This guide will walk you through building the APK file for your Kids Curated Player app.

## Prerequisites

1. **Install Android Studio**
   - Download from: https://developer.android.com/studio
   - Install with default settings
   - Open Android Studio for the first time and let it download required components

2. **Install JDK** (if not included with Android Studio)
   - Download Java 11 or newer from: https://adoptium.net/
   - Install and note the installation path

## Method 1: Using Android Studio (Easiest)

### Step 1: Open the Project
1. Launch Android Studio
2. Click "Open an Existing Project" or go to File → Open
3. Navigate to the `KidsCuratedPlayer` folder
4. Click "Open"

### Step 2: Wait for Gradle Sync
1. Android Studio will automatically sync Gradle files
2. Wait for the process to complete (shown in bottom status bar)
3. If prompted to update Gradle or plugins, click "Update"

### Step 3: Configure Your Videos
1. In Android Studio, navigate to:
   ```
   app → java → com.kidscurated.player → data → VideoRepository.kt
   ```
2. Add your YouTube video URLs following the VIDEO_CONFIGURATION_GUIDE.md
3. Save the file (Cmd+S on Mac, Ctrl+S on Windows)

### Step 4: Build APK
1. Click on the menu: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for the build to complete (progress shown at bottom)
3. When complete, a notification will appear with "locate" link

### Step 5: Find Your APK
1. Click the "locate" link in the notification, OR
2. Navigate to:
   ```
   KidsCuratedPlayer/app/build/outputs/apk/debug/app-debug.apk
   ```

### Step 6: Install on Android Device
1. Copy `app-debug.apk` to your Android device
2. On your device, go to Settings → Security
3. Enable "Install from Unknown Sources" or "Install Unknown Apps"
4. Use a file manager to find and tap the APK file
5. Follow installation prompts

## Method 2: Using Terminal/Command Line

### For macOS/Linux:

```bash
# Navigate to project folder
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer

# Make gradlew executable
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

### For Windows:

```cmd
# Navigate to project folder
cd C:\path\to\KidsCuratedPlayer

# Build debug APK
gradlew.bat assembleDebug

# APK will be at: app\build\outputs\apk\debug\app-debug.apk
```

## Method 3: Building a Release APK (Signed)

For a production-ready APK:

### Step 1: Create a Keystore
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

Follow prompts and **REMEMBER YOUR PASSWORDS**.

### Step 2: Configure Signing in build.gradle

Add to `app/build.gradle` inside the `android` block:

```gradle
signingConfigs {
    release {
        storeFile file("../my-release-key.jks")
        storePassword "your-keystore-password"
        keyAlias "my-key-alias"
        keyPassword "your-key-password"
    }
}

buildTypes {
    release {
        signingConfig signingConfigs.release
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

### Step 3: Build Release APK

In Android Studio:
- **Build → Generate Signed Bundle / APK**
- Select **APK**
- Choose your keystore file
- Enter passwords
- Select **release** build variant
- Click **Finish**

Or via terminal:
```bash
./gradlew assembleRelease
```

Release APK location: `app/build/outputs/apk/release/app-release.apk`

## Troubleshooting

### Problem: Gradle sync fails
**Solution:**
- Check internet connection
- In Android Studio: File → Invalidate Caches / Restart
- Update Android Studio to latest version

### Problem: Build fails with "SDK not found"
**Solution:**
1. Open Android Studio → Preferences/Settings
2. Go to Appearance & Behavior → System Settings → Android SDK
3. Install Android SDK Platform 34
4. Install Build Tools version 34.0.0

### Problem: "Command not found: ./gradlew"
**Solution:**
```bash
chmod +x gradlew
```

### Problem: Java version issues
**Solution:**
- Make sure Java 11 or newer is installed
- Set JAVA_HOME environment variable
- In Android Studio: File → Project Structure → SDK Location → JDK location

### Problem: APK won't install on device
**Solution:**
- Enable "Install from Unknown Sources" in Android Settings
- Check device has enough storage space
- Try rebooting the device
- Uninstall any previous version of the app first

## Testing Your App

### Test on Emulator (in Android Studio):
1. Click "Device Manager" (phone icon on right side)
2. Create a new virtual device if needed
3. Click the play button next to your device
4. Wait for emulator to start
5. Click the green "Run" button (play icon) in toolbar

### Test on Physical Device:
1. Enable Developer Options on your device:
   - Settings → About Phone → Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging
3. Connect device via USB
4. Click the green "Run" button in Android Studio
5. Select your device from the list

## Customizing Before Building

### Change App Name:
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your Custom Name</string>
```

### Change App Icon:
1. Right-click `app/src/main/res` folder
2. New → Image Asset
3. Select your icon image
4. Follow the wizard

### Change Colors:
Edit `app/src/main/java/com/kidscurated/player/ui/theme/Color.kt`

## Final Checklist Before Building

- [ ] All YouTube video IDs are correct
- [ ] Tested video playback on at least one video
- [ ] App name is set to your preference
- [ ] App icon is customized (optional)
- [ ] Tested on emulator or physical device
- [ ] All required videos are added to VideoRepository.kt

## Need Help?

If you encounter issues:
1. Check the error message carefully
2. Google the specific error message
3. Clean and rebuild: Build → Clean Project, then Build → Rebuild Project
4. Check that all files are saved
5. Restart Android Studio

Good luck! 🚀
