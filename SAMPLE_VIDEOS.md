# 🎬 Sample Video Library for Quick Start

Ready-to-use examples of educational and safe content for children. Copy and paste these into your `VideoRepository.kt` file.

## 📚 How to Use These Examples

1. Open `app/src/main/java/com/kidscurated/player/data/VideoRepository.kt`
2. Replace the sample videos with these curated examples
3. Pick videos appropriate for your child's age
4. Always preview videos yourself before adding them!

---

## 🧸 Preschool Age (3-5 years)

### Educational & Learning Videos

```kotlin
val curatedVideos = listOf(
    Video(
        id = "LXb3EKWsInQ",
        title = "KIDZ BOP Kids - Happy (Official Music Video)",
        channelName = "Kidz Bop",
        thumbnailUrl = "https://img.youtube.com/vi/LXb3EKWsInQ/maxresdefault.jpg",
        views = "42M views",
        uploadTime = "5 years ago",
        duration = "4:00",
        youtubeUrl = "https://www.youtube.com/watch?v=LXb3EKWsInQ"
    ),
    Video(
        id = "jTIFSBlHzR8",
        title = "ABC Song | Sesame Street",
        channelName = "Sesame Street",
        thumbnailUrl = "https://img.youtube.com/vi/jTIFSBlHzR8/maxresdefault.jpg",
        views = "15M views",
        uploadTime = "3 years ago",
        duration = "2:45",
        youtubeUrl = "https://www.youtube.com/watch?v=jTIFSBlHzR8"
    ),
    Video(
        id = "_UR-l3QI2nE",
        title = "Counting to 10 Song | Super Simple Songs",
        channelName = "Super Simple Songs",
        thumbnailUrl = "https://img.youtube.com/vi/_UR-l3QI2nE/maxresdefault.jpg",
        views = "89M views",
        uploadTime = "8 years ago",
        duration = "2:15",
        youtubeUrl = "https://www.youtube.com/watch?v=_UR-l3QI2nE"
    )
)
```

---

## 🎒 Elementary Age (6-10 years)

### Science & Nature

```kotlin
val curatedVideos = listOf(
    Video(
        id = "23rUAHB_pNE",
        title = "What Is Photosynthesis? | SciShow Kids",
        channelName = "SciShow Kids",
        thumbnailUrl = "https://img.youtube.com/vi/23rUAHB_pNE/maxresdefault.jpg",
        views = "450K views",
        uploadTime = "2 years ago",
        duration = "4:30",
        youtubeUrl = "https://www.youtube.com/watch?v=23rUAHB_pNE"
    ),
    Video(
        id = "8jPQjjsBbIc",
        title = "Solar System 101 | National Geographic Kids",
        channelName = "National Geographic Kids",
        thumbnailUrl = "https://img.youtube.com/vi/8jPQjjsBbIc/maxresdefault.jpg",
        views = "2.3M views",
        uploadTime = "1 year ago",
        duration = "5:45",
        youtubeUrl = "https://www.youtube.com/watch?v=8jPQjjsBbIc"
    ),
    Video(
        id = "hS5CfP8n_js",
        title = "Why Do We Have Seasons? | Crash Course Kids",
        channelName = "Crash Course Kids",
        thumbnailUrl = "https://img.youtube.com/vi/hS5CfP8n_js/maxresdefault.jpg",
        views = "680K views",
        uploadTime = "3 years ago",
        duration = "4:15",
        youtubeUrl = "https://www.youtube.com/watch?v=hS5CfP8n_js"
    ),
    Video(
        id = "7-BO-87cNHg",
        title = "Animal Habitats for Kids | Science Lesson",
        channelName = "Free School",
        thumbnailUrl = "https://img.youtube.com/vi/7-BO-87cNHg/maxresdefault.jpg",
        views = "1.1M views",
        uploadTime = "4 years ago",
        duration = "7:20",
        youtubeUrl = "https://www.youtube.com/watch?v=7-BO-87cNHg"
    )
)
```

