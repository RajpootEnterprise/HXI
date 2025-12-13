# PostgreSQL Schema --- HXI Platform

## hxi_rageclick_db

### rage_clicks

``` sql
CREATE TABLE rage_clicks (
  id SERIAL PRIMARY KEY,
  session_id VARCHAR(255),
  user_id VARCHAR(255),
  click_count INT,
  page VARCHAR(255),
  first_click TIMESTAMP,
  last_click TIMESTAMP,
  created_at TIMESTAMP DEFAULT NOW()
);
```

### rage_thresholds

``` sql
CREATE TABLE rage_thresholds (
  id SERIAL PRIMARY KEY,
  page VARCHAR(255),
  allowed_clicks INT,
  time_window_ms INT,
  created_at TIMESTAMP DEFAULT NOW()
);
```

## hxi_experience_db

### experience_score

``` sql
CREATE TABLE experience_score (
  id SERIAL PRIMARY KEY,
  session_id VARCHAR(255),
  user_id VARCHAR(255),
  score FLOAT,
  emotion_weight FLOAT,
  rage_penalty FLOAT,
  heatmap_weight FLOAT,
  journey_complexity FLOAT,
  created_at TIMESTAMP DEFAULT NOW()
);
```
