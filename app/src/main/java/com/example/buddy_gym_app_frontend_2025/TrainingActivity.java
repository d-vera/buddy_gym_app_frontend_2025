package com.example.buddy_gym_app_frontend_2025;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.Training;
import com.example.buddy_gym_app_frontend_2025.models.TrainingStats;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingActivity extends AppCompatActivity {

    private TextView exerciseTitleText;
    private TextView lowWeightText;
    private TextView highWeightText;
    private TextView lastWeightText;
    private EditText actualWeightInput;
    private EditText setsInput;
    private EditText repsInput;
    private EditText notesInput;
    private Button insertButton;
    private Button historyButton;

    private RetrofitClient retrofitClient;
    private int exerciseId;
    private String exerciseName;
    private int muscleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        retrofitClient = RetrofitClient.getInstance(this);

        // Get exercise info from intent
        exerciseId = getIntent().getIntExtra("exercise_id", -1);
        exerciseName = getIntent().getStringExtra("exercise_name");
        muscleId = getIntent().getIntExtra("muscle_id", -1);

        initializeViews();
        loadTrainingStats();
        setupListeners();
    }

    private void initializeViews() {
        exerciseTitleText = findViewById(R.id.exerciseTitleText);
        lowWeightText = findViewById(R.id.lowWeightText);
        highWeightText = findViewById(R.id.highWeightText);
        lastWeightText = findViewById(R.id.lastWeightText);
        actualWeightInput = findViewById(R.id.actualWeightInput);
        setsInput = findViewById(R.id.setsInput);
        repsInput = findViewById(R.id.repsInput);
        notesInput = findViewById(R.id.notesInput);
        insertButton = findViewById(R.id.insertButton);
        historyButton = findViewById(R.id.historyButton);

        exerciseTitleText.setText(exerciseName);
    }

    private void setupListeners() {
        insertButton.setOnClickListener(v -> insertTraining());

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrainingActivity.this, HistoryActivity.class);
            intent.putExtra("exercise_id", exerciseId);
            intent.putExtra("exercise_name", exerciseName);
            intent.putExtra("muscle_id", muscleId);
            startActivity(intent);
        });
    }

    private void loadTrainingStats() {
        retrofitClient.getTrainingService().getTrainingStats(exerciseId, null)
                .enqueue(new Callback<List<TrainingStats>>() {
                    @Override
                    public void onResponse(Call<List<TrainingStats>> call, Response<List<TrainingStats>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            TrainingStats stats = response.body().get(0);
                            lowWeightText.setText(stats.getLowWeight() + " Kg");
                            highWeightText.setText(stats.getHighWeight() + " Kg");
                            lastWeightText.setText(stats.getLastWeight() + " Kg");
                        } else {
                            lowWeightText.setText("0 Kg");
                            highWeightText.setText("0 Kg");
                            lastWeightText.setText("0 Kg");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TrainingStats>> call, Throwable t) {
                        Toast.makeText(TrainingActivity.this, "Failed to load stats", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertTraining() {
        String weightStr = actualWeightInput.getText().toString().trim();
        String setsStr = setsInput.getText().toString().trim();
        String repsStr = repsInput.getText().toString().trim();

        if (weightStr.isEmpty()) {
            actualWeightInput.setError("Weight is required");
            actualWeightInput.requestFocus();
            return;
        }

        if (setsStr.isEmpty()) {
            setsInput.setError("Sets is required");
            setsInput.requestFocus();
            return;
        }

        if (repsStr.isEmpty()) {
            repsInput.setError("Reps is required");
            repsInput.requestFocus();
            return;
        }

        insertButton.setEnabled(false);
        insertButton.setText("Inserting...");

        Training training = new Training(
                exerciseId,
                weightStr,
                Integer.parseInt(setsStr),
                Integer.parseInt(repsStr)
        );

        retrofitClient.getTrainingService().createTraining(training).enqueue(new Callback<Training>() {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                insertButton.setEnabled(true);
                insertButton.setText(R.string.insert);

                if (response.isSuccessful()) {
                    Toast.makeText(TrainingActivity.this, "Training recorded successfully!", Toast.LENGTH_SHORT).show();
                    
                    // Clear inputs
                    actualWeightInput.setText("");
                    setsInput.setText("");
                    repsInput.setText("");
                    notesInput.setText("");
                    
                    // Reload stats
                    loadTrainingStats();
                } else {
                    Toast.makeText(TrainingActivity.this, "Failed to record training", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Training> call, Throwable t) {
                insertButton.setEnabled(true);
                insertButton.setText(R.string.insert);
                Toast.makeText(TrainingActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
