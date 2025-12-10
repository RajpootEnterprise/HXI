
# PostgreSQL Schema — HXI Platform
Database: **hxi_dashboard**

This PostgreSQL database stores all clean, processed analytics that power the HXI Dashboard UI.

---

# 📘 TABLES OVERVIEW

| Table Name | Purpose |
|------------|---------|
| users | All application users |
| sessions | Session metadata |
| events | Normalized UI events |
| rage_click_events | Processed rage click detections |
| heatmap_points | Click/scroll heatmap coordinates |
| journey_steps | Page/screen navigation logs |
| latency_metrics | API latency analytics |
| emotion_predictions | AI-based emotion classifications |
| experience_scores | Final computed UX score |
| session_replay_chunks | Event-based replay data |
| error_logs | JS errors captured from frontend |

---

# 🟦 users
```sql
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    external_user_id VARCHAR(255),
    email VARCHAR(255),
    device_type VARCHAR(50),
    locale VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);
````

---

# 🟦 sessions

```sql
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
```

---

# 🟦 events

```sql
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
```

---

# 🟦 rage_click_events

```sql
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
```

---

# 🟦 heatmap_points

```sql
CREATE TABLE heatmap_points (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    x INT,
    y INT,
    weight FLOAT DEFAULT 1,
    event_time TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 journey_steps

```sql
CREATE TABLE journey_steps (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    step_number INT,
    screen_name VARCHAR(255),
    event_time TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 latency_metrics

```sql
CREATE TABLE latency_metrics (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    api_endpoint TEXT,
    latency_ms INT,
    status_code INT,
    recorded_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 emotion_predictions

```sql
CREATE TABLE emotion_predictions (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    event_id BIGINT REFERENCES events(event_id),
    predicted_emotion VARCHAR(50),
    confidence FLOAT,
    predicted_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 experience_scores

```sql
CREATE TABLE experience_scores (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    score INT,
    issues JSONB,
    generated_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 session_replay_chunks

```sql
CREATE TABLE session_replay_chunks (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    chunk_index INT,
    chunk_data JSONB,
    recorded_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 error_logs

```sql
CREATE TABLE error_logs (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen_name VARCHAR(255),
    error_message TEXT,
    stack_trace TEXT,
    occurred_at TIMESTAMP DEFAULT NOW()
);
```
