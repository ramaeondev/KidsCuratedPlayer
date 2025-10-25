# 🔧 Troubleshooting: JSON Not Loading from Supabase

## Issue
App shows empty screens or loading spinner indefinitely - no videos/shorts appear.

## Root Cause
Most likely one of these issues:
1. **Supabase table not created yet** ⚠️ MOST COMMON
2. **Row Level Security (RLS) blocking access** ⚠️ VERY COMMON
3. **No data inserted into table**
4. **Network/API issues**

---

## ✅ SOLUTION: Complete Setup Steps

### Step 1: Run the SQL Setup Script

1. **Open Supabase Dashboard**
   - Go to: https://app.supabase.com
   - Login and open project: `mpudbsgszekwghohwcwf`

2. **Open SQL Editor**
   - Click on "SQL Editor" in the left sidebar
   - Click "New query"

3. **Copy and Run the Complete SQL**
   - Open the file `SUPABASE_SETUP.sql` in this repository
   - Copy ALL the content (entire file)
   - Paste into Supabase SQL Editor
   - Click "Run" or press `Ctrl+Enter` (Windows) / `Cmd+Enter` (Mac)

4. **Verify Success**
   - You should see: `total_videos=20, regular_videos=10, shorts=10`
   - If you see errors, read them carefully and fix

### Step 2: Verify Table Exists

Go to Table Editor → You should see a table named `videos` with 20 rows

### Step 3: Verify RLS Policies

1. Click on `videos` table
2. Click "Policies" tab
3. You should see: **"Allow public read access"** policy enabled
4. If not present, run this SQL:
   ```sql
   ALTER TABLE videos ENABLE ROW LEVEL SECURITY;
   
   CREATE POLICY "Allow public read access" 
   ON videos 
   FOR SELECT 
   USING (true);
   ```

### Step 4: Test API Manually

Open this URL in your browser (replace with your project URL):
```
https://mpudbsgszekwghohwcwf.supabase.co/rest/v1/videos?select=*
```

**Expected Result:** JSON array with 20 video objects

**If you see:**
- `[]` (empty array) → No data inserted OR RLS blocking
- `{"message":"relation \"public.videos\" does not exist"}` → Table not created
- `401 Unauthorized` → API key issue (unlikely, already configured in app)

---

## 📱 Testing the App After Setup

1. **Download Latest APK**
   - Wait ~5 minutes after pushing code
   - Download: https://github.com/ramaeondev/KidsCuratedPlayer/releases/download/latest/app-debug-latest.apk

2. **Install on Android Device**
   - Enable "Unknown Sources" in Settings if needed
   - Install the APK

3. **Check Logs (if using Android Studio)**
   - Connect device via USB
   - Open Logcat
   - Look for these log messages:
     - `📡 Fetching regular videos from Supabase...`
     - `✅ Received 10 videos from Supabase`
     - `📱 ShortsScreen: Received 10 shorts`
   - If you see `❌ Error`, read the error message

4. **What You Should See:**
   - **Home Tab:** 10 Telugu rhyme videos with thumbnails
   - **Shorts Tab:** 10 Telugu rhyme shorts
   - **Library Tab:** Both categories in separate tabs

---

## 🚨 Common Errors and Fixes

### Error: "No videos found. Please check..."

**Cause:** Table exists but RLS is blocking OR no data inserted

**Fix:**
```sql
-- Enable RLS with public read access
ALTER TABLE videos ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Allow public read access" 
ON videos 
FOR SELECT 
USING (true);
```

### Error: "relation \"public.videos\" does not exist"

**Cause:** Table not created yet

**Fix:** Run the complete `SUPABASE_SETUP.sql` script

### Error: Empty array `[]` from API

**Cause:** RLS enabled but no policy allowing reads

**Fix:** 
1. Go to Table Editor → videos → Policies
2. Create new policy:
   - Policy name: `Allow public read access`
   - Policy command: `SELECT`
   - Target roles: `public`
   - USING expression: `true`
   - Click "Create policy"

### Error: App shows loading forever

**Cause:** Network error or API timeout

**Fix:**
1. Check internet connection
2. Verify Supabase project is active
3. Check logs for specific error messages

---

## 🔍 Manual Debugging

### Test Supabase REST API

```bash
# Test getting all videos
curl "https://mpudbsgszekwghohwcwf.supabase.co/rest/v1/videos?select=*" \
  -H "apikey: YOUR_ANON_KEY" \
  -H "Authorization: Bearer YOUR_ANON_KEY"

# Test getting only regular videos
curl "https://mpudbsgszekwghohwcwf.supabase.co/rest/v1/videos?select=*&isShort=eq.false" \
  -H "apikey: YOUR_ANON_KEY" \
  -H "Authorization: Bearer YOUR_ANON_KEY"

# Test getting only shorts
curl "https://mpudbsgszekwghohwcwf.supabase.co/rest/v1/videos?select=*&isShort=eq.true" \
  -H "apikey: YOUR_ANON_KEY" \
  -H "Authorization: Bearer YOUR_ANON_KEY"
```

### Check App Logs

When app is running, you should see console logs:
```
📡 Fetching regular videos from Supabase...
✅ Received 10 videos from Supabase
🏠 HomeScreen: Received 10 videos
```

If you see errors:
```
❌ Error fetching videos: <error message>
```

---

## ✅ Success Checklist

- [ ] Supabase table `videos` created
- [ ] RLS enabled with public read policy
- [ ] 20 videos inserted (10 regular + 10 shorts)
- [ ] API test URL returns JSON data
- [ ] App downloaded and installed
- [ ] Home screen shows 10 videos
- [ ] Shorts screen shows 10 shorts
- [ ] Videos play without Error 153

---

## 🆘 Still Not Working?

If you've followed all steps and still have issues:

1. **Share the error message** from the app (visible on screen or in logs)
2. **Test the API URL** in browser and share what you see
3. **Check Supabase dashboard** → Table Editor → videos → verify 20 rows exist
4. **Verify RLS policy** exists and is enabled

The app now has detailed logging to help diagnose issues!
