package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.buddy_gym_app_frontend_2025.adapters.HistoryPagerAdapter;
import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HistoryActivity extends AppCompatActivity {

    private TextView userNameText;
    private TextView exerciseTitleText;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button backButton;
    private Toolbar toolbar;

    private int exerciseId;
    private String exerciseName;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        tokenManager = retrofitClient.getTokenManager();

        // Get exercise info from intent
        exerciseId = getIntent().getIntExtra("exercise_id", -1);
        exerciseName = getIntent().getStringExtra("exercise_name");

        initializeViews();
        setupViewPager();
        setupListeners();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        userNameText = findViewById(R.id.userNameText);
        exerciseTitleText = findViewById(R.id.exerciseTitleText);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        backButton = findViewById(R.id.backButton);

        // Set user name
        String userName = tokenManager.getUserName();
        if (userName != null && !userName.isEmpty()) {
            userNameText.setText(userName);
        }

        // Set exercise title
        exerciseTitleText.setText("History: " + exerciseName);
    }

    private void setupViewPager() {
        HistoryPagerAdapter adapter = new HistoryPagerAdapter(this, exerciseId);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.history);
                    } else {
                        tab.setText(R.string.performance);
                    }
                }
        ).attach();
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> finish());
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
