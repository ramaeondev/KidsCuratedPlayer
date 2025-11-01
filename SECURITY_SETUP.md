# 🔐 Supabase Credentials Setup Guide

## Overview

This app uses Supabase anon keys which are **safe to be public** (by design). The credentials are compiled into the APK via BuildConfig. You can optionally override them in `local.properties` for development/testing.

## Setup Instructions

### Default Credentials (Already Configured)

The app comes with default Supabase credentials compiled into the APK. **Supabase anon keys are public by design** and safe to include in client apps.

**Default values are already set in `build.gradle`** - no setup needed for production builds!

### Step 1: Override Credentials (Optional - For Development Only)

If you want to use different credentials for development/testing, edit `local.properties`:

```properties
# Android SDK location (required for builds)
sdk.dir=/Users/YourUsername/Library/Android/sdk

# Supabase Credentials (optional override - defaults already work)
supabase.url=https://your-project.supabase.co
supabase.anon.key=your-anon-key-here
```

### Step 2: Verify Setup (Optional Override)

1. Sync Gradle in Android Studio
2. Build the project
3. Check `BuildConfig` - credentials should be loaded

## Important Notes

### ✅ Supabase Anon Keys are Public by Design
- **Anon keys are meant to be public** - they're designed to be in client apps
- Row Level Security (RLS) policies protect your data, not the key
- The default credentials work out of the box

### ✅ Optional Override for Development
- Override in `local.properties` if you need different credentials for testing
- Keep `local.properties` in `.gitignore` (already configured)
- Default credentials are compiled into the APK

### ✅ Production Builds
- **No setup needed!** Default credentials are already in `build.gradle`
- APK will have credentials compiled in automatically
- Works without any local.properties file

## Troubleshooting

### Issue: "Supabase not configured"
**Solution:** Make sure `local.properties` exists and contains valid credentials. Sync Gradle after making changes.

### Issue: "BuildConfig not found"
**Solution:** 
1. Clean and rebuild the project
2. Make sure `buildConfig = true` in `build.gradle`
3. Sync Gradle

### Issue: Credentials not loading
**Solution:**
1. Check `local.properties` format (no spaces around `=`)
2. Make sure file is in project root (same level as `build.gradle`)
3. Sync Gradle after changes

## Security Best Practices

1. **Use Row Level Security (RLS)**: Protect data with RLS policies in Supabase (not the key)
2. **Monitor Usage**: Check Supabase dashboard for unusual activity
3. **Rotate Keys if Needed**: Can change keys in Supabase and update `build.gradle` defaults
4. **Development Override**: Use `local.properties` for testing different projects

## How It Works

1. **Default credentials** are in `build.gradle` (compiled into APK)
2. **Optional override** via `local.properties` (for development)
3. **BuildConfig** reads from `local.properties` OR uses defaults
4. **APK always has credentials** - ready to use out of the box!

## Migration from Old Code

✅ **Already done!** The credentials are now in `build.gradle` as defaults, so:
- APK will have credentials compiled in
- Works immediately without setup
- Can still override for development if needed

The old hardcoded credentials are now **insecure** and should be rotated in Supabase dashboard.

