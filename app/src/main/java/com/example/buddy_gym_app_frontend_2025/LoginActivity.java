package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.LoginRequest;
import com.example.buddy_gym_app_frontend_2025.models.LoginResponse;
import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView passwordToggle;
    private Button signInButton;
    private TextView signUpText;
    private boolean isPasswordVisible = false;

    private RetrofitClient retrofitClient;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofitClient = RetrofitClient.getInstance(this);
        tokenManager = retrofitClient.getTokenManager();

        // Check if user is already logged in
        if (tokenManager.isLoggedIn()) {
            navigateToHome();
            return;
        }

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordToggle = findViewById(R.id.passwordToggle);
        signInButton = findViewById(R.id.signInButton);
        signUpText = findViewById(R.id.signUpText);
    }

    private void setupListeners() {
        signInButton.setOnClickListener(v -> performLogin());

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        passwordToggle.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = false;
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordVisible = true;
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    private void performLogin() {
        String emailOrUsername = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (emailOrUsername.isEmpty()) {
            emailEditText.setError("Email or username is required");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        signInButton.setEnabled(false);
        signInButton.setText("Signing in...");

        LoginRequest loginRequest = new LoginRequest(emailOrUsername, password);

        retrofitClient.getAuthService().login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                signInButton.setEnabled(true);
                signInButton.setText(R.string.sign_in);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    
                    // Save token and user info
                    tokenManager.saveToken(loginResponse.getToken());
                    if (loginResponse.getUser() != null) {
                        tokenManager.saveUserInfo(
                                loginResponse.getUser().getId(),
                                loginResponse.getUser().getFullName(),
                                loginResponse.getUser().getEmail()
                        );
                    }

                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    navigateToHome();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                signInButton.setEnabled(true);
                signInButton.setText(R.string.sign_in);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
