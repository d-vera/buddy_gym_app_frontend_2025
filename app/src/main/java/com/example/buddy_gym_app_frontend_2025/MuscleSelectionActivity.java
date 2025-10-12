package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.adapters.MuscleAdapter;
import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.Muscle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuscleSelectionActivity extends AppCompatActivity {

    private static final String TAG = "MuscleSelectionActivity";
    private RecyclerView muscleRecyclerView;
    private MuscleAdapter muscleAdapter;
    private RetrofitClient retrofitClient;
    private ProgressBar progressBar;
    private View errorLayout;
    private TextView errorTextView;
    private android.widget.Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_selection);

        retrofitClient = RetrofitClient.getInstance(this);

        initializeViews();
        loadMuscles();
    }

    private void initializeViews() {
        muscleRecyclerView = findViewById(R.id.muscleRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorLayout = findViewById(R.id.errorLayout);
        errorTextView = findViewById(R.id.errorTextView);
        retryButton = findViewById(R.id.retryButton);
        
        muscleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        muscleAdapter = new MuscleAdapter(muscle -> {
            // Navigate to exercise list for selected muscle
            Intent intent = new Intent(MuscleSelectionActivity.this, ExerciseListActivity.class);
            intent.putExtra("muscle_id", muscle.getId());
            intent.putExtra("muscle_name", muscle.getName());
            startActivity(intent);
        });

        muscleRecyclerView.setAdapter(muscleAdapter);
        
        retryButton.setOnClickListener(v -> loadMuscles());
    }

    private void loadMuscles() {
        progressBar.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        muscleRecyclerView.setVisibility(View.GONE);
        
        Log.d(TAG, "Loading muscles from API...");
        
        retrofitClient.getMuscleService().getMuscles().enqueue(new Callback<List<Muscle>>() {
            @Override
            public void onResponse(Call<List<Muscle>> call, Response<List<Muscle>> response) {
                progressBar.setVisibility(View.GONE);
                
                Log.d(TAG, "Response code: " + response.code());
                
                if (response.isSuccessful() && response.body() != null) {
                    List<Muscle> muscles = response.body();
                    Log.d(TAG, "Loaded " + muscles.size() + " muscles");
                    
                    if (muscles.isEmpty()) {
                        showError("No muscles found in database");
                    } else {
                        muscleRecyclerView.setVisibility(View.VISIBLE);
                        muscleAdapter.setMuscles(muscles);
                    }
                } else {
                    String errorMsg = "Failed to load muscles. Code: " + response.code();
                    Log.e(TAG, errorMsg);
                    showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<Muscle>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                String errorMsg = "Network error: " + t.getMessage();
                Log.e(TAG, errorMsg, t);
                showError(errorMsg);
            }
        });
    }
    
    private void showError(String message) {
        errorTextView.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
        muscleRecyclerView.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
