# db_connection.py
# Central place to build the database object using config settings.

from .config import DB_CONFIG  # package-safe import
from .crud_module import AnimalShelter  # package-safe import


def get_database():
    # Creates and returns a ready-to-use AnimalShelter object
    return AnimalShelter(
        username=DB_CONFIG["username"],
        password=DB_CONFIG["password"],
        host=DB_CONFIG["host"],
        port=DB_CONFIG["port"],
        db_name=DB_CONFIG["db_name"],
        collection_name=DB_CONFIG["collection_name"],
    )
