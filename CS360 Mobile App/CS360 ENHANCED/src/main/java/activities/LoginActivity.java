package com.example.inventoryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoryapp.R;
import com.example.inventoryapp.DatabaseHelper;

// 👇 THIS IMPORT IS THE FIX
import com.example.inventoryapp.activities.InventoryActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(v -> {
            String user = usernameInput.getText().toString();
            String pass = passwordInput.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUser(user, pass)) {
                // 👇 THIS NOW RESOLVES
                startActivity(new Intent(LoginActivity.this, InventoryActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(v -> {
            String user = usernameInput.getText().toString();
            String pass = passwordInput.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            db.addUser(user, pass);
            Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
        });
    }
}
