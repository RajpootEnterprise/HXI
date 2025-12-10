
# MongoDB Schema — HXI Platform
Database: **hxi_raw**

MongoDB stores raw, high-volume, unstructured, real-time event data.

This is the primary data lake for the analytics pipeline.

---

# 📘 COLLECTIONS OVERVIEW

| Collection | Purpose |
|-----------|---------|
| events | Raw SDK UI events |
| sessions | Raw session-level info |
| replay_chunks | Replay frames |
| emotion_predictions | Raw AI classifications |
| latency_metrics | API performance logs |
| journey_steps | Navigation data |
| rage_clicks | Rage click detection |

---

# 🟩 events
```json
{
  "sessionId": "uuid",
  "eventType": "click",
  "screen": "Home",
  "elementId": "btn-login",
  "x": 120,
  "y": 450,
  "timestamp": "2025-01-15T10:15:20Z",
  "metadata": { "color": "red" }
}
````

Indexes:

```js
db.events.createIndex({ sessionId: 1 });
db.events.createIndex({ eventType: 1 });
```

---

# 🟩 sessions

```json
{
  "sessionId": "uuid",
  "userId": "uuid",
  "ip": "192.168.1.10",
  "device": "Android",
  "startTime": ISODate()
}
```

---

# 🟩 replay_chunks

```json
{
  "sessionId": "uuid",
  "chunkIndex": 0,
  "data": {},
  "recordedAt": ISODate()
}
```

Index:

```js
db.replay_chunks.createIndex({ sessionId: 1, chunkIndex: 1 });
```

---

# 🟩 emotion_predictions

```json
{
  "sessionId": "uuid",
  "eventId": "xyz123",
  "predictedEmotion": "frustrated",
  "confidence": 0.81,
  "timestamp": ISODate()
}
```

---

# 🟩 latency_metrics

```json
{
  "sessionId": "uuid",
  "endpoint": "/api/login",
  "latency": 300,
  "status": 200,
  "timestamp": ISODate()
}
```

---

# 🟩 journey_steps

```json
{
  "sessionId": "uuid",
  "stepNumber": 3,
  "screen": "Checkout",
  "timestamp": ISODate()
}
```

---

# 🟩 rage_clicks

```json
{
  "sessionId": "uuid",
  "elementId": "btn-pay",
  "clicks": 8,
  "timestamp": ISODate()
}
```