from fastapi import FastAPI
from routers import emotion, dropoff

app = FastAPI()
app.include_router(emotion.router)
app.include_router(dropoff.router)
