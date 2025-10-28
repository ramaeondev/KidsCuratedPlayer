# F-Droid Submission Guide for YouKids

This guide explains how to submit YouKids to F-Droid, the free and open-source Android app repository.

## 📋 Prerequisites

Before submitting to F-Droid, ensure:

1. ✅ **Open Source License** - GPL-3.0 ✓ (Already set)
2. ✅ **No Proprietary Dependencies** - All dependencies are FOSS ✓
3. ✅ **No Tracking/Ads** - Privacy-first ✓
4. ✅ **Reproducible Builds** - Standard Gradle build ✓
5. ✅ **Version Tagged** - Use git tags (see below)

## 🎯 F-Droid Requirements Met

### ✅ License
- **License File**: `LICENSE` (GPL-3.0-or-later)
- **Open Source**: Fully auditable code
- **Compliant**: No proprietary components

### ✅ Dependencies
All dependencies are F-Droid compatible:
- ✓ AndroidX libraries (Apache 2.0)
- ✓ Jetpack Compose (Apache 2.0)
- ✓ ExoPlayer (Apache 2.0)
- ✓ Coil (Apache 2.0)
- ✓ Retrofit/OkHttp (Apache 2.0)
- ✓ Supabase Kotlin SDK (MIT)
- ✓ Ktor (Apache 2.0)
- ✓ YouTube Android Player (Apache 2.0)

### ✅ Privacy
- No tracking SDKs (Firebase, Crashlytics, etc.)
- Optional anonymous analytics (can be disabled)
- No ads or in-app purchases
- COPPA/GDPR/CCPA compliant
- Minimal permissions (only storage)

### ✅ Build Configuration
- Standard Gradle build
- No obfuscation required
- Debug signing removed for F-Droid
- Reproducible builds enabled

## 📝 Metadata Files Created

The following F-Droid metadata files have been created:

```
KidsCuratedPlayer/
├── metadata/
│   └── com.kidscurated.player.yml      # F-Droid app metadata
└── fastlane/
    └── metadata/
        └── android/
            └── en-US/
                ├── title.txt            # App title
                ├── short_description.txt # Short description
                ├── full_description.txt  # Full description
                └── changelogs/
                    └── 3.txt            # Changelog for v1.2.0
```

## 🚀 Submission Steps

### Step 1: Create Git Tag for Version

F-Droid builds from git tags. Create and push a tag:

```bash
cd /Users/ramu/Documents/GitHub/KidsCuratedPlayer

# Create tag for current version
git tag -a v1.2.0 -m "YouKids v1.2.0 - Initial F-Droid release"

# Push tag to GitHub
git push origin v1.2.0

# Verify tag
git tag -l
```

### Step 2: Update Email in Metadata

Edit `metadata/com.kidscurated.player.yml`:

```yaml
AuthorEmail: rama@therama.dev
```

Replace with your real email address.

### Step 3: Fork F-Droid Data Repository

1. Go to: https://gitlab.com/fdroid/fdroiddata
2. Click "Fork" button
3. Clone your fork:
   ```bash
   git clone https://gitlab.com/YOUR_USERNAME/fdroiddata.git
   cd fdroiddata
   ```

### Step 4: Add Your App Metadata

Copy metadata to F-Droid repo:

```bash
# Copy metadata file
cp /Users/ramu/Documents/GitHub/KidsCuratedPlayer/metadata/com.kidscurated.player.yml \
   metadata/com.kidscurated.player.yml

# Create metadata directory structure
mkdir -p metadata/com.kidscurated.player/en-US

# Copy descriptions
cp /Users/ramu/Documents/GitHub/KidsCuratedPlayer/fastlane/metadata/android/en-US/*.txt \
   metadata/com.kidscurated.player/en-US/
```

### Step 5: Validate Metadata

F-Droid provides validation tools:

```bash
# Install fdroid tools (requires Python)
pip install fdroidserver

# Validate metadata
fdroid readmeta

# Check specific app
fdroid checkupdates com.kidscurated.player
```

### Step 6: Test Build Locally (Optional)

Test if F-Droid can build your app:

```bash
# Build the app using F-Droid
fdroid build -v -l com.kidscurated.player
```

### Step 7: Create Merge Request

```bash
cd fdroiddata

# Add and commit changes
git add metadata/com.kidscurated.player.yml
git add metadata/com.kidscurated.player/
git commit -m "New app: YouKids - Safe video player for kids"

# Push to your fork
git push origin master

# Go to GitLab and create Merge Request
```

### Step 8: Submit Merge Request

