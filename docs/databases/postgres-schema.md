# PostgreSQL Schema

## users
```
CREATE TABLE IF NOT EXISTS users (
  user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  external_user_id VARCHAR(255),
  email VARCHAR(255),
  locale VARCHAR(50),
  device_type VARCHAR(50),
  created_at TIMESTAMP DEFAULT now()
);
```

## sessions
```
CREATE TABLE IF NOT EXISTS sessions (
  session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(user_id) ON DELETE SET NULL,
  device_id VARCHAR(255),
  ip_address VARCHAR(50),
  user_agent TEXT,
  screen_resolution VARCHAR(20),
  app_version VARCHAR(20),
  started_at TIMESTAMP DEFAULT now(),
  ended_at TIMESTAMP,
  duration_ms BIGINT
);
```

CREATE INDEX IF NOT EXISTS idx_sessions_user ON sessions(user_id);
CREATE INDEX IF NOT EXISTS idx_sessions_started ON sessions(started_at);

## events (normalized)
````
CREATE TABLE IF NOT EXISTS events (
  event_id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  event_type VARCHAR(64),
  screen VARCHAR(255),
  element_id VARCHAR(255),
  x INT,
  y INT,
  scroll INT,
  metadata JSONB,
  occurred_at TIMESTAMP DEFAULT now()
);
````

CREATE INDEX IF NOT EXISTS idx_events_session ON events(session_id);
CREATE INDEX IF NOT EXISTS idx_events_type ON events(event_type);
CREATE INDEX IF NOT EXISTS idx_events_screen ON events(screen);

## rage_click_events
````
CREATE TABLE IF NOT EXISTS rage_click_events (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  element_id VARCHAR(255),
  screen VARCHAR(255),
  click_count INT,
  first_click_at TIMESTAMP,
  last_click_at TIMESTAMP,
  detected_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_rage_session ON rage_click_events(session_id);

## heatmap_points (fine-grain)
````
CREATE TABLE IF NOT EXISTS heatmap_points (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  screen VARCHAR(255),
  x INT,
  y INT,
  weight FLOAT DEFAULT 1,
  occurred_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_heatmap_screen ON heatmap_points(screen);

## journey_steps
````
CREATE TABLE IF NOT EXISTS journey_steps (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  step_num INT,
  screen VARCHAR(255),
  occurred_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_journey_session ON journey_steps(session_id);

## latency_metrics
````
CREATE TABLE IF NOT EXISTS latency_metrics (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  api TEXT,
  latency_ms INT,
  status INT,
  occurred_at TIMESTAMP DEFAULT now()
);
````
## emotion_predictions
````
CREATE TABLE IF NOT EXISTS emotion_predictions (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  event_id BIGINT REFERENCES events(event_id) ON DELETE SET NULL,
  emotion VARCHAR(64),
  confidence FLOAT,
  occurred_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_emotion_session ON emotion_predictions(session_id);

## experience_scores
````
CREATE TABLE IF NOT EXISTS experience_scores (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  score INT,
  issues JSONB,
  calculated_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_score_session ON experience_scores(session_id);

## replay_chunks (small JSON blobs)
````
CREATE TABLE IF NOT EXISTS replay_chunks (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE CASCADE,
  chunk_index INT,
  chunk_data JSONB,
  recorded_at TIMESTAMP DEFAULT now()
);
````
CREATE INDEX IF NOT EXISTS idx_replay_session ON replay_chunks(session_id);

## error_logs
````
CREATE TABLE IF NOT EXISTS error_logs (
  id BIGSERIAL PRIMARY KEY,
  session_id UUID REFERENCES sessions(session_id) ON DELETE SET NULL,
  screen VARCHAR(255),
  message TEXT,
  stack TEXT,
  occurred_at TIMESTAMP DEFAULT now()
);
````

### Notes / Best practices

* Use `pg_dump` before schema changes.
* Use migration tooling (Prisma/TypeORM/Flyway) for production changes — do not change schema ad-hoc on prod.
* For very high-volume tables (events, heatmap_points), consider partitioning by time (monthly) or using hypertables (TimescaleDB) if analytics are time-series heavy.
