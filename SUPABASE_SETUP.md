# Supabase Analytics Setup Guide

This guide will help you set up Supabase backend for YouKids analytics.

## Step 1: Create Supabase Project

1. Go to [https://supabase.com](https://supabase.com)
2. Sign up or log in
3. Click "New Project"
4. Fill in:
   - **Project Name**: YouKids Analytics
   - **Database Password**: Choose a strong password
   - **Region**: Select closest to your users
5. Click "Create new project" and wait for it to initialize (~2 minutes)

## Step 2: Create Database Tables

1. In your Supabase dashboard, go to **SQL Editor** (left sidebar)
2. Click "New query"
3. Copy and paste the entire content from `SUPABASE_SCHEMA.sql`
4. Click "Run" to execute the SQL
5. You should see: "Success. No rows returned"

This creates 3 tables:
- `user_installs` - Tracks unique app installations
- `app_opens` - Tracks daily/monthly active users
- `analytics_events` - Tracks custom events

## Step 3: Get Your Credentials

1. Go to **Project Settings** (gear icon in sidebar)
2. Click **API** tab
3. Copy these two values:
   - **Project URL** (e.g., `https://abcdefgh.supabase.co`)
   - **anon public key** (starts with `eyJ...`)

## Step 4: Update Your App

1. Open `app/src/main/java/com/kidscurated/player/data/Analytics.kt`
2. Find these lines:
   ```kotlin
   private const val SUPABASE_URL = "YOUR_SUPABASE_URL"
   private const val SUPABASE_KEY = "YOUR_SUPABASE_ANON_KEY"
   ```
3. Replace with your actual credentials:
   ```kotlin
   private const val SUPABASE_URL = "https://abcdefgh.supabase.co"
   private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   ```
4. Build and run the app

## Step 5: Verify Data Collection

1. Install and open the app on your device
2. Go back to Supabase dashboard
3. Click **Table Editor** (left sidebar)
4. Select `user_installs` table
5. You should see a new row with your anonymous user ID
6. Check `app_opens` table to see app open events

## Step 6: View Analytics

### Quick Stats

Go to **SQL Editor** and run these queries:

**Total unique users:**
```sql
SELECT COUNT(DISTINCT user_id) as total_users FROM user_installs;
```

**Daily Active Users (last 7 days):**
```sql
SELECT * FROM dau_stats LIMIT 7;
```

**Top countries:**
```sql
SELECT * FROM installs_by_country LIMIT 10;
```

**Android version distribution:**
```sql
SELECT * FROM device_distribution;
```

### Dashboard Access

You can also:
1. Use **Table Editor** to browse raw data
2. Use **SQL Editor** for custom queries
3. Create a dashboard with tools like Metabase or Grafana
4. Export data to CSV for analysis

## Privacy & Legal Compliance

✅ **What We Collect (Legal):**
- Anonymous User ID (Android ID + UUID)
- Install timestamp
- App opens count
- Device type (e.g., "Samsung Galaxy S21")
- Android version (e.g., "Android 13")
- App version (e.g., "1.1.0")
- Country code (e.g., "US")

❌ **What We DON'T Collect:**
- No personal information (names, emails, phone numbers)
- No precise location (GPS coordinates)
- No behavioral tracking (browsing history)
- No contact list access
- No COPPA violations

### Privacy Policy

The app already includes a comprehensive privacy policy in `PRIVACY_POLICY.md`. Make sure to:
1. Host this policy online (e.g., GitHub Pages)
2. Link to it in Google Play Store listing
3. Include link in app settings

## Data Models

### user_installs
| Field | Type | Description |
|-------|------|-------------|
| user_id | TEXT | Anonymous ID (unique) |
| install_time | BIGINT | Unix timestamp |
| device_type | TEXT | Manufacturer + Model |
| android_version | TEXT | Android OS version |
| app_version | TEXT | App version |
| country_code | TEXT | ISO country code |

### app_opens
| Field | Type | Description |
|-------|------|-------------|
| user_id | TEXT | Anonymous ID |
| timestamp | BIGINT | Unix timestamp |
| open_count | INT | Total opens for this user |
| device_type | TEXT | Device info |
| android_version | TEXT | OS version |
| app_version | TEXT | App version |

### analytics_events
| Field | Type | Description |
|-------|------|-------------|
| user_id | TEXT | Anonymous ID |
| event_name | TEXT | Event type |
| timestamp | BIGINT | Unix timestamp |
| properties | JSONB | Custom event data |
| device_type | TEXT | Device info |
| android_version | TEXT | OS version |
| app_version | TEXT | App version |

## Custom Events

You can track custom events in your app:

```kotlin
// Track video play
Analytics.sendEvent("video_played", mapOf(
    "video_id" to video.id,
    "is_short" to video.isShort,
    "duration" to video.duration
))

// Track screen view
Analytics.sendEvent("screen_view", mapOf(
    "screen_name" to "home"
))

// Track feature usage
Analytics.sendEvent("feature_used", mapOf(
    "feature_name" to "video_player"
))
```

## Troubleshooting

**"⚠️ Supabase not configured - skipping analytics upload"**
- You haven't updated the credentials in Analytics.kt yet

**"❌ Error sending event: ..."**
- Check internet connection
- Verify Supabase credentials are correct
- Check Supabase project is active
- View Logcat for detailed error message

**No data showing in Supabase:**
- Wait 1-2 minutes for data to appear
- Check Table Editor (not SQL Editor)
- Verify RLS policies are enabled
- Check app logs for errors

## Security

The database is secured with Row Level Security (RLS):
- ✅ App can INSERT data (anonymous)
- ✅ Dashboard can READ data (authenticated)
- ❌ Nobody can DELETE or UPDATE data
- ❌ Anonymous users cannot read data

This prevents:
- Data tampering
- Unauthorized access
- Data leaks

## Cost

Supabase Free Tier includes:
- 500MB database storage
- 1GB file storage
- 50,000 monthly active users
- 2GB bandwidth

This is sufficient for:
- ~100,000 user installs
- ~1,000,000 app opens per month
- Unlimited analytics events

Upgrade if you exceed these limits.

## Support

- **Supabase Docs**: https://supabase.com/docs
- **Supabase Discord**: https://discord.supabase.com
- **GitHub Issues**: Create an issue in your repo

## Next Steps

After setup:
1. ✅ Test with a few devices
2. ✅ Monitor data collection for 1 week
3. ✅ Create custom analytics queries
4. ✅ Set up alerts for milestones (1K users, 10K users, etc.)
5. ✅ Export data regularly for backup
