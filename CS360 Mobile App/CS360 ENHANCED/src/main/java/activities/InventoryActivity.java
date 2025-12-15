package com.example.inventoryapp.activities; // Matches the folder path: java/activities

import android.Manifest; // Needed for SMS permission
import android.content.pm.PackageManager; // Used to check permission status
import android.os.Bundle; // Used for activity lifecycle
import android.widget.ArrayAdapter; // Used to display inventory list
import android.widget.Button; // UI button
import android.widget.EditText; // Text input fields
import android.widget.ListView; // List display
import android.widget.Toast; // User feedback messages

import androidx.appcompat.app.AppCompatActivity; // Base activity class
import androidx.core.app.ActivityCompat; // Permission handling
import androidx.core.content.ContextCompat; // Permission checking

import com.example.inventoryapp.R; // Access to layout and view IDs
import com.example.inventoryapp.DatabaseHelper; // Database helper for CRUD operations

import java.util.ArrayList; // Used to store inventory items

public class InventoryActivity extends AppCompatActivity {

    // Input fields for item name and quantity
    EditText itemNameInput;
    EditText itemQtyInput;

    // Buttons for inventory actions
    Button addBtn, updateBtn, deleteBtn;

    // ListView to display inventory
    ListView inventoryList;

    // Database helper instance
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connects this activity to its XML layout
        setContentView(R.layout.activity_inventory);

        // Initialize database helper
        db = new DatabaseHelper(this);

        // Link UI elements to their XML IDs
        itemNameInput = findViewById(R.id.itemNameInput);
        itemQtyInput = findViewById(R.id.itemQtyInput);
        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        inventoryList = findViewById(R.id.inventoryList);

        // Load existing inventory items
        loadInventory();

        // Add new inventory item
        addBtn.setOnClickListener(v -> {
            String name = itemNameInput.getText().toString();
            String qtyText = itemQtyInput.getText().toString();

            // Input validation
            if (name.isEmpty() || qtyText.isEmpty()) {
                Toast.makeText(this, "Enter name and quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty = Integer.parseInt(qtyText);
            db.addInventory(name, qty); // Insert into database
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            loadInventory(); // Refresh list
        });

        // Update existing inventory item
        updateBtn.setOnClickListener(v -> {
            String name = itemNameInput.getText().toString();
            String qtyText = itemQtyInput.getText().toString();

            if (name.isEmpty() || qtyText.isEmpty()) {
                Toast.makeText(this, "Enter name and quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty = Integer.parseInt(qtyText);
            db.updateInventory(name, qty); // Update record
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
            loadInventory();
        });

        // Delete inventory item
        deleteBtn.setOnClickListener(v -> {
            String name = itemNameInput.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            db.deleteInventory(name); // Remove from database
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            loadInventory();
        });

        // Request SMS permission as required by project
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1
            );
        }
    }

    // Loads inventory data into the ListView
    private void loadInventory() {
        ArrayList<String> items = db.getInventoryItems(); // Fetch data
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        inventoryList.setAdapter(adapter); // Display data
    }
}
