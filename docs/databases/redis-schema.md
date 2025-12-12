# Redis Schema

```md

Redis DB: **0 (default)**  
Used for: real-time counters, heatmaps, temporary buffers, latency pipelines, streams.

---

# 📘 REDIS KEYS OVERVIEW

| Key Pattern | Type | TTL | Purpose |
|-------------|------|-----|---------|
| session:<sid> | HASH | 1 hour | Temporary session state |
| session:live:<sid> | STREAM | 24 hours | Live raw events stream |
| active_users | ZSET | none | Track online users by last ping |
| rage:<sid>:<element> | COUNTER | 5 min | Click burst detection |
| heatmap:<screen> | HASH | none | Live heatmap for UI |
| latency:<api> | LIST | 7 days | Rolling latency logs |
| exp_score:<sid> | STRING | 30 min | Cached UX score |
| replay_queue | LIST | none | Queue of replay chunks |

---

# 🟥 `session:<sid>` (HASH)
Stores temporary session data before moving to DB.

```

HSET session:12345 user "[abc@xyz.com](mailto:abc@xyz.com)" device "Android" startedAt "169999"
EXPIRE session:12345 3600

```

---

# 🟥 `session:live:<sid>` (STREAM)
Live firehose for real-time dashboards.

```

XADD session:live:12345 * type click x 190 y 240 ts 169999

```

---

# 🟥 `active_users` (ZSET)
```

ZADD active_users 1700000000 "user123"

```

---

# 🟥 `rage:<sid>:<element>` (COUNTER)
```

INCR rage:12345:btn-login
EXPIRE rage:12345:btn-login 300

```

---

# 🟥 `heatmap:<screen>` (HASH)
```

HINCRBY heatmap:Home "120_330" 1

```

---

# 🟥 `latency:<api>` (Rolling LIST)
```

LPUSH latency:/api/login 320
LTRIM latency:/api/login 0 100

```

---

# 🟥 `exp_score:<sid>`
```

SET exp_score:12345 78 EX 1800