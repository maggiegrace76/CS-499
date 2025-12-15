# test_crud.py
# Quick run test for CRUD operations using the AnimalShelter class.

from module5cs340.db_connection import get_database  # package import

db = get_database()

print("--- TESTING CREATE ---")
new_animal = {
    "animal_id": "A12345",
    "name": "Bella",
    "species": "Dog",
    "age": 4,
    "breed": "Labrador"
}
print("Create result:", db.create(new_animal))

print("--- TESTING READ ---")
print(db.read({"name": "Bella"}))

print("--- TESTING UPDATE ---")
print("Updated:", db.update({"animal_id": "A12345"}, {"age": 5}))

print("--- TESTING DELETE ---")
print("Deleted:", db.delete({"animal_id": "A12345"}))

db.close()
