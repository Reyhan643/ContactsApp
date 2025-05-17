package com.example.firebasecontactcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(a -> {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Login gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
