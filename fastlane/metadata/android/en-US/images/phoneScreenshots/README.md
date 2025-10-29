# Phone Screenshots Directory

Place your app screenshots here for F-Droid and app stores.

## Naming Convention
- Use numbered prefixes: `1_`, `2_`, `3_`, etc.
- Add descriptive names: `1_home.png`, `2_shorts.png`, `3_library.png`

## Requirements
- **Format**: PNG or JPEG
- **Count**: 3-8 screenshots recommended
- **Size**: 1080x1920 (portrait) or 1920x1080 (landscape)
- **Content**: Show key features and main screens

## Example Screenshot Names
- `1_home.png` - Home screen with video gallery
- `2_shorts.png` - YouTube Shorts section
- `3_library.png` - Video library view
- `4_player.png` - Video player in action
- `5_settings.png` - Settings/preferences (optional)

## Quick Capture
Run your app and use one of these methods:

**Android Studio:**
```
Run app → Click camera icon in toolbar → Save screenshot
```

**ADB Command:**
```bash
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png 1_home.png
```

**On Device:**
```
Power + Volume Down → Transfer to computer → Rename
```
