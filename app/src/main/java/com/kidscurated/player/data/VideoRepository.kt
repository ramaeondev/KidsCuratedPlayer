package com.kidscurated.player.data

data class Video(
    val id: String,
    val title: String,
    val channelName: String,
    val thumbnailUrl: String,
    val views: String,
    val uploadTime: String,
    val duration: String,
    val youtubeUrl: String,
    val isShort: Boolean = false
)

object VideoRepository {
    // Curated Telugu Rhymes for Children
    // These videos are safe, educational, and perfect for kids!
    
    val curatedVideos = listOf(
        Video(
            id = "VMXHQRLRywY",
            title = "Top 25 Telugu Rhymes for Children - Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/VMXHQRLRywY/maxresdefault.jpg",
            views = "19 crore views",
            uploadTime = "9 years ago",
            duration = "25:12",
            youtubeUrl = "https://www.youtube.com/watch?v=VMXHQRLRywY"
        ),
        Video(
            id = "giVecL2ANv8",
            title = "Dam Dam Dam - 3D Animation Telugu Rhymes for Children",
            channelName = "CVS 3D Rhymes & Kids Songs",
            thumbnailUrl = "https://img.youtube.com/vi/giVecL2ANv8/maxresdefault.jpg",
            views = "33 crore views",
            uploadTime = "12 years ago",
            duration = "3:08",
            youtubeUrl = "https://www.youtube.com/watch?v=giVecL2ANv8"
        ),
        Video(
            id = "kE8K0WomVSQ",
            title = "Kothi Bava | Telugu Rhymes For Children | Mango Telugu Rhymes",
            channelName = "Mango Telugu Rhymes",
            thumbnailUrl = "https://img.youtube.com/vi/kE8K0WomVSQ/maxresdefault.jpg",
            views = "70 lakh views",
            uploadTime = "4 months ago",
            duration = "2:28",
            youtubeUrl = "https://www.youtube.com/watch?v=kE8K0WomVSQ"
        ),
        Video(
            id = "OSDPdzTi_yQ",
            title = "మా తాత అందం - Telugu Nursery Rhymes for Kids",
            channelName = "Kids Zone",
            thumbnailUrl = "https://img.youtube.com/vi/OSDPdzTi_yQ/maxresdefault.jpg",
            views = "71 lakh views",
            uploadTime = "5 months ago",
            duration = "1:18",
            youtubeUrl = "https://www.youtube.com/watch?v=OSDPdzTi_yQ"
        ),
        Video(
            id = "phYVw1ZrSr8",
            title = "తారంగం తారంగం | Telugu Nursery Rhymes For Kids | KidsOne Telugu",
            channelName = "KidsOne Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/phYVw1ZrSr8/maxresdefault.jpg",
            views = "27 lakh views",
            uploadTime = "11 months ago",
            duration = "2:17",
            youtubeUrl = "https://www.youtube.com/watch?v=phYVw1ZrSr8"
        ),
        Video(
            id = "VsmS35KaUyk",
            title = "Telugu Rhymes for Children Vol. 1 - 3D Chitti Chilakamma and 23 Rhymes",
            channelName = "CVS 3D Rhymes & Kids Songs",
            thumbnailUrl = "https://img.youtube.com/vi/VsmS35KaUyk/maxresdefault.jpg",
            views = "36 crore views",
            uploadTime = "5 years ago",
            duration = "38:27",
            youtubeUrl = "https://www.youtube.com/watch?v=VsmS35KaUyk"
        ),
        Video(
            id = "4TpW-Qfjd-0",
            title = "Chitti Chilakamma Amma Kottinda | Telugu Rhymes for Children | Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/4TpW-Qfjd-0/maxresdefault.jpg",
            views = "32 crore views",
            uploadTime = "2 years ago",
            duration = "5:57",
            youtubeUrl = "https://www.youtube.com/watch?v=4TpW-Qfjd-0"
        ),
        Video(
            id = "RqLstPFSJ0o",
            title = "జామ చెట్టు | Telugu Rhymes for Children by infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/RqLstPFSJ0o/maxresdefault.jpg",
            views = "7.1 crore views",
            uploadTime = "9 years ago",
            duration = "1:00",
            youtubeUrl = "https://www.youtube.com/watch?v=RqLstPFSJ0o"
        ),
        Video(
            id = "0F6WRYemPRE",
            title = "Gundrani Gundrati Laddu | Telugu Rhymes for Children | Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/0F6WRYemPRE/maxresdefault.jpg",
            views = "84 crore views",
            uploadTime = "2 years ago",
            duration = "3:14",
            youtubeUrl = "https://www.youtube.com/watch?v=0F6WRYemPRE"
        ),
        Video(
            id = "2kUaylNjr4M",
            title = "Telugu Rhymes for Children | 27 Telugu Nursery Rhymes Collection | Telugu Baby Songs",
            channelName = "CVS 3D Rhymes & Kids Songs",
            thumbnailUrl = "https://img.youtube.com/vi/2kUaylNjr4M/maxresdefault.jpg",
            views = "30 crore views",
            uploadTime = "9 years ago",
            duration = "31:43",
            youtubeUrl = "https://www.youtube.com/watch?v=2kUaylNjr4M"
        )
    )
    
