# CS 340 MongoDB CRUD Module

This project implements a MongoDB-based CRUD (Create, Read, Update, Delete) module originally developed for CS 340 and enhanced for inclusion in a CS 499 professional ePortfolio. The purpose of this project is to demonstrate the practical application of database concepts, modular software design, defensive programming, and algorithmic reasoning using Python and MongoDB.

The module simulates a backend system for managing records in an animal shelter database. It provides reusable, well-structured methods that allow data to be safely created, retrieved, updated, and deleted. Enhancements made to this project focus on improving reliability, maintainability, security awareness, and clarity of design, aligning the artifact with professional software development standards.

## Technologies Used
- Python 3
- MongoDB
- PyMongo
- Environment Variables for Configuration

## Project Structure
module5cs340/
- __init__.py  
- crud_module.py  
- db_connection.py  
- config.py  
- test_crud.py  
- README.md  

## File Descriptions
The `crud_module.py` file contains the core CRUD logic implemented in the `AnimalShelter` class. This class manages database connections and provides methods for creating, reading, updating, and deleting records. Enhancements include input validation, safer query handling, error checking, and comments explaining design decisions.

The `db_connection.py` file centralizes database connection creation. This separation ensures that connection logic is not duplicated throughout the project and supports cleaner, more maintainable code.

The `config.py` file stores database configuration settings using environment variables rather than hardcoded credentials. This design choice improves security and reflects industry best practices for handling sensitive information.

The `test_crud.py` file provides a simple executable script used to validate CRUD functionality. It demonstrates how each CRUD operation behaves and confirms that database interactions function as expected.

## How to Run
To run this project, MongoDB must be running locally or be accessible remotely. Required dependencies can be installed using `pip install pymongo python-dotenv`. Database credentials must be set using environment variables, including MONGO_USER, MONGO_PASS, MONGO_DB, and MONGO_COLLECTION. The test script should be executed from the parent directory using the command `python -m module5cs340.test_crud`.

## Enhancements Made
Several enhancements were applied to the original CS 340 artifact to prepare it for professional presentation. Input validation was added to prevent invalid or incomplete data from being written to the database. Database configuration was centralized and secured using environment variables. Error handling was improved to prevent runtime failures and provide clearer feedback during database operations. The project structure was reorganized into a reusable Python package to improve maintainability and scalability. Additionally, algorithmic complexity considerations were documented to demonstrate understanding of performance implications.

## Algorithmic Considerations
Create operations are designed to run in constant time, O(1), since they insert a single document. Read operations may run in linear time, O(n), depending on the query and dataset size. Update and delete operations also run in O(n) time based on the number of matching documents. These considerations were documented to demonstrate awareness of algorithmic efficiency and trade-offs.

## Learning Outcomes
This project demonstrates practical database integration using MongoDB, application of CRUD principles, defensive programming techniques, modular and maintainable software design, and basic algorithmic analysis. It also reflects preparation for building scalable, data-driven applications in professional computing environments.

## Author
Maggie Dixon  
Bachelor of Science in Computer Science  
Concentration: Data Analytics

