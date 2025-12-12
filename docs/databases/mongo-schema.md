
# MongoDB Schema

# 📘 COLLECTIONS

| Collection | Purpose |
|-----------|---------|
| events | Raw UI events from SDK |
| sessions | Raw session start/stop and metadata |
| replay_chunks | Raw replay event frames |
| emotion_predictions | Raw emotion classification |
| latency_metrics | Raw latency data |
| journey_steps | Screen transitions |
| rage_clicks | Unprocessed rage-click signals |

---

# 🟩 `events`
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
  "metadata": {
    "color": "red",
    "target": "button"
  }
}
````

Indexes:

```js
db.events.createIndex({ sessionId: 1 });
db.events.createIndex({ type: 1 });
db.events.createIndex({ screen: 1 });
```

---

# 🟩 `sessions`

```json
{
  "sessionId": "uuid",
  "userId": "uuid",
  "device": "Android",
  "ip": "192.168.1.10",
  "startedAt": ISODate(),
  "userAgent": "Mozilla"
}
```

---

# 🟩 `replay_chunks`

```json
{
  "sessionId": "uuid",
  "chunkIndex": 0,
  "data": { /* raw replay frame */ },
  "timestamp": ISODate()
}
```

Index:

```js
db.replay_chunks.createIndex({ sessionId: 1, chunkIndex: 1 });
```

---

# 🟩 `emotion_predictions`

```json
{
  "sessionId": "uuid",
  "eventId": "xyz",
  "emotion": "frustrated",
  "confidence": 0.82,
  "timestamp": ISODate()
}
```

---

# 🟩 `latency_metrics`

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

# 🟩 `journey_steps`

```json
{
  "sessionId": "uuid",
  "step": 3,
  "screen": "Checkout",
  "timestamp": ISODate()
}
```

---

# 🟩 `rage_clicks`

```json
{
  "sessionId": "uuid",
  "elementId": "btn-pay",
  "clickCount": 8,
  "timestamp": ISODate()
}
```