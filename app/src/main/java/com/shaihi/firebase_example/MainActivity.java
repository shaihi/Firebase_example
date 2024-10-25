package com.shaihi.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView tv = findViewById(R.id.helloText);
        tv.setText("Hello + " +mAuth.getCurrentUser().getEmail());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        saveMessage("Shai is doing a test");

        Button btnLogout = findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                mAuth.signOut();

                // Redirect to LoginActivity after logout
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Define the method to save a message to Firestore
    private void saveMessage(String messageText) {
        // Reference to Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new document with data to store
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("message", messageText); // Key-value pair to store in Firestore, the key is "message" and the value is messageText
        messageData.put("timestamp", System.currentTimeMillis()); // Add a timestamp

        // Save the message to a "messages" collection
        db.collection("messages")
                .add(messageData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Message saved successfully
                        Toast.makeText(getApplicationContext(), "Message saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to save message
                        Toast.makeText(getApplicationContext(), "Error saving message: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}