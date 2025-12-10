-- PostgreSQL Schema for HXI Platform
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

------------------------------------------------------
-- USERS TABLE
------------------------------------------------------
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    external_user_id VARCHAR(255),
    email VARCHAR(255),
    device_type VARCHAR(50),
    locale VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- SESSIONS TABLE
------------------------------------------------------
CREATE TABLE sessions (
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(user_id),
    device_id VARCHAR(255),
    ip_address VARCHAR(100),
    user_agent TEXT,
    start_time TIMESTAMP DEFAULT NOW(),
    end_time TIMESTAMP,
    duration_ms BIGINT,
    screen_resolution VARCHAR(50),
    app_version VARCHAR(50)
);

CREATE INDEX idx_sessions_user ON sessions(user_id);

------------------------------------------------------
-- EVENTS TABLE
------------------------------------------------------
CREATE TABLE events (
    event_id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    event_type VARCHAR(50),
    page_url TEXT,
    screen_name VARCHAR(255),
    element_id VARCHAR(255),
    element_type VARCHAR(100),
    x INT,
    y INT,
    scroll_position INT,
    timestamp TIMESTAMP DEFAULT NOW(),
    metadata JSONB
);

CREATE INDEX idx_events_session ON events(session_id);
CREATE INDEX idx_events_type ON events(event_type);

------------------------------------------------------
-- RAGE CLICK EVENTS
------------------------------------------------------
CREATE TABLE rage_click_events (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    element_id VARCHAR(255),
    screen_name VARCHAR(255),
    click_count INT,
    first_click_time TIMESTAMP,
    last_click_time TIMESTAMP,
    detected_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- HEATMAP POINTS
------------------------------------------------------
CREATE TABLE heatmap_points (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    x INT,
    y INT,
    weight FLOAT DEFAULT 1,
    event_time TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- JOURNEY STEPS
------------------------------------------------------
CREATE TABLE journey_steps (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    step_number INT,
    screen_name VARCHAR(255),
    event_time TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- LATENCY METRICS
------------------------------------------------------
CREATE TABLE latency_metrics (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    api_endpoint TEXT,
    latency_ms INT,
    status_code INT,
    recorded_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- EMOTION PREDICTIONS
------------------------------------------------------
CREATE TABLE emotion_predictions (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    event_id BIGINT REFERENCES events(event_id),
    predicted_emotion VARCHAR(50),
    confidence FLOAT,
    predicted_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- EXPERIENCE SCORE TABLE
------------------------------------------------------
CREATE TABLE experience_scores (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    score INT,
    issues JSONB,
    generated_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- SESSION REPLAY CHUNKS
------------------------------------------------------
CREATE TABLE session_replay_chunks (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    chunk_index INT,
    chunk_data JSONB,
    recorded_at TIMESTAMP DEFAULT NOW()
);

------------------------------------------------------
-- ERROR LOGS
------------------------------------------------------
CREATE TABLE error_logs (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    error_message TEXT,
    stack_trace TEXT,
    occurred_at TIMESTAMP DEFAULT NOW()
);
