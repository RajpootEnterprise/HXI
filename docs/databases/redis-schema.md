# Redis Schema — HXI Platform
Redis is used for real-time analytics, caching, live metrics, counters, and streams.

Redis DB: **0 (default)**

---

# 📘 REDIS KEYS OVERVIEW

| Key Pattern | Type | Purpose |
|-------------|-------|---------|
| session:<id> | HASH | Active session cache |
| active_users | ZSET | Track online users |
| events_stream | STREAM | Live events feed |
| rage:<session>:<element> | COUNTER | Rage click detection |
| heatmap:<screen> | HASH | Live heatmap |
| latency:<api> | LIST | Store last 100 latency reads |
| exp_score:<session> | STRING | Cached UX score |
| replay_queue | LIST | Replay processing |

---

# 🟥 session:<session_id>
````

HSET session:12345 user_id 777 device "Android" start_time "10:22"
EXPIRE session:12345 3600

```

---

# 🟥 active_users (sorted set)
```

ZADD active_users 1700000000 user123

```

---

# 🟥 events_stream (Redis STREAM)
```

XADD events_stream * sessionId 123 type click x 190 y 250

```

---

# 🟥 rage:<session>:<element>
```

INCR rage:session123:btn-login

```

---

# 🟥 heatmap:<screen>
```

HINCRBY heatmap:Home "120_330" 1

```

---

# 🟥 latency:<api>
```

LPUSH latency:/api/login 320
LTRIM latency:/api/login 0 100

```

---

# 🟥 exp_score:<session>
```

SET exp_score:session123 78 EX 1800

```

