package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;

public class HomeActivity extends AppCompatActivity {

    private TextView userNameText;
    private RelativeLayout startWorkoutButton;
    private TokenManager tokenManager;
    private Toolbar toolbar;

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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            performLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performLogout() {
        tokenManager.clearToken();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
