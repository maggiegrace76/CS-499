
package com.example.inventoryapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class InventoryActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText itemName, itemQuantity;
    Button addBtn, updateBtn, deleteBtn;
    GridView gridView;
    ArrayAdapter<String> adapter;
    ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        db = new DatabaseHelper(this);
        itemName = findViewById(R.id.itemName);
        itemQuantity = findViewById(R.id.itemQuantity);
        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        gridView = findViewById(R.id.gridView);

        itemList = db.getInventoryItems();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemList);
        gridView.setAdapter(adapter);

        addBtn.setOnClickListener(v -> {
            db.addInventory(itemName.getText().toString(), Integer.parseInt(itemQuantity.getText().toString()));
            refreshGrid();
            sendSms("New item added: " + itemName.getText().toString());
        });

        updateBtn.setOnClickListener(v -> {
            db.updateInventory(itemName.getText().toString(), Integer.parseInt(itemQuantity.getText().toString()));
            refreshGrid();
        });

        deleteBtn.setOnClickListener(v -> {
            db.deleteInventory(itemName.getText().toString());
            refreshGrid();
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    private void refreshGrid() {
        itemList.clear();
        itemList.addAll(db.getInventoryItems());
        adapter.notifyDataSetChanged();
    }

    private void sendSms(String message) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager.getDefault().sendTextMessage("5554", null, message, null, null);
        }
    }
}
