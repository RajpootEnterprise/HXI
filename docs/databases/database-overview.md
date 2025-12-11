=========================================================
HXI PLATFORM — COMPLETE DATABASE & STORAGE BLUEPRINT
=========================================================

Your project uses FOUR storage layers:
1. MongoDB
2. PostgreSQL
3. Redis
4. Kafka Topics (Event Streaming)

---------------------------------------------------------
                 1. MONGODB DATABASES
---------------------------------------------------------
Create these INSIDE Mongo Express (http://localhost:8081)

1. hxi_events_db
    - Stores raw, high-volume user events
    - Collection(s):
      • events

2. hxi_session_db
    - Stores complete session data + replay chunks
    - Collection(s):
      • sessions
      • session_events

3. hxi_journey_db
    - Tracks user flows and visited screen sequence
    - Collection(s):
      • journeys

4. hxi_emotion_db
    - Stores AI emotion prediction outputs
    - Collection(s):
      • emotion_predictions

5. hxi_heatmap_db
    - Stores click/scroll coordinates for heatmap engine
    - Collection(s):
      • heatmaps


---------------------------------------------------------
                 2. POSTGRESQL DATABASES
---------------------------------------------------------
Create these INSIDE pgAdmin (http://localhost:5050)

PostgreSQL Server:  
Host: postgres  
User: postgres  
Password: root

CREATE THE FOLLOWING DATABASES:

1. hxi_rageclick_db
    - Purpose: Rage click detection analytics
    - Tables:
      • rage_clicks
      • rage_thresholds

2. hxi_experience_db
    - Purpose: Stores computed user experience scores
    - Tables:
      • experience_score


---------------------------------------------------------
                 3. REDIS (IN-MEMORY DB)
---------------------------------------------------------
Redis does NOT require manual database creation.

Use default database 0.

Used for:
• Temporary counters
• Cached predictions
• Partial session replay buffers

Redis Keys used:
rage:count:{sessionId}
session:temp:{sessionId}
score:cache:{sessionId}


---------------------------------------------------------
                 4. KAFKA TOPICS
---------------------------------------------------------
These are NOT databases — they are event pipelines.

Create topics through Kafka UI (http://localhost:8088)

Kafka Topics Required:
1. user-events
2. rageclick-events
3. emotion-events
4. heatmap-events


---------------------------------------------------------
         FINAL CLEAN STRUCTURE (ALL IN ONE VIEW)
---------------------------------------------------------

MongoDB:
hxi_events_db
- events
hxi_session_db
- sessions
- session_events
hxi_journey_db
- journeys
hxi_emotion_db
- emotion_predictions
hxi_heatmap_db
- heatmaps

PostgreSQL:
hxi_rageclick_db
- rage_clicks
- rage_thresholds
hxi_experience_db
- experience_score

Redis:
DB 0 (default)
- rage:count:{sessionId}
- session:temp:{sessionId}
- score:cache:{sessionId}

Kafka Topics:
- user-events
- rageclick-events
- emotion-events
- heatmap-events

---------------------------------------------------------
            END OF HXI DATABASE BLUEPRINT
---------------------------------------------------------
