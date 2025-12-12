# Kafka Topics — HXI Platform
Kafka does NOT have tables or databases.
It only has **topics** where microservices publish or consume messages.

Topics must be created manually in Kafka UI / Cloud provider.

---

## 1. user-events
Incoming raw events from client SDK.

✔ Produced by: client SDK  
✔ Consumed by: event-intake-service

---

## 2. rageclick-events
Events related to rage-click detection.

✔ Produced by: rageclick-service  
✔ Consumed by: experience-score-service

---

## 3. emotion-events
Emotion prediction results.

✔ Produced by: emotion-prediction-service  
✔ Consumed by: experience-score-service

---

## 4. heatmap-events
Click/scroll coordinate streams.

✔ Produced by: heatmap-engine-service  
✔ Consumed by: experience-score-service
