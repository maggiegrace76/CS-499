# config.py
# MongoDB connection settings pulled from environment variables (safer than hardcoding).

import os

DB_CONFIG = {
    "username": os.getenv("MONGO_USER", "aacuser"),
    "password": os.getenv("MONGO_PASS", "password"),
    "host": os.getenv("MONGO_HOST", "localhost"),
    "port": int(os.getenv("MONGO_PORT", "27017")),
    "db_name": os.getenv("MONGO_DB", "AAC"),
    "collection_name": os.getenv("MONGO_COLLECTION", "animals"),
}