    val curatedShorts = listOf(
        Video(
            id = "yvoLY8U0IU4",
            title = "Kaki Kaki Kadavala Kaki | Telugu Rhymes & Kids Songs | Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/yvoLY8U0IU4/maxresdefault.jpg",
            views = "1.3 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/yvoLY8U0IU4",
            isShort = true
        ),
        Video(
            id = "nlOeOkkFDic",
            title = "Telugu Kids Rhymes | Enugamma Enugu | #telugurhymes - Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/nlOeOkkFDic/maxresdefault.jpg",
            views = "49 lakh views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/nlOeOkkFDic",
            isShort = true
        ),
        Video(
            id = "twalaOG9CGk",
            title = "Chandamama Raave | Telugu Rhymes for Kids & Babies | Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/twalaOG9CGk/maxresdefault.jpg",
            views = "1 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/twalaOG9CGk",
            isShort = true
        ),
        Video(
            id = "nB5XtiJIRN4",
            title = "Telugu Rhymes - Nedu Mangaḷavaram DOCTOR SONG | Butta Bomma Telugu Rhymes For Children",
            channelName = "Butta Bomma Telugu Rhymes For Children",
            thumbnailUrl = "https://img.youtube.com/vi/nB5XtiJIRN4/maxresdefault.jpg",
            views = "1.2 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/nB5XtiJIRN4",
            isShort = true
        ),
        Video(
            id = "ZDJ-dMkf7Ng",
            title = "Tappetloy Talaloyi - Little Krishna | Telugu Rhymes & Baby Songs | Infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/ZDJ-dMkf7Ng/maxresdefault.jpg",
            views = "2.1 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/ZDJ-dMkf7Ng",
            isShort = true
        ),
        Video(
            id = "7dnIde8lgrk",
            title = "Vache Vache Railu Bandi - Fun Telugu Animated Train Song for Children",
            channelName = "KidsOne",
            thumbnailUrl = "https://img.youtube.com/vi/7dnIde8lgrk/maxresdefault.jpg",
            views = "90K views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/7dnIde8lgrk",
            isShort = true
        ),
        Video(
            id = "wTXwh9rXJKU",
            title = "చిట్టి చిలకమ్మ | Chitti Chilakamma | Telugu Rhymes For Children | Kidsone Telugu",
            channelName = "Kidsone Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/wTXwh9rXJKU/maxresdefault.jpg",
            views = "31 lakh views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/wTXwh9rXJKU",
            isShort = true
        ),
        Video(
            id = "z_O5EhnLFZU",
            title = "Holi Vaccindi - Holi Song | Telugu Rhymes | infobells",
            channelName = "infobells - Telugu",
            thumbnailUrl = "https://img.youtube.com/vi/z_O5EhnLFZU/maxresdefault.jpg",
            views = "13 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/z_O5EhnLFZU",
            isShort = true
        ),
        Video(
            id = "qTHpKwGTtMw",
            title = "Bath Song Part 1 Telugu Shorts | Baby Ronnie | Minnu and Mintu Telugu Nursery Rhyme",
            channelName = "Videogyan Telugu - Nursery Rhymes & Kids Songs",
            thumbnailUrl = "https://img.youtube.com/vi/qTHpKwGTtMw/maxresdefault.jpg",
            views = "79 crore views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/qTHpKwGTtMw",
            isShort = true
        ),
        Video(
            id = "v3UWXUK1dRA",
            title = "Chitti Chilakamma - Parrot Song | Butta Bomma Telugu Babies Rhymes",
            channelName = "Butta Bomma Telugu Babies Rhymes",
            thumbnailUrl = "https://img.youtube.com/vi/v3UWXUK1dRA/maxresdefault.jpg",
            views = "54 lakh views",
            uploadTime = "Recent",
            duration = "0:60",
            youtubeUrl = "https://www.youtube.com/shorts/v3UWXUK1dRA",
            isShort = true
        )
    )
    
    fun getVideoById(id: String): Video? {
        return (curatedVideos + curatedShorts).find { it.id == id }
    }
    
    fun getAllVideos() = curatedVideos
    
    fun getAllShorts() = curatedShorts
}
