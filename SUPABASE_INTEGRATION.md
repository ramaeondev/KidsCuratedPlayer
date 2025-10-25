# Supabase Integration Guide

## ✅ Setup Complete!

Your app now fetches videos dynamically from Supabase instead of hardcoded data.

## 📊 Supabase Table Structure

Create a table named `videos` in your Supabase project with these columns:

```sql
CREATE TABLE videos (
  id TEXT PRIMARY KEY,
  title TEXT NOT NULL,
  channelName TEXT NOT NULL,
  thumbnailUrl TEXT NOT NULL,
  views TEXT NOT NULL,
  uploadTime TEXT NOT NULL,
  duration TEXT NOT NULL,
  youtubeUrl TEXT NOT NULL,
  isShort BOOLEAN NOT NULL DEFAULT false
);
```

## 📝 Sample Data Insert

```sql
INSERT INTO videos (id, title, channelName, thumbnailUrl, views, uploadTime, duration, youtubeUrl, isShort)
VALUES 
('VMXHQRLRywY', 'Top 25 Telugu Rhymes for Children - Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/VMXHQRLRywY/maxresdefault.jpg', '19 crore views', '9 years ago', '25:12', 'https://www.youtube.com/watch?v=VMXHQRLRywY', false),
('yvoLY8U0IU4', 'Kaki Kaki Kadavala Kaki | Telugu Rhymes', 'infobells - Telugu', 'https://img.youtube.com/vi/yvoLY8U0IU4/maxresdefault.jpg', '1.3 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/yvoLY8U0IU4', true);
```

## 🔑 Configuration

Your Supabase credentials are already configured in:
- **File**: `app/src/main/java/com/kidscurated/player/data/SupabaseConfig.kt`
- **URL**: https://mpudbsgszekwghohwcwf.supabase.co
- **API Key**: Already set

## 🚀 How It Works

### 1. **Automatic Data Fetching**
- App launches → Fetches videos from Supabase
- Data is cached for 5 minutes
- Falls back to local data if API fails

### 2. **Screens Updated**
- ✅ **HomeScreen**: Fetches regular videos from Supabase
- ✅ **ShortsScreen**: Fetches shorts from Supabase
- ✅ **LibraryScreen**: Fetches both with separate tabs

### 3. **Loading States**
- Shows loading spinner while fetching
- Displays errors if fetch fails
- Seamless fallback to local data

## 📱 Usage

### Add Videos via Supabase Dashboard
1. Go to https://app.supabase.com
2. Open your project
3. Navigate to Table Editor → `videos`
4. Click "Insert row"
5. Fill in the video details:
   - `id`: YouTube video ID (e.g., "dQw4w9WgXcQ")
   - `title`: Video title
   - `channelName`: Channel name
   - `thumbnailUrl`: `https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg`
   - `views`: View count (e.g., "1M views")
   - `uploadTime`: When uploaded (e.g., "2 days ago")
   - `duration`: Video length (e.g., "5:30")
   - `youtubeUrl`: Full YouTube URL
   - `isShort`: `true` for shorts, `false` for regular videos

### Add Videos Programmatically
Use Supabase API or SQL:

```sql
INSERT INTO videos VALUES (
  'VIDEO_ID',
  'Video Title',
  'Channel Name',
  'https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg',
  '1M views',
  '2 days ago',
  '5:30',
  'https://www.youtube.com/watch?v=VIDEO_ID',
  false
);
```

## 🔄 Cache Management

The app caches fetched data for 5 minutes to reduce API calls.

To clear cache (future feature):
```kotlin
VideoRepository.clearCache()
```

## 🛡️ Security

- ✅ Anon key is safe for client-side use
- ✅ Enable Row Level Security (RLS) in Supabase:
  ```sql
  -- Allow public read access
  CREATE POLICY "Allow public read access" ON videos
    FOR SELECT USING (true);
  ```

## 📊 API Endpoints Used

- `/videos?select=*` - Get all videos
- `/videos?select=*&isShort=eq.false` - Get regular videos only
- `/videos?select=*&isShort=eq.true` - Get shorts only

## 🐛 Troubleshooting

### Videos not loading?
1. Check internet connection
2. Verify Supabase table exists
3. Check table has data
4. Verify RLS policies allow read access
5. Check app logs for errors

### App shows local data?
- API call failed, check logs
- Supabase credentials might be wrong
- Table name might be incorrect

## 🎯 Next Steps

1. **Import all your videos** into Supabase table
2. **Enable RLS policies** for security
3. **Test the app** - it will fetch from Supabase
4. **Remove local fallback data** (optional) after testing

## 📖 Related Files

- `SupabaseConfig.kt` - Supabase credentials
- `SupabaseService.kt` - API endpoints
- `RetrofitClient.kt` - HTTP client
- `VideoRepository.kt` - Data fetching logic
- `HomeScreen.kt`, `ShortsScreen.kt`, `LibraryScreen.kt` - UI screens
