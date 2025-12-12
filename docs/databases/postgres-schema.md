
# PostgreSQL Schema

# 📘 TABLE OVERVIEW

| Table | Purpose |
|-------|---------|
| users | User identity & attributes |
| sessions | Session-level data |
| events | Normalized UI events |
| rage_click_events | Processed rage-click bursts |
| heatmap_points | Heatmap aggregates |
| journey_steps | User screen flows |
| latency_metrics | API performance |
| emotion_predictions | AI emotion output |
| experience_scores | Final computed UX score |
| replay_chunks | Optimized replay storage |
| error_logs | Collected JS errors |

---

# 🟦 `users`
```sql
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    external_user_id VARCHAR(255),
    email VARCHAR(255),
    locale VARCHAR(50),
    device_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);
````

---

# 🟦 `sessions`

```sql
CREATE TABLE sessions (
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(user_id),
    device_id VARCHAR(255),
    ip_address VARCHAR(50),
    user_agent TEXT,
    screen_resolution VARCHAR(20),
    app_version VARCHAR(20),
    started_at TIMESTAMP DEFAULT NOW(),
    ended_at TIMESTAMP,
    duration_ms BIGINT
);
CREATE INDEX idx_sessions_user ON sessions(user_id);
```

---

# 🟦 `events`

```sql
CREATE TABLE events (
    event_id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    event_type VARCHAR(50),
    screen VARCHAR(255),
    element_id VARCHAR(255),
    x INT, y INT,
    scroll INT,
    metadata JSONB,
    occurred_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_events_session ON events(session_id);
CREATE INDEX idx_events_event_type ON events(event_type);
CREATE INDEX idx_events_screen ON events(screen);
```

---

# 🟦 `rage_click_events`

```sql
CREATE TABLE rage_click_events (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    element_id VARCHAR(255),
    screen VARCHAR(255),
    click_count INT,
    first_click_at TIMESTAMP,
    last_click_at TIMESTAMP,
    detected_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 `heatmap_points`

```sql
CREATE TABLE heatmap_points (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen VARCHAR(255),
    x INT, y INT,
    weight FLOAT DEFAULT 1,
    occurred_at TIMESTAMP DEFAULT NOW()
);
CREATE INDEX idx_heatmap_screen ON heatmap_points(screen);
```

---

# 🟦 `journey_steps`

```sql
CREATE TABLE journey_steps (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    step INT,
    screen VARCHAR(255),
    occurred_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 `latency_metrics`

```sql
CREATE TABLE latency_metrics (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    api TEXT,
    latency_ms INT,
    status INT,
    occurred_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 `emotion_predictions`

```sql
CREATE TABLE emotion_predictions (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    event_id BIGINT REFERENCES events(event_id),
    emotion VARCHAR(50),
    confidence FLOAT,
    occurred_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 `experience_scores`

```sql
CREATE TABLE experience_scores (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    score INT,
    issues JSONB,
    calculated_at TIMESTAMP DEFAULT NOW()
);
```

---

# 🟦 `replay_chunks`

```sql
CREATE TABLE replay_chunks (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    chunk_index INT,
    chunk_data JSONB,
    recorded_at TIMESTAMP DEFAULT NOW()
);
CREATE INDEX idx_replay_session ON replay_chunks(session_id);
```

---

# 🟦 `error_logs`

```sql
CREATE TABLE error_logs (
    id BIGSERIAL PRIMARY KEY,
    session_id UUID REFERENCES sessions(session_id),
    screen VARCHAR(255),
    message TEXT,
    stack TEXT,
    occurred_at TIMESTAMP DEFAULT NOW()
);
```