---

## 🔬 Tweens (11-13 years)

### STEM & Education

```kotlin
val curatedVideos = listOf(
    Video(
        id = "VFbhSugC0kA",
        title = "How Do Vaccines Work? | TED-Ed",
        channelName = "TED-Ed",
        thumbnailUrl = "https://img.youtube.com/vi/VFbhSugC0kA/maxresdefault.jpg",
        views = "3.2M views",
        uploadTime = "2 years ago",
        duration = "5:15",
        youtubeUrl = "https://www.youtube.com/watch?v=VFbhSugC0kA"
    ),
    Video(
        id = "kv6FwMMIuYU",
        title = "The Science of Thinking | Crash Course",
        channelName = "Crash Course",
        thumbnailUrl = "https://img.youtube.com/vi/kv6FwMMIuYU/maxresdefault.jpg",
        views = "890K views",
        uploadTime = "1 year ago",
        duration = "10:45",
        youtubeUrl = "https://www.youtube.com/watch?v=kv6FwMMIuYU"
    ),
    Video(
        id = "rDnIDe9WTFM",
        title = "Why Is the Ocean Salty? | SciShow",
        channelName = "SciShow",
        thumbnailUrl = "https://img.youtube.com/vi/rDnIDe9WTFM/maxresdefault.jpg",
        views = "1.5M views",
        uploadTime = "3 years ago",
        duration = "4:30",
        youtubeUrl = "https://www.youtube.com/watch?v=rDnIDe9WTFM"
    )
)
```

---

## 🎬 Sample Shorts (Vertical Videos)

```kotlin
val curatedShorts = listOf(
    Video(
        id = "example1",
        title = "Quick Science Fact - Water Cycle",
        channelName = "Science Shorts for Kids",
        thumbnailUrl = "https://img.youtube.com/vi/example1/maxresdefault.jpg",
        views = "250K views",
        uploadTime = "1 day ago",
        duration = "0:45",
        youtubeUrl = "https://www.youtube.com/shorts/example1"
    ),
    Video(
        id = "example2",
        title = "Amazing Animal Facts - Elephants",
        channelName = "Animal Facts",
        thumbnailUrl = "https://img.youtube.com/vi/example2/maxresdefault.jpg",
        views = "500K views",
        uploadTime = "3 days ago",
        duration = "0:30",
        youtubeUrl = "https://www.youtube.com/shorts/example2"
    )
)
```

---

## 🎨 Art & Creativity

```kotlin
Video(
    id = "nwK2B5l-bIs",
    title = "How to Draw a Cartoon - Easy Drawing Tutorial for Kids",
    channelName = "Art for Kids Hub",
    thumbnailUrl = "https://img.youtube.com/vi/nwK2B5l-bIs/maxresdefault.jpg",
    views = "2.1M views",
    uploadTime = "6 months ago",
    duration = "8:30",
    youtubeUrl = "https://www.youtube.com/watch?v=nwK2B5l-bIs"
),
Video(
    id = "example_craft",
    title = "5 Easy Paper Crafts for Kids",
    channelName = "Easy Peasy and Fun",
    thumbnailUrl = "https://img.youtube.com/vi/example_craft/maxresdefault.jpg",
    views = "1.8M views",
    uploadTime = "4 months ago",
    duration = "10:15",
    youtubeUrl = "https://www.youtube.com/watch?v=example_craft"
)
```

---

## 📖 Stories & Reading

```kotlin
Video(
    id = "example_story",
    title = "The Very Hungry Caterpillar - Animated Story",
    channelName = "Storyline Online",
    thumbnailUrl = "https://img.youtube.com/vi/example_story/maxresdefault.jpg",
    views = "5.2M views",
    uploadTime = "1 year ago",
    duration = "6:45",
    youtubeUrl = "https://www.youtube.com/watch?v=example_story"
)
```