1. Go to: https://gitlab.com/YOUR_USERNAME/fdroiddata
2. Click "Merge Requests" → "New Merge Request"
3. Source: your fork/master
4. Target: fdroid/fdroiddata/master
5. Title: "New app: YouKids (com.kidscurated.player)"
6. Description:
   ```markdown
   ## New App Submission: YouKids
   
   **Package ID**: com.kidscurated.player
   **License**: GPL-3.0-or-later
   **Website**: https://github.com/ramaeondev/KidsCuratedPlayer
   
   ### Description
   Safe, parent-controlled video player for kids with no algorithms or ads.
   
   ### Anti-Features
   None - fully FOSS, no tracking, no ads, no proprietary dependencies
   
   ### Build Info
   - Builds from git tags (v1.2.0)
   - Standard Gradle build
   - All dependencies are FOSS
   - No obfuscation or minification required
   
   ### Checklist
   - [x] App is FOSS (GPL-3.0)
   - [x] No proprietary dependencies
   - [x] No tracking or analytics (optional anonymous stats only)
   - [x] No ads or in-app purchases
   - [x] Metadata files included
   - [x] Git tags created
   - [x] Reproducible builds
   ```

7. Click "Submit merge request"

## ⏰ Timeline

After submitting:

1. **Review Process**: 1-4 weeks
   - F-Droid team reviews metadata
   - Validates build configuration
   - Tests app functionality
   
2. **Feedback**: 3-7 days
   - Team may request changes
   - Respond to comments on merge request
   
3. **Approval**: 1-2 days
   - Once approved, merge request is accepted
   
4. **First Build**: 24-48 hours
   - F-Droid server builds your app
   - App appears in F-Droid catalog
   
5. **Automatic Updates**: Ongoing
   - F-Droid checks for new tags daily
   - Automatically builds new versions

**Total Time**: 2-5 weeks from submission to live

## 📱 After Approval

Once approved, users can install YouKids from F-Droid:

```
https://f-droid.org/packages/com.kidscurated.player
```

### Automatic Updates

F-Droid will automatically:
- Check for new git tags daily
- Build new versions when tags are pushed
- Update users' apps automatically

### Publishing New Versions

To release new versions:

1. Update `versionCode` and `versionName` in `app/build.gradle`
2. Commit changes
3. Create git tag:
   ```bash
   git tag -a v1.3.0 -m "YouKids v1.3.0 - New features"
   git push origin v1.3.0
   ```
4. F-Droid automatically detects and builds new version

## 🖼️ Screenshots (Optional)

F-Droid supports screenshots. Create:

```
fastlane/metadata/android/en-US/images/
├── phoneScreenshots/
│   ├── 1_home.png
│   ├── 2_shorts.png
│   ├── 3_library.png
│   └── 4_player.png
├── icon.png (512x512)
└── featureGraphic.png (1024x500)
```

Requirements:
- PNG or JPEG format
- Phone screenshots: 16:9 or 9:16 ratio
- Icon: 512x512px (high-res)
- Feature graphic: 1024x500px (optional)

## 🔧 Troubleshooting

### Build Fails

If F-Droid build fails:

1. Check build logs in merge request comments
2. Common issues:
   - Missing dependencies
   - Signing configuration (remove for F-Droid)
   - Gradle version incompatibility
3. Fix issues and update merge request

### Metadata Validation Errors

If metadata has errors:

```bash
# Validate locally
fdroid readmeta
fdroid lint com.kidscurated.player
```

### Update Rejected

If update is rejected:
- Review F-Droid policy: https://f-droid.org/docs/
- Check for anti-features
- Ensure no proprietary code added

## 📚 Resources

### F-Droid Documentation
- **Inclusion Policy**: https://f-droid.org/docs/Inclusion_Policy/
- **Build Metadata**: https://f-droid.org/docs/Build_Metadata_Reference/
- **Submitting Apps**: https://f-droid.org/docs/Submitting_to_F-Droid/

### F-Droid Community
- **Forum**: https://forum.f-droid.org/
- **Matrix**: #fdroid:f-droid.org
- **Merge Requests**: https://gitlab.com/fdroid/fdroiddata/-/merge_requests

## ✅ Pre-Submission Checklist

Before submitting, verify:

- [ ] App is 100% open source (GPL-3.0) ✓
- [ ] No proprietary dependencies ✓
- [ ] No tracking or ads ✓
- [ ] Git tag created (v1.2.0)
- [ ] Author email updated in metadata
- [ ] App builds successfully locally
- [ ] Metadata files complete
- [ ] Screenshots prepared (optional)
- [ ] Changelog written ✓
- [ ] LICENSE file present ✓
- [ ] README.md updated ✓

## 🎉 Benefits of F-Droid

### For Users
- ✅ Verified FOSS apps only
- ✅ No Google Play Services required
- ✅ Privacy-respecting
- ✅ Automatic updates
- ✅ Reproducible builds

### For Developers
- ✅ Reach privacy-conscious users
- ✅ Free hosting and distribution
- ✅ Automatic builds
- ✅ No developer fees
- ✅ Active community support

## 📧 Contact

If you have questions about F-Droid submission:

1. **F-Droid Forum**: https://forum.f-droid.org/
2. **Matrix Chat**: #fdroid:f-droid.org
3. **Email**: team@f-droid.org

---

**Ready to Submit?** Follow the steps above to get YouKids on F-Droid! 🚀

*Note: This guide was created for YouKids v1.2.0. Update metadata file when releasing new versions.*
