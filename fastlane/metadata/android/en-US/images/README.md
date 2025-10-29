# F-Droid Images Directory

This directory contains images for F-Droid and app stores.

## Required Files

### Icon (Required)
- **File**: `icon.png`
- **Size**: 512x512 pixels (PNG)
- **Description**: High-resolution app icon
- **Notes**: 
  - Must be square
  - No transparency recommended
  - Should match your app launcher icon design

### Phone Screenshots (Optional but Recommended)
Place screenshots in `phoneScreenshots/` directory:
- **Files**: `1_home.png`, `2_shorts.png`, `3_library.png`, `4_player.png`, etc.
- **Naming**: Number prefix (1_, 2_, 3_...) for ordering
- **Format**: PNG or JPEG
- **Orientation**: Portrait (9:16 or similar) or Landscape (16:9)
- **Recommended Size**: 1080x1920 (portrait) or 1920x1080 (landscape)
- **Notes**:
  - Show key features of your app
  - Include 3-8 screenshots
  - F-Droid will display them in numerical order

## How to Add Images

### Option 1: Export from Android Studio
1. Run your app on an emulator or device
2. Use "Capture Screenshot" in Android Studio (Camera icon in Run toolbar)
3. Save screenshots to `phoneScreenshots/` with names like `1_home.png`

### Option 2: Use ADB
```bash
# Take screenshot
adb shell screencap -p /sdcard/screenshot.png

# Pull to computer
adb pull /sdcard/screenshot.png phoneScreenshots/1_home.png
```

### Option 3: From Device
1. Take screenshots on your device (Power + Volume Down)
2. Transfer to computer via USB or cloud
3. Rename and place in `phoneScreenshots/`

### Creating icon.png
Your app uses a vector drawable icon. To create icon.png:

1. **Android Studio Method**:
   - Right-click `app/src/main/res/drawable/ic_launcher_foreground.xml`
   - Select "Image Asset Studio"
   - Export as PNG at 512x512

2. **Manual Export**:
   - Build your app
   - Extract APK
   - Get largest mipmap icon (xxxhdpi)
   - Scale to 512x512 if needed

3. **Design Tool**:
   - Recreate your icon design in Figma/Inkscape/Photoshop
   - Export at 512x512 PNG
   - Match colors: Red background (#FF0000), white play triangle, gold "Y" (#FFD700)

## After Adding Images

Commit and push:
```bash
git add fastlane/metadata/android/en-US/images/
git commit -m "Add F-Droid images and screenshots"
git push origin main
```

Then update your F-Droid merge request - the images will be automatically picked up.
