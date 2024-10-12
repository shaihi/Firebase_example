package com.shaihi.firebase_example;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    // Declare the EditText fields for user input (email and password)
    private EditText emailEditText, passwordEditText;

    // Declare the buttons for login and sign up
    private Button loginButton, signupButton;

    // Declare a FirebaseAuth object to handle authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for the activity

        // Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Link the UI elements (EditText and Button) with the corresponding views in the layout
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        // Set an onClickListener for the sign-up button to handle user sign-up
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email and password input from the user
                String email = emailEditText.getText().toString().trim(); // Remove leading/trailing spaces
                String password = passwordEditText.getText().toString().trim(); // Remove leading/trailing spaces

                // Check if the email and password are valid (not empty)
                if (validateInputs(email, password)) {
                    // Create a new user with the provided email and password using Firebase Authentication
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Check if the sign-up operation was successful
                                    if (task.isSuccessful()) {
                                        // Sign-up successful, get the newly created user
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Sign Up Successful.", Toast.LENGTH_SHORT).show();
                                        // You can add code here to navigate to another activity or perform further actions

                                    } else {
                                        // Sign-up failed, show the error message
                                        Toast.makeText(LoginActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        // Set an onClickListener for the login button to handle user login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email and password input from the user
                String email = emailEditText.getText().toString().trim(); // Remove leading/trailing spaces
                String password = passwordEditText.getText().toString().trim(); // Remove leading/trailing spaces

                // Check if the email and password are valid (not empty)
                if (validateInputs(email, password)) {
                    // Log in the user with the provided email and password using Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Check if the login operation was successful
                                    if (task.isSuccessful()) {
                                        // Login successful, get the currently signed-in user
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                        // You can add code here to navigate to another activity or perform further actions

                                    } else {
                                        // Login failed, show the error message
                                        Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    // This method validates that the email and password inputs are not empty
    private boolean validateInputs(String email, String password) {
        // Regular expression for valid email pattern
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        // Regular expression for a strong password pattern
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*_-]).{8,}$";

        // Check if email matches the valid pattern
        if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if password matches the strong password pattern
        if (!password.matches(passwordPattern)) {
            Toast.makeText(this, "Password must be at least 8 characters long, include one uppercase letter, one lowercase letter, one number, and one special character (!@#$%^&*_-)", Toast.LENGTH_LONG).show();
            return false;
        }

        // Both email and password are valid
        return true;
    }
}