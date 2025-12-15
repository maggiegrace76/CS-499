package com.example.inventoryapp;

import android.content.ContentValues; // Used to insert/update database rows
import android.content.Context; // Required for database access
import android.database.Cursor; // Used to read query results
import android.database.sqlite.SQLiteDatabase; // SQLite database access
import android.database.sqlite.SQLiteOpenHelper; // Helper class for database management

import java.util.ArrayList; // Used to return inventory data

/**
 * DatabaseHelper manages all local SQLite database operations for the
 * inventory application. This includes user authentication data and
 * inventory CRUD functionality.
 *
 * Separating database logic from UI logic improves maintainability,
 * scalability, and code readability.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name constant
    private static final String DB_NAME = "inventory.db";

    // Database version (increment if schema changes)
    private static final int DB_VERSION = 1;

    // Constructor initializes the database helper
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creates database tables when the app is first launched
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Table for user authentication
        db.execSQL(
                "CREATE TABLE users (" +
                        "username TEXT PRIMARY KEY, " +
                        "password TEXT)"
        );

        // Table for inventory items
        db.execSQL(
                "CREATE TABLE inventory (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "item TEXT, " +
                        "quantity INTEGER)"
        );
    }

    // Handles database upgrades if version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop old tables to prevent conflicts
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS inventory");

        // Recreate tables
        onCreate(db);
    }

    // Checks whether a user exists with matching credentials
    public boolean checkUser(String user, String pass) {

        SQLiteDatabase db = this.getReadableDatabase();

        // Parameterized query helps prevent SQL injection
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{user, pass}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close(); // Prevent memory leaks
        return exists;
    }

    // Adds a new user to the database
    public void addUser(String user, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", user);
        values.put("password", pass);

        db.insert("users", null, values);
    }

    // Adds a new inventory item
    public void addInventory(String item, int quantity) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item", item);
        values.put("quantity", quantity);

        db.insert("inventory", null, values);
    }

    // Updates an existing inventory item
    public void updateInventory(String item, int quantity) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", quantity);

        db.update(
                "inventory",
                values,
                "item = ?",
                new String[]{item}
        );
    }

    // Deletes an inventory item
    public void deleteInventory(String item) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "inventory",
                "item = ?",
                new String[]{item}
        );
    }

    // Retrieves all inventory items as a list of formatted strings
    public ArrayList<String> getInventoryItems() {

        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT item, quantity FROM inventory",
                null
        );

        // Loop through query results
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int qty = cursor.getInt(1);
            items.add(name + " - Qty: " + qty);
        }

        cursor.close(); // Prevent memory leaks
        return items;
    }
}
