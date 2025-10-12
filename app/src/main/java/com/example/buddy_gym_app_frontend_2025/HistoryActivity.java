package com.example.buddy_gym_app_frontend_2025;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
}
