# GitHub Actions CI/CD Guide

This project includes automated build workflows using GitHub Actions to build APK files automatically.

## 🤖 Available Workflows

### 1. **Build APK Workflow** (`build-apk.yml`)

**Triggers:**
- Automatically on every push to `main` or `develop` branches
- On pull requests to `main` branch
- Manually via GitHub UI (Actions tab → "Build Android APK" → "Run workflow")

**What it does:**
- Sets up Java 21 and Android SDK
- Builds both Debug and Release APK files
- Uploads APKs as downloadable artifacts
- Artifacts are kept for 30 days

**Download APK:**
1. Go to: https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions
2. Click on the latest successful workflow run
3. Scroll down to "Artifacts" section
4. Download `app-debug` (or `app-release`)
5. Extract the ZIP file to get the APK

---

### 2. **Release Workflow** (`release.yml`)

**Triggers:**
- Automatically when you push a version tag (e.g., `v1.0.0`)
- Manually via GitHub UI with custom version input

**What it does:**
- Builds the APK
- Renames APK with version number
- Creates a GitHub Release with detailed release notes
- Attaches the APK to the release for direct download
- Keeps artifact for 90 days

**Create a Release:**

#### Option A: Using Git Tags (Recommended)
```bash
# Make sure all changes are committed
git add .
git commit -m "Release version 1.0.0"

# Create and push version tag
git tag -a v1.0.0 -m "Release v1.0.0 - Initial public release"
git push origin v1.0.0

# GitHub Actions will automatically build and create the release
```

#### Option B: Manual Trigger from GitHub UI
1. Go to https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions
2. Click "Create Release with APK" workflow
3. Click "Run workflow" button
4. Enter version (e.g., `v1.0.0`)
5. Click "Run workflow"

**Download from Release:**
1. Go to: https://github.com/thavaneesh-dev/KidsCuratedPlayer/releases
2. Click on the latest release
3. Download the APK file (e.g., `app-v1.0.0.apk`)
4. Transfer to your Android device and install

---

## 📋 Step-by-Step: First Release

### 1. Push Your Code to GitHub

```bash
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer

# Add all files
git add .

# Commit
git commit -m "Add GitHub Actions workflows for automated APK builds"

# Push to GitHub (you'll need to set up authentication first)
git push origin main
```

### 2. Verify Workflows are Working

1. Go to: https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions
2. You should see "Build Android APK" workflow running
3. Wait for it to complete (usually 3-5 minutes)
4. Green checkmark = success!

### 3. Create Your First Release

```bash
# Create version tag
git tag -a v1.0.0 -m "Release v1.0.0 - Kids Curated Player initial release"

# Push the tag
git push origin v1.0.0
```

### 4. Download Your APK

**From Releases (Recommended):**
- Visit: https://github.com/thavaneesh-dev/KidsCuratedPlayer/releases
- Download `app-v1.0.0.apk`

**From Actions Artifacts:**
- Visit: https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions
- Click latest "Create Release with APK" run
- Download from Artifacts section

---

## 🔄 Workflow for Updates

When you want to release a new version with updated videos:

### 1. Update Your Videos

Edit `app/src/main/java/com/kidscurated/player/data/VideoRepository.kt`:
```kotlin
val curatedVideos = listOf(
    Video(
        id = "YOUR_VIDEO_ID",
        title = "Your Video Title",
        // ... rest of the video data
    ),
    // Add more videos...
)
```

### 2. Commit Changes

```bash
git add app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
git commit -m "Update video list - Added educational content"
git push origin main
```

### 3. Create New Release

```bash
# Increment version number
git tag -a v1.1.0 -m "Release v1.1.0 - Updated video collection"
git push origin v1.1.0
```

### 4. Share the New APK

The APK will be available at:
```
https://github.com/thavaneesh-dev/KidsCuratedPlayer/releases/download/v1.1.0/app-v1.1.0.apk
```

You can share this direct download link with anyone!

---

## 🎯 Quick Commands Reference

```bash
# Build locally (for testing)
./gradlew assembleDebug

# Commit changes
git add .
git commit -m "Your commit message"
git push origin main

# Create release
git tag -a v1.0.0 -m "Release message"
git push origin v1.0.0

# List tags
git tag -l

# Delete a tag (if needed)
git tag -d v1.0.0
git push origin :refs/tags/v1.0.0
```

---

## 📱 Sharing APKs with Others

### Direct Download Links

After creating a release, share this link:
```
https://github.com/thavaneesh-dev/KidsCuratedPlayer/releases/latest/download/app-v1.0.0.apk
```

Replace `v1.0.0` with your actual version.

### QR Code

You can generate a QR code for the download link using:
- https://www.qr-code-generator.com/
- Paste your release URL
- Generate and print/share the QR code

### Installation Instructions for Recipients

Send these instructions along with the APK:

1. **Download** the APK file on your Android device
2. **Settings** → **Security** → Enable "Install from Unknown Sources"
3. **Open** the downloaded APK file
4. **Tap** "Install"
5. **Launch** the app when installation completes

---

## 🛠️ Troubleshooting

### Workflow Fails with "SDK not found"

The workflow automatically sets up Android SDK. If it fails:
- Check the workflow logs for specific errors
- Ensure `build.gradle` files are correct
- Verify Java version compatibility (21)

### APK Size Too Large

The workflow may fail if APK exceeds GitHub's limits:
- Current APK size: ~18 MB (well within limits)
- GitHub limit: 2 GB per artifact
- No issues expected!

### Can't Download Artifact

Artifacts are only available:
- To repository members
- For 30 days (builds) or 90 days (releases)
- If workflow completed successfully

**Solution:** Use GitHub Releases instead - public and permanent!

---

## 🔒 Security Notes

- ✅ Workflows run in isolated GitHub-hosted runners
- ✅ No secrets or credentials stored in workflows
- ✅ APKs are unsigned debug builds (safe for personal use)
- ✅ Public releases are available to anyone with the link
- ⚠️ Never commit `local.properties` or keystore files

---

## 📊 Workflow Status Badge

Add this to your README to show build status:

```markdown
![Build APK](https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions/workflows/build-apk.yml/badge.svg)
```

Result: ![Build APK](https://github.com/thavaneesh-dev/KidsCuratedPlayer/actions/workflows/build-apk.yml/badge.svg)

---

## 🎓 Learning Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Build Guide](https://developer.android.com/studio/build/building-cmdline)
- [Creating Releases](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository)

---

## ✨ Benefits of This Setup

1. **Automated Builds**: No need for local build environment
2. **Version Control**: Every release is tagged and tracked
3. **Easy Distribution**: Direct download links for APKs
4. **Build History**: All previous versions available
5. **Public Access**: Anyone can download your curated app
6. **Professional**: GitHub releases look polished and official
7. **Free**: GitHub Actions is free for public repositories

---

**Need Help?** Open an issue on GitHub or check the FAQ_TROUBLESHOOTING.md guide.