---

## 🎵 Music & Movement

```kotlin
Video(
    id = "D0Ajq682yrA",
    title = "Baby Shark Dance | Kids Songs",
    channelName = "Pinkfong Baby Shark",
    thumbnailUrl = "https://img.youtube.com/vi/D0Ajq682yrA/maxresdefault.jpg",
    views = "12B views",
    uploadTime = "7 years ago",
    duration = "2:15",
    youtubeUrl = "https://www.youtube.com/watch?v=D0Ajq682yrA"
),
Video(
    id = "example_freeze",
    title = "Freeze Dance | Brain Break for Kids",
    channelName = "GoNoodle",
    thumbnailUrl = "https://img.youtube.com/vi/example_freeze/maxresdefault.jpg",
    views = "3.5M views",
    uploadTime = "2 years ago",
    duration = "3:30",
    youtubeUrl = "https://www.youtube.com/watch?v=example_freeze"
)
```

---

## 🌍 Geography & Culture

```kotlin
Video(
    id = "example_geography",
    title = "7 Continents for Kids | Geography Learning",
    channelName = "Geography Learning",
    thumbnailUrl = "https://img.youtube.com/vi/example_geography/maxresdefault.jpg",
    views = "920K views",
    uploadTime = "1 year ago",
    duration = "5:00",
    youtubeUrl = "https://www.youtube.com/watch?v=example_geography"
)
```

---

## 🔢 Math & Logic

```kotlin
Video(
    id = "example_math",
    title = "Multiplication Tables Song | Times Tables",
    channelName = "Numberock",
    thumbnailUrl = "https://img.youtube.com/vi/example_math/maxresdefault.jpg",
    views = "1.2M views",
    uploadTime = "3 years ago",
    duration = "4:20",
    youtubeUrl = "https://www.youtube.com/watch?v=example_math"
),
Video(
    id = "example_shapes",
    title = "Learn Shapes for Kids | 2D and 3D Shapes",
    channelName = "Math Learning",
    thumbnailUrl = "https://img.youtube.com/vi/example_shapes/maxresdefault.jpg",
    views = "850K views",
    uploadTime = "2 years ago",
    duration = "6:10",
    youtubeUrl = "https://www.youtube.com/watch?v=example_shapes"
)
```

---

## 🏃 Physical Activity & Exercise

```kotlin
Video(
    id = "example_exercise",
    title = "Kids Workout - 10 Minute Exercise Video",
    channelName = "Kids Fitness",
    thumbnailUrl = "https://img.youtube.com/vi/example_exercise/maxresdefault.jpg",
    views = "2.8M views",
    uploadTime = "1 year ago",
    duration = "10:00",
    youtubeUrl = "https://www.youtube.com/watch?v=example_exercise"
)
```

---

## 🧘 Mindfulness & Calm

```kotlin
Video(
    id = "example_calm",
    title = "Guided Meditation for Kids | 5 Minute Calm",
    channelName = "Cosmic Kids Yoga",
    thumbnailUrl = "https://img.youtube.com/vi/example_calm/maxresdefault.jpg",
    views = "1.5M views",
    uploadTime = "6 months ago",
    duration = "5:00",
    youtubeUrl = "https://www.youtube.com/watch?v=example_calm"
)
```

---

## 📝 Full Example - Copy & Paste Ready

Here's a complete `VideoRepository.kt` with mixed content:

