# crud_module.py
# MongoDB CRUD module (CS 340 / CS 499-ready) with validation + safer query handling.

from pymongo import MongoClient
from pymongo.errors import PyMongoError


class AnimalShelter:
    # Connect to MongoDB database/collection
    def __init__(self, username, password, host="localhost", port=27017, db_name="AAC", collection_name="animals"):
        self.client = None  # holds Mongo client connection
        self.database = None  # holds database reference
        self.collection = None  # holds collection reference

        try:
            uri = f"mongodb://{username}:{password}@{host}:{port}"
            self.client = MongoClient(uri)
            self.database = self.client[db_name]
            self.collection = self.database[collection_name]
        except PyMongoError as e:
            print(f"Could not connect to MongoDB: {e}")

    # Close MongoDB connection (good practice for cleanup)
    def close(self):
        if self.client:
            self.client.close()

    # CREATE with validation
    def create(self, data):
        # Must be a dictionary
        if not isinstance(data, dict):
            print("Error: data must be a dictionary.")
            return False

        # Required fields for a valid record
        required_fields = ["animal_id", "name", "species", "age", "breed"]

        # Validate required fields exist and are not empty
        for field in required_fields:
            if field not in data:
                print(f"Error: Missing required field '{field}'.")
                return False
            if data[field] is None or str(data[field]).strip() == "":
                print(f"Error: Field '{field}' cannot be empty.")
                return False

        # Validate age is an integer and non-negative
        try:
            data["age"] = int(data["age"])
            if data["age"] < 0:
                print("Error: Age cannot be negative.")
                return False
        except (ValueError, TypeError):
            print("Error: Age must be a valid integer.")
            return False

        # Insert document
        try:
            result = self.collection.insert_one(data)
            return result.acknowledged
        except PyMongoError as e:
            print(f"Couldn't add data: {e}")
            return False

    # READ with safer query validation
    def read(self, query):
        # Query must be a dict (or None meaning "all")
        if query is None:
            query = {}
        if not isinstance(query, dict):
            print("Error: query must be a dictionary.")
            return []

        try:
            return list(self.collection.find(query))
        except PyMongoError as e:
            print(f"Couldn't read data: {e}")
            return []

    # UPDATE with safer validation
    def update(self, query, new_values):
        if not isinstance(query, dict) or not query:
            print("Error: query must be a non-empty dictionary.")
            return 0
        if not isinstance(new_values, dict) or not new_values:
            print("Error: new_values must be a non-empty dictionary.")
            return 0

        try:
            result = self.collection.update_many(query, {"$set": new_values})
            return result.modified_count
        except PyMongoError as e:
            print(f"Couldn't update data: {e}")
            return 0

    # DELETE with safer validation
    def delete(self, query):
        if not isinstance(query, dict) or not query:
            print("Error: query must be a non-empty dictionary.")
            return 0

        try:
            result = self.collection.delete_many(query)
            return result.deleted_count
        except PyMongoError as e:
            print(f"Couldn't delete data: {e}")
            return 0
