package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;

public class HomeActivity extends AppCompatActivity {

    private TextView userNameText;
    private RelativeLayout startWorkoutButton;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        tokenManager = retrofitClient.getTokenManager();

        initializeViews();
        loadUserInfo();
        setupListeners();
    }

    private void initializeViews() {
        userNameText = findViewById(R.id.userNameText);
        startWorkoutButton = findViewById(R.id.startWorkoutButton);
    }

    private void loadUserInfo() {
        String userName = tokenManager.getUserName();
        if (userName != null && !userName.isEmpty()) {
            userNameText.setText(userName);
        }
    }

    private void setupListeners() {
        startWorkoutButton.setOnClickListener(v -> {
            // Navigate to muscle selection
            Intent intent = new Intent(HomeActivity.this, MuscleSelectionActivity.class);
            startActivity(intent);
        });
    }
}
