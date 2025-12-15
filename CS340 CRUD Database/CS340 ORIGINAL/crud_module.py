
# crud_module.py
# This file has a class to help connect to MongoDB and do basic database actions: create, read, update, and delete

from pymongo import MongoClient
from pymongo.errors import PyMongoError

class AnimalShelter:
    # This sets up the connection to MongoDB when you make a new AnimalShelter object
    def __init__(self, username, password, host='localhost', port=27017, db_name='AAC', collection_name='animals'):
        try:
            # Connect to the MongoDB database using your username and password
            self.client = MongoClient(f'mongodb://{username}:{password}@{host}:{port}')
            self.database = self.client[db_name]  # Select the database
            self.collection = self.database[collection_name]  # Select the collection (like a table)
        except PyMongoError as e:
            print(f"Could not connect to MongoDB: {e}")

    # This adds one new record to the database
    def create(self, data):
        try:
            result = self.collection.insert_one(data)  # Insert one document
            return result.acknowledged  # Return True if it worked
        except PyMongoError as e:
            print(f"Couldn't add data: {e}")
            return False

    # This finds and returns records that match the search
    def read(self, query):
        try:
            return list(self.collection.find(query))  # Find all documents that match the query
        except PyMongoError as e:
            print(f"Couldn't read data: {e}")
            return []

    # This updates one or more records that match the search
    def update(self, query, new_values):
        try:
            result = self.collection.update_many(query, {'$set': new_values})  # Change values for matching documents
            return result.modified_count  # Show how many were updated
        except PyMongoError as e:
            print(f"Couldn't update data: {e}")
            return 0

    # This deletes one or more records that match the search
    def delete(self, query):
        try:
            result = self.collection.delete_many(query)  # Delete matching documents
            return result.deleted_count  # Show how many were deleted
        except PyMongoError as e:
            print(f"Couldn't delete data: {e}")
            return 0
