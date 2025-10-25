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
    // Add your curated YouTube video links here
    // Extract video ID from URLs like: https://www.youtube.com/watch?v=VIDEO_ID
    // or from shorts: https://www.youtube.com/shorts/VIDEO_ID
    
    val curatedVideos = listOf(
        Video(
            id = "dQw4w9WgXcQ",
            title = "Sample Educational Video 1",
            channelName = "Kids Learning Channel",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/maxresdefault.jpg",
            views = "1.2M views",
            uploadTime = "2 days ago",
            duration = "5:30",
            youtubeUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        ),
        Video(
            id = "jNQXAC9IVRw",
            title = "Sample Educational Video 2",
            channelName = "Educational Kids",
            thumbnailUrl = "https://img.youtube.com/vi/jNQXAC9IVRw/maxresdefault.jpg",
            views = "850K views",
            uploadTime = "1 week ago",
            duration = "8:15",
            youtubeUrl = "https://www.youtube.com/watch?v=jNQXAC9IVRw"
        ),
        Video(
            id = "9bZkp7q19f0",
            title = "Sample Learning Video 3",
            channelName = "Fun Learning",
            thumbnailUrl = "https://img.youtube.com/vi/9bZkp7q19f0/maxresdefault.jpg",
            views = "2.1M views",
            uploadTime = "3 days ago",
            duration = "6:45",
            youtubeUrl = "https://www.youtube.com/watch?v=9bZkp7q19f0"
        )
    )
    
    val curatedShorts = listOf(
        Video(
            id = "dQw4w9WgXcQ",
            title = "Quick Learning Moment 1",
            channelName = "Kids Shorts",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/maxresdefault.jpg",
            views = "500K views",
            uploadTime = "1 day ago",
            duration = "0:45",
            youtubeUrl = "https://www.youtube.com/shorts/dQw4w9WgXcQ"
        ),
        Video(
            id = "jNQXAC9IVRw",
            title = "Fun Fact Short 2",
            channelName = "Educational Shorts",
            thumbnailUrl = "https://img.youtube.com/vi/jNQXAC9IVRw/maxresdefault.jpg",
            views = "750K views",
            uploadTime = "2 days ago",
            duration = "0:30",
            youtubeUrl = "https://www.youtube.com/shorts/jNQXAC9IVRw"
        ),
        Video(
            id = "9bZkp7q19f0",
            title = "Amazing Discovery",
            channelName = "Science for Kids",
            thumbnailUrl = "https://img.youtube.com/vi/9bZkp7q19f0/maxresdefault.jpg",
            views = "1.2M views",
            uploadTime = "5 hours ago",
            duration = "0:55",
            youtubeUrl = "https://www.youtube.com/shorts/9bZkp7q19f0"
        )
    )
    
    fun getVideoById(id: String): Video? {
        return (curatedVideos + curatedShorts).find { it.id == id }
    }
    
    fun getAllVideos() = curatedVideos
    
    fun getAllShorts() = curatedShorts
}
