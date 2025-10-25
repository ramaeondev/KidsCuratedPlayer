# How to Add Your YouTube Videos

Follow these steps to add your curated YouTube videos to the app:

## Step 1: Get the YouTube Video ID

### For Regular Videos:
From a URL like: `https://www.youtube.com/watch?v=dQw4w9WgXcQ`
The Video ID is: `dQw4w9WgXcQ` (the part after `v=`)

### For Shorts:
From a URL like: `https://www.youtube.com/shorts/abc123xyz`
The Video ID is: `abc123xyz` (the part after `shorts/`)

## Step 2: Open the VideoRepository File

Open this file:
```
app/src/main/java/com/kidscurated/player/data/VideoRepository.kt
```

## Step 3: Add Your Videos

### For Regular Videos (Home Feed):

Find the `curatedVideos` list and add your video:

```kotlin
Video(
    id = "YOUR_VIDEO_ID",
    title = "Your Video Title",
    channelName = "Channel Name",
    thumbnailUrl = "https://img.youtube.com/vi/YOUR_VIDEO_ID/maxresdefault.jpg",
    views = "1.2M views",
    uploadTime = "2 days ago",
    duration = "5:30",
    youtubeUrl = "https://www.youtube.com/watch?v=YOUR_VIDEO_ID"
)
```

### For Shorts (Shorts Feed):

Find the `curatedShorts` list and add your short:

```kotlin
Video(
    id = "YOUR_SHORT_ID",
    title = "Your Short Title",
    channelName = "Channel Name",
    thumbnailUrl = "https://img.youtube.com/vi/YOUR_SHORT_ID/maxresdefault.jpg",
    views = "500K views",
    uploadTime = "1 day ago",
    duration = "0:45",
    youtubeUrl = "https://www.youtube.com/shorts/YOUR_SHORT_ID"
)
```

## Step 4: Example with Real Video

Let's say you want to add this video:
`https://www.youtube.com/watch?v=LXb3EKWsInQ`

Add this to your `curatedVideos` list:

```kotlin
Video(
    id = "LXb3EKWsInQ",
    title = "KIDZ BOP Kids - Happy (Official Music Video)",
    channelName = "Kidz Bop",
    thumbnailUrl = "https://img.youtube.com/vi/LXb3EKWsInQ/maxresdefault.jpg",
    views = "42M views",
    uploadTime = "5 years ago",
    duration = "4:00",
    youtubeUrl = "https://www.youtube.com/watch?v=LXb3EKWsInQ"
)
```

## Important Notes:

1. **Always use commas** between video entries (except the last one)
2. **Keep quotes** around all text values
3. **The thumbnail URL** is automatic - just replace `YOUR_VIDEO_ID` with the actual ID
4. You can find view count, upload time, and duration on the actual YouTube video page
5. **Test each video** to make sure embedding is allowed (some creators disable embedding)

## Full Example:

```kotlin
val curatedVideos = listOf(
    Video(
        id = "LXb3EKWsInQ",
        title = "KIDZ BOP Kids - Happy",
        channelName = "Kidz Bop",
        thumbnailUrl = "https://img.youtube.com/vi/LXb3EKWsInQ/maxresdefault.jpg",
        views = "42M views",
        uploadTime = "5 years ago",
        duration = "4:00",
        youtubeUrl = "https://www.youtube.com/watch?v=LXb3EKWsInQ"
    ),
    Video(
        id = "ANOTHER_VIDEO_ID",
        title = "Another Great Video",
        channelName = "Educational Channel",
        thumbnailUrl = "https://img.youtube.com/vi/ANOTHER_VIDEO_ID/maxresdefault.jpg",
        views = "1M views",
        uploadTime = "1 week ago",
        duration = "6:15",
        youtubeUrl = "https://www.youtube.com/watch?v=ANOTHER_VIDEO_ID"
    )
)
```

## Finding Good Educational Content:

- Sesame Street
- National Geographic Kids
- PBS Kids
- Khan Academy Kids
- Crash Course Kids
- SciShow Kids
- The Kids Should See This
- Free School
- Peekaboo Kidz

Remember: Preview all content before adding it to ensure it's appropriate for your child!