```kotlin
package com.kidscurated.player.data

data class Video(
    val id: String,
    val title: String,
    val channelName: String,
    val thumbnailUrl: String,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val youtubeUrl: String
)

object VideoRepository {
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
            id = "_UR-l3QI2nE",
            title = "Counting to 10 Song",
            channelName = "Super Simple Songs",
            thumbnailUrl = "https://img.youtube.com/vi/_UR-l3QI2nE/maxresdefault.jpg",
            views = "89M views",
            uploadTime = "8 years ago",
            duration = "2:15",
            youtubeUrl = "https://www.youtube.com/watch?v=_UR-l3QI2nE"
        ),
        Video(
            id = "D0Ajq682yrA",
            title = "Baby Shark Dance",
            channelName = "Pinkfong",
            thumbnailUrl = "https://img.youtube.com/vi/D0Ajq682yrA/maxresdefault.jpg",
            views = "12B views",
            uploadTime = "7 years ago",
            duration = "2:15",
            youtubeUrl = "https://www.youtube.com/watch?v=D0Ajq682yrA"
        )
        // Add more videos here...
    )
    
    val curatedShorts = listOf(
        Video(
            id = "example1",
            title = "Quick Science Fact",
            channelName = "Science Shorts",
            thumbnailUrl = "https://img.youtube.com/vi/example1/maxresdefault.jpg",
            views = "250K views",
            uploadTime = "1 day ago",
            duration = "0:45",
            youtubeUrl = "https://www.youtube.com/shorts/example1"
        )
        // Add more shorts here...
    )
    
    fun getVideoById(id: String): Video? {
        return (curatedVideos + curatedShorts).find { it.id == id }
    }
    
    fun getAllVideos() = curatedVideos
    
    fun getAllShorts() = curatedShorts
}
```

---

## ⚠️ Important Notes

### Video IDs Used Here
- Some video IDs in this document are real (like "LXb3EKWsInQ")
- Some are placeholders (like "example1", "example_craft")
- **Replace all placeholder IDs with real YouTube video IDs**
- **Always verify videos play before giving to your child**

### Finding Video IDs
1. Go to the YouTube video you want
2. Look at the URL: `https://www.youtube.com/watch?v=VIDEO_ID`
3. Copy the ID part (after `v=`)
4. Use format: `https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg` for thumbnails

### Recommended Sources for Finding Good Content

**Official Channels:**
- Sesame Street Official
- PBS Kids
- National Geographic Kids
- Khan Academy Kids
- Crash Course Kids
- SciShow Kids
- TED-Ed

**Music & Songs:**
- Super Simple Songs
- Kidz Bop
- Pinkfong
- CoComelon (for younger kids)

**Arts & Crafts:**
- Art for Kids Hub
- Easy Peasy and Fun
- Red Ted Art

**Exercise:**
- GoNoodle
- Cosmic Kids Yoga
- Kids Fitness

---

## 🔍 How to Find More Videos

### YouTube Search Tips
1. Use specific educational keywords
2. Look for verified channels (✓ mark)
3. Check video date (recent is better)
4. Read comments from other parents
5. Preview entire video yourself

### Search Terms to Try
- "educational videos for kids"
- "[subject] for kids explained"
- "science experiments for children"
- "math songs for elementary"
- "story time for preschoolers"
- "kids yoga"
- "children's workout video"

---

## ✅ Quality Check Before Adding

For each video, verify:
- [ ] Entire video watched by you
- [ ] Age-appropriate language
- [ ] Educational or positive value
- [ ] No scary/inappropriate content
- [ ] No commercial advertisements
- [ ] Good production quality
- [ ] Engaging but not overstimulating
- [ ] Video embeds successfully (test in app)

---

## 🎯 Pro Tips

1. **Start Small** - Begin with 5-10 videos you've fully vetted
2. **Mix It Up** - Include different types: educational, music, stories, movement
3. **Age Appropriate** - Match content to your child's developmental stage
4. **Update Regularly** - Add new content weekly to keep it fresh
5. **Get Feedback** - Ask your child what they like
6. **Balance Content** - Not all videos need to be purely educational
7. **Seasonal Content** - Add holiday or season-specific videos

---

**Remember: The video IDs shown here are examples. Always use actual YouTube video IDs and preview all content before adding to your child's app!**

For more guidance, see **PARENT_SAFETY_GUIDE.md**
