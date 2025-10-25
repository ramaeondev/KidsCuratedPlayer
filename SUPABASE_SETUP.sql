-- ============================================
-- SUPABASE DATABASE SETUP FOR KIDS CURATED PLAYER
-- ============================================
-- Run this entire script in your Supabase SQL Editor
-- Project: mpudbsgszekwghohwcwf

-- Step 1: Create the videos table
CREATE TABLE IF NOT EXISTS videos (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    channelName TEXT NOT NULL,
    thumbnailUrl TEXT NOT NULL,
    views TEXT NOT NULL,
    uploadTime TEXT NOT NULL,
    duration TEXT NOT NULL,
    youtubeUrl TEXT NOT NULL,
    isShort BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Step 2: Enable Row Level Security (RLS)
ALTER TABLE videos ENABLE ROW LEVEL SECURITY;

-- Step 3: Create policy to allow public read access (IMPORTANT!)
-- This allows the app to read data without authentication
CREATE POLICY "Allow public read access" 
ON videos 
FOR SELECT 
USING (true);

-- Step 4: Create policy to allow authenticated insert/update/delete (optional)
-- Uncomment if you want to manage content through authenticated API calls
-- CREATE POLICY "Allow authenticated insert" ON videos FOR INSERT WITH CHECK (auth.role() = 'authenticated');
-- CREATE POLICY "Allow authenticated update" ON videos FOR UPDATE USING (auth.role() = 'authenticated');
-- CREATE POLICY "Allow authenticated delete" ON videos FOR DELETE USING (auth.role() = 'authenticated');

-- Step 5: Insert sample Telugu rhyme videos (10 regular videos)
INSERT INTO videos (id, title, channelName, thumbnailUrl, views, uploadTime, duration, youtubeUrl, isShort) VALUES
('VMXHQRLRywY', 'Top 25 Telugu Rhymes for Children - Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/VMXHQRLRywY/maxresdefault.jpg', '19 crore views', '9 years ago', '25:12', 'https://www.youtube.com/watch?v=VMXHQRLRywY', false),
('giVecL2ANv8', 'Dam Dam Dam - 3D Animation Telugu Rhymes for Children', 'CVS 3D Rhymes & Kids Songs', 'https://img.youtube.com/vi/giVecL2ANv8/maxresdefault.jpg', '33 crore views', '12 years ago', '3:08', 'https://www.youtube.com/watch?v=giVecL2ANv8', false),
('kE8K0WomVSQ', 'Kothi Bava | Telugu Rhymes For Children | Mango Telugu Rhymes', 'Mango Telugu Rhymes', 'https://img.youtube.com/vi/kE8K0WomVSQ/maxresdefault.jpg', '70 lakh views', '4 months ago', '2:28', 'https://www.youtube.com/watch?v=kE8K0WomVSQ', false),
('OSDPdzTi_yQ', 'మా తాత అందం - Telugu Nursery Rhymes for Kids', 'Kids Zone', 'https://img.youtube.com/vi/OSDPdzTi_yQ/maxresdefault.jpg', '71 lakh views', '5 months ago', '1:18', 'https://www.youtube.com/watch?v=OSDPdzTi_yQ', false),
('phYVw1ZrSr8', 'తారంగం తారంగం | Telugu Nursery Rhymes For Kids | KidsOne Telugu', 'KidsOne Telugu', 'https://img.youtube.com/vi/phYVw1ZrSr8/maxresdefault.jpg', '27 lakh views', '11 months ago', '2:17', 'https://www.youtube.com/watch?v=phYVw1ZrSr8', false),
('VsmS35KaUyk', 'Telugu Rhymes for Children Vol. 1 - 3D Chitti Chilakamma and 23 Rhymes', 'CVS 3D Rhymes & Kids Songs', 'https://img.youtube.com/vi/VsmS35KaUyk/maxresdefault.jpg', '36 crore views', '5 years ago', '38:27', 'https://www.youtube.com/watch?v=VsmS35KaUyk', false),
('4TpW-Qfjd-0', 'Chitti Chilakamma Amma Kottinda | Telugu Rhymes for Children | Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/4TpW-Qfjd-0/maxresdefault.jpg', '32 crore views', '2 years ago', '5:57', 'https://www.youtube.com/watch?v=4TpW-Qfjd-0', false),
('RqLstPFSJ0o', 'జామ చెట్టు | Telugu Rhymes for Children by infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/RqLstPFSJ0o/maxresdefault.jpg', '7.1 crore views', '9 years ago', '1:00', 'https://www.youtube.com/watch?v=RqLstPFSJ0o', false),
('0F6WRYemPRE', 'Gundrani Gundrati Laddu | Telugu Rhymes for Children | Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/0F6WRYemPRE/maxresdefault.jpg', '84 crore views', '2 years ago', '3:14', 'https://www.youtube.com/watch?v=0F6WRYemPRE', false),
('2kUaylNjr4M', 'Telugu Rhymes for Children | 27 Telugu Nursery Rhymes Collection | Telugu Baby Songs', 'CVS 3D Rhymes & Kids Songs', 'https://img.youtube.com/vi/2kUaylNjr4M/maxresdefault.jpg', '30 crore views', '9 years ago', '31:43', 'https://www.youtube.com/watch?v=2kUaylNjr4M', false);

-- Step 6: Insert sample Telugu rhyme shorts (10 shorts)
INSERT INTO videos (id, title, channelName, thumbnailUrl, views, uploadTime, duration, youtubeUrl, isShort) VALUES
('yvoLY8U0IU4', 'Kaki Kaki Kadavala Kaki | Telugu Rhymes & Kids Songs | Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/yvoLY8U0IU4/maxresdefault.jpg', '1.3 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/yvoLY8U0IU4', true),
('nlOeOkkFDic', 'Telugu Kids Rhymes | Enugamma Enugu | #telugurhymes - Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/nlOeOkkFDic/maxresdefault.jpg', '49 lakh views', 'Recent', '0:60', 'https://www.youtube.com/shorts/nlOeOkkFDic', true),
('twalaOG9CGk', 'Chandamama Raave | Telugu Rhymes for Kids & Babies | Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/twalaOG9CGk/maxresdefault.jpg', '1 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/twalaOG9CGk', true),
('nB5XtiJIRN4', 'Telugu Rhymes - Nedu Mangaḷavaram DOCTOR SONG | Butta Bomma Telugu Rhymes For Children', 'Butta Bomma Telugu Rhymes For Children', 'https://img.youtube.com/vi/nB5XtiJIRN4/maxresdefault.jpg', '1.2 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/nB5XtiJIRN4', true),
('ZDJ-dMkf7Ng', 'Tappetloy Talaloyi - Little Krishna | Telugu Rhymes & Baby Songs | Infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/ZDJ-dMkf7Ng/maxresdefault.jpg', '2.1 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/ZDJ-dMkf7Ng', true),
('7dnIde8lgrk', 'Vache Vache Railu Bandi - Fun Telugu Animated Train Song for Children', 'KidsOne', 'https://img.youtube.com/vi/7dnIde8lgrk/maxresdefault.jpg', '90K views', 'Recent', '0:60', 'https://www.youtube.com/shorts/7dnIde8lgrk', true),
('wTXwh9rXJKU', 'చిట్టి చిలకమ్మ | Chitti Chilakamma | Telugu Rhymes For Children | Kidsone Telugu', 'Kidsone Telugu', 'https://img.youtube.com/vi/wTXwh9rXJKU/maxresdefault.jpg', '31 lakh views', 'Recent', '0:60', 'https://www.youtube.com/shorts/wTXwh9rXJKU', true),
('z_O5EhnLFZU', 'Holi Vaccindi - Holi Song | Telugu Rhymes | infobells', 'infobells - Telugu', 'https://img.youtube.com/vi/z_O5EhnLFZU/maxresdefault.jpg', '13 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/z_O5EhnLFZU', true),
('qTHpKwGTtMw', 'Bath Song Part 1 Telugu Shorts | Baby Ronnie | Minnu and Mintu Telugu Nursery Rhyme', 'Videogyan Telugu - Nursery Rhymes & Kids Songs', 'https://img.youtube.com/vi/qTHpKwGTtMw/maxresdefault.jpg', '79 crore views', 'Recent', '0:60', 'https://www.youtube.com/shorts/qTHpKwGTtMw', true),
('v3UWXUK1dRA', 'Chitti Chilakamma - Parrot Song | Butta Bomma Telugu Babies Rhymes', 'Butta Bomma Telugu Babies Rhymes', 'https://img.youtube.com/vi/v3UWXUK1dRA/maxresdefault.jpg', '54 lakh views', 'Recent', '0:60', 'https://www.youtube.com/shorts/v3UWXUK1dRA', true);

-- Step 7: Verify the data was inserted
SELECT COUNT(*) as total_videos, 
       SUM(CASE WHEN isShort = false THEN 1 ELSE 0 END) as regular_videos,
       SUM(CASE WHEN isShort = true THEN 1 ELSE 0 END) as shorts
FROM videos;

-- Expected output: total_videos=20, regular_videos=10, shorts=10

-- ============================================
-- IMPORTANT NOTES:
-- ============================================
-- 1. The RLS policy "Allow public read access" is CRITICAL
--    Without it, your app cannot fetch data
-- 2. Make sure to run ALL of the above commands
-- 3. Check the final SELECT query to verify data was inserted
-- 4. If you get errors, try running sections separately
-- ============================================
