// MongoDB Collections for HXI Platform

db.createCollection("events");
db.createCollection("sessions");
db.createCollection("replay_chunks");
db.createCollection("emotion_predictions");
db.createCollection("latency_metrics");
db.createCollection("rage_clicks");
db.createCollection("journey_steps");

// Example document structures

// EVENTS
db.events.createIndex({ sessionId: 1 });
db.events.createIndex({ eventType: 1 });
db.events.insertOne({
    sessionId: "uuid",
    eventType: "click",
    screen: "Home",
    elementId: "#btn-login",
    x: 120,
    y: 450,
    timestamp: new Date(),
    metadata: {}
});

// SESSION REPLAY CHUNKS
db.replay_chunks.createIndex({ sessionId: 1, chunkIndex: 1 });

// EMOTION PREDICTIONS
db.emotion_predictions.createIndex({ eventId: 1 });

// LATENCY METRICS
db.latency_metrics.createIndex({ sessionId: 1 });

// JOURNEY STEPS
db.journey_steps.createIndex({ sessionId: 1 });

// TTL Example (delete old events after 30 days)
db.events.createIndex({ timestamp: 1 }, { expireAfterSeconds: 2592000 });
