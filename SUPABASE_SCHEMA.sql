-- YouKids Analytics Database Schema for Supabase
-- This schema stores anonymous analytics data in compliance with COPPA/GDPR/CCPA

-- ============================================================
-- TABLE: user_installs
-- Tracks unique app installations
-- ============================================================
CREATE TABLE IF NOT EXISTS user_installs (
    id BIGSERIAL PRIMARY KEY,
    user_id TEXT NOT NULL UNIQUE,
    install_time BIGINT NOT NULL,
    device_type TEXT NOT NULL,
    android_version TEXT NOT NULL,
    app_version TEXT NOT NULL,
    country_code TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Index for faster queries
CREATE INDEX idx_user_installs_user_id ON user_installs(user_id);
CREATE INDEX idx_user_installs_install_time ON user_installs(install_time);
CREATE INDEX idx_user_installs_country ON user_installs(country_code);

-- ============================================================
-- TABLE: app_opens
-- Tracks daily/monthly active users (DAU/MAU)
-- ============================================================
CREATE TABLE IF NOT EXISTS app_opens (
    id BIGSERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    timestamp BIGINT NOT NULL,
    open_count INT NOT NULL,
    device_type TEXT NOT NULL,
    android_version TEXT NOT NULL,
    app_version TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Indexes for analytics queries
CREATE INDEX idx_app_opens_user_id ON app_opens(user_id);
CREATE INDEX idx_app_opens_timestamp ON app_opens(timestamp);
CREATE INDEX idx_app_opens_created_at ON app_opens(created_at);

-- ============================================================
-- TABLE: analytics_events
-- Tracks custom events (video plays, button clicks, etc.)
-- ============================================================
CREATE TABLE IF NOT EXISTS analytics_events (
    id BIGSERIAL PRIMARY KEY,
    user_id TEXT NOT NULL,
    event_name TEXT NOT NULL,
    timestamp BIGINT NOT NULL,
    properties JSONB,
    device_type TEXT NOT NULL,
    android_version TEXT NOT NULL,
    app_version TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Indexes for event queries
CREATE INDEX idx_analytics_events_user_id ON analytics_events(user_id);
CREATE INDEX idx_analytics_events_event_name ON analytics_events(event_name);
CREATE INDEX idx_analytics_events_timestamp ON analytics_events(timestamp);
CREATE INDEX idx_analytics_events_created_at ON analytics_events(created_at);

-- ============================================================
-- ANALYTICS VIEWS (for easy querying)
-- ============================================================

-- Daily Active Users (DAU)
CREATE OR REPLACE VIEW dau_stats AS
SELECT 
    DATE(created_at) as date,
    COUNT(DISTINCT user_id) as unique_users
FROM app_opens
WHERE created_at >= NOW() - INTERVAL '30 days'
GROUP BY DATE(created_at)
ORDER BY date DESC;

-- Monthly Active Users (MAU)
CREATE OR REPLACE VIEW mau_stats AS
SELECT 
    DATE_TRUNC('month', created_at) as month,
    COUNT(DISTINCT user_id) as unique_users
FROM app_opens
GROUP BY DATE_TRUNC('month', created_at)
ORDER BY month DESC;

-- Total installs by country
CREATE OR REPLACE VIEW installs_by_country AS
SELECT 
    country_code,
    COUNT(*) as total_installs
FROM user_installs
GROUP BY country_code
ORDER BY total_installs DESC;

-- Device distribution
CREATE OR REPLACE VIEW device_distribution AS
SELECT 
    android_version,
    COUNT(*) as device_count
FROM user_installs
GROUP BY android_version
ORDER BY device_count DESC;

-- Most common events
CREATE OR REPLACE VIEW top_events AS
SELECT 
    event_name,
    COUNT(*) as event_count
FROM analytics_events
WHERE created_at >= NOW() - INTERVAL '7 days'
GROUP BY event_name
ORDER BY event_count DESC
LIMIT 20;

-- ============================================================
-- ROW LEVEL SECURITY (RLS) POLICIES
-- Enable RLS for all tables to secure data
-- ============================================================

-- Enable RLS
ALTER TABLE user_installs ENABLE ROW LEVEL SECURITY;
ALTER TABLE app_opens ENABLE ROW LEVEL SECURITY;
ALTER TABLE analytics_events ENABLE ROW LEVEL SECURITY;

-- Policy: Allow anonymous inserts (for app to write data)
CREATE POLICY "Allow anonymous inserts for user_installs" 
ON user_installs FOR INSERT 
WITH CHECK (true);

CREATE POLICY "Allow anonymous inserts for app_opens" 
ON app_opens FOR INSERT 
WITH CHECK (true);

CREATE POLICY "Allow anonymous inserts for analytics_events" 
ON analytics_events FOR INSERT 
WITH CHECK (true);

-- Policy: Only authenticated users can read (for dashboard)
CREATE POLICY "Allow authenticated reads for user_installs" 
ON user_installs FOR SELECT 
USING (auth.role() = 'authenticated');

CREATE POLICY "Allow authenticated reads for app_opens" 
ON app_opens FOR SELECT 
USING (auth.role() = 'authenticated');

CREATE POLICY "Allow authenticated reads for analytics_events" 
ON analytics_events FOR SELECT 
USING (auth.role() = 'authenticated');

-- ============================================================
-- SAMPLE QUERIES FOR ANALYTICS DASHBOARD
-- ============================================================

-- Total unique users
-- SELECT COUNT(DISTINCT user_id) as total_users FROM user_installs;

-- DAU for last 7 days
-- SELECT * FROM dau_stats LIMIT 7;

-- MAU trend
-- SELECT * FROM mau_stats;

-- Top 10 countries
-- SELECT * FROM installs_by_country LIMIT 10;

-- Android version distribution
-- SELECT * FROM device_distribution;

-- Average app opens per user
-- SELECT AVG(open_count) as avg_opens FROM app_opens;

-- Most active users (top 10)
-- SELECT user_id, COUNT(*) as opens 
-- FROM app_opens 
-- GROUP BY user_id 
-- ORDER BY opens DESC 
-- LIMIT 10;
