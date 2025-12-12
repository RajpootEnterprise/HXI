# Redis Schema

Redis DB: 0 (default)  
Used for: real-time counters, short-lived caches, streams, temporary buffers.

## Key Patterns & Types

| Key Pattern                    | Type   | TTL (recommended) | Purpose |
|--------------------------------|--------|-------------------|---------|
| session:<sid>                  | HASH   | 1 hour            | temporary session state / metadata |
| session:live:<sid>             | STREAM | 24 hours          | live event stream for real-time dashboards |
| active_users                   | ZSET   | none              | online users sorted by last ping (score = unix ts) |
| rage:<sid>:<element>           | STRING | 5 min             | increment-only counter for burst/rage detection |
| heatmap:<screen>               | HASH   | none              | aggregated heatmap counters (coordinate -> count) |
| latency:<api>                  | LIST   | 7 days            | LPUSH recent latency metrics then LTRIM to N |
| exp_score:<sid>                | STRING | 30 min            | cached UX score |
| replay_queue                   | LIST   | none              | queue of replay chunk IDs to process |

## Usage examples

### session hash
```

HSET session:12345 userId "uuid" device "Android" startedAt "2025-12-12T10:22:00Z"
EXPIRE session:12345 3600

```

### stream (live events)
```

XADD session:live:12345 * type click x 190 y 240 ts 1700000000

```

### zset for active users
```

ZADD active_users 1700000000 user:uuid123

```

### rage counter
```

INCR rage:12345:btn-login
EXPIRE rage:12345:btn-login 300

```

### heatmap increment
```

HINCRBY heatmap:Home "120_330" 1

```

### latency rolling list
```

LPUSH latency:/api/login 320
LTRIM latency:/api/login 0 99   -- keep latest 100

```

### cached experience score
```

SET exp_score:12345 78 EX 1800

```

## Best practices
- Keep keys short and consistent. Use `:` separators.  
- Avoid storing large binary blobs — keep them in object storage (S3) and store URLs.  
- For counters use `INCR` + short TTL; for long-term analytics flush/rollup to Postgres or Mongo.  
- Monitor memory usage & set eviction policy (`volatile-lru` etc.) appropriate to your needs.