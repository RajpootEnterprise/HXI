# mongo-schema

## Databases (logical)
- hxi_events_db      (raw user events)
- hxi_session_db     (session metadata & replay chunks)
- hxi_journey_db     (journey / flow events)
- hxi_emotion_db     (emotion inference outputs)
- hxi_heatmap_db     (click/scroll coordinates for heatmaps)

---

## 1) hxi_events_db - Collection: `events`
Purpose: Raw SDK events (write-heavy).

Example document:
```json
{
  "sessionId": "uuid",
  "type": "click",
  "screen": "Home",
  "elementId": "btn-login",
  "x": 120,
  "y": 430,
  "scroll": 200,
  "timestamp": ISODate(),
  "metadata": { "color": "red", "extra": { /* arbitrary */ } }
}
````

Indexes (run in mongo shell):

```js
use hxi_events_db;
db.events.createIndex({ sessionId: 1 });
db.events.createIndex({ type: 1 });
db.events.createIndex({ screen: 1 });
db.events.createIndex({ timestamp: -1 });
```

Validator (optional — helps maintain shape while staying flexible):

```js
db.createCollection("events", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["sessionId", "type", "timestamp"],
      properties: {
        sessionId: { bsonType: "string" },
        type: { bsonType: "string" },
        timestamp: { bsonType: "date" },
        metadata: { bsonType: ["object", "null"] }
      }
    }
  }
});
```

---

## 2) hxi_session_db

Collections: `sessions`, `session_events`, `replay_chunks`

### `sessions` (session summary)

Example:

```json
{
  "sessionId": "uuid",
  "userId": "uuid|null",
  "device": "Android",
  "ip": "1.2.3.4",
  "startedAt": ISODate(),
  "endedAt": ISODate(),
  "durationMs": 12345,
  "userAgent": "...",
  "meta": {}
}
```

Indexes:

```js
use hxi_session_db;
db.sessions.createIndex({ sessionId: 1 }, { unique: true });
db.sessions.createIndex({ userId: 1 });
db.sessions.createIndex({ startedAt: -1 });
```

### `session_events` (ordered events per session)

Example:

```json
{
  "sessionId":"uuid",
  "ts": ISODate(),
  "type":"click",
  "payload": { /* event payload */ }
}
```

Indexes:

```js
db.session_events.createIndex({ sessionId: 1, ts: 1 });
```

### `replay_chunks` (small JSON frames)

Example:

```json
{
  "sessionId": "uuid",
  "chunkIndex": 0,
  "data": { /* compressed frame or events */ },
  "recordedAt": ISODate()
}
```

Indexes:

```js
db.replay_chunks.createIndex({ sessionId: 1, chunkIndex: 1 }, { unique: true });
```

---

## 3) hxi_journey_db - Collection: `journeys`

Purpose: sequences of screens visited; keep small summary per session.

Example:

```json
{
  "sessionId": "uuid",
  "steps": [
    { "step": 1, "screen": "Home", "ts": ISODate() },
    { "step": 2, "screen": "Checkout", "ts": ISODate() }
  ],
  "createdAt": ISODate()
}
```

Indexes:

```js
use hxi_journey_db;
db.journeys.createIndex({ sessionId: 1 });
```

---

## 4) hxi_emotion_db - Collection: `emotion_predictions`

Example:

```json
{
  "sessionId": "uuid",
  "eventId": "string|null",
  "emotion": "frustrated",
  "confidence": 0.82,
  "timestamp": ISODate()
}
```

Indexes:

```js
use hxi_emotion_db;
db.emotion_predictions.createIndex({ sessionId: 1, timestamp: -1 });
```

---

## 5) hxi_heatmap_db - Collection: `heatmaps`

Store click/scroll points. Consider periodic aggregation into Postgres for dashboards.

Example:

```json
{
  "sessionId": "uuid",
  "screen": "Home",
  "x": 120,
  "y": 330,
  "scroll": 200,
  "timestamp": ISODate()
}
```

Indexes:

```js
use hxi_heatmap_db;
db.heatmaps.createIndex({ screen: 1 });
db.heatmaps.createIndex({ sessionId: 1 });
```

---

### Notes / Best practices

* Keep documents small (avoid storing large binary screenshots in these collections; use object store and reference URL).
* Use TTL collections for certain telemetry (e.g., very temporary raw debug data) via `{ expireAfterSeconds }`.
* Use MongoDB aggregation to rollup into PostgreSQL or analytics store for dashboard queries.

