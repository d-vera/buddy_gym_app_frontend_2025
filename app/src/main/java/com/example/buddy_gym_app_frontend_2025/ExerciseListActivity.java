package com.example.buddy_gym_app_frontend_2025;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.adapters.ExerciseAdapter;
import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseListActivity extends AppCompatActivity {

    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private FloatingActionButton addExerciseFab;
    private RetrofitClient retrofitClient;

    private int muscleId;
    private String muscleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        retrofitClient = RetrofitClient.getInstance(this);

        // Get muscle info from intent
        muscleId = getIntent().getIntExtra("muscle_id", -1);
        muscleName = getIntent().getStringExtra("muscle_name");

        initializeViews();
        loadExercises();
    }

    private void initializeViews() {
        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView);
        addExerciseFab = findViewById(R.id.addExerciseFab);

        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseAdapter = new ExerciseAdapter(new ExerciseAdapter.OnExerciseActionListener() {
            @Override
            public void onExerciseClick(Exercise exercise) {
                // Navigate to training activity
                Intent intent = new Intent(ExerciseListActivity.this, TrainingActivity.class);
                intent.putExtra("exercise_id", exercise.getId());
                intent.putExtra("exercise_name", exercise.getName());
                intent.putExtra("muscle_id", muscleId);
                startActivity(intent);
            }

            @Override
            public void onEditClick(Exercise exercise) {
                showEditExerciseDialog(exercise);
            }

            @Override
            public void onDeleteClick(Exercise exercise) {
                showDeleteConfirmationDialog(exercise);
            }
        });

        exerciseRecyclerView.setAdapter(exerciseAdapter);

        addExerciseFab.setOnClickListener(v -> showAddExerciseDialog());
    }

    private void loadExercises() {
        retrofitClient.getExerciseService().getExercises(muscleId).enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exerciseAdapter.setExercises(response.body());
                } else {
                    Toast.makeText(ExerciseListActivity.this, "Failed to load exercises", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Toast.makeText(ExerciseListActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddExerciseDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exercise, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        EditText exerciseNameInput = dialogView.findViewById(R.id.exerciseNameInput);
        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        dialogTitle.setText(R.string.insert_new_exercise);

        acceptButton.setOnClickListener(v -> {
            String exerciseName = exerciseNameInput.getText().toString().trim();
            if (exerciseName.isEmpty()) {
                exerciseNameInput.setError("Exercise name is required");
                return;
            }

            Exercise newExercise = new Exercise(muscleId, exerciseName, null);
            createExercise(newExercise);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showEditExerciseDialog(Exercise exercise) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exercise, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        EditText exerciseNameInput = dialogView.findViewById(R.id.exerciseNameInput);
        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        dialogTitle.setText(R.string.insert_change);
        exerciseNameInput.setText(exercise.getName());

        acceptButton.setOnClickListener(v -> {
            String exerciseName = exerciseNameInput.getText().toString().trim();
            if (exerciseName.isEmpty()) {
                exerciseNameInput.setError("Exercise name is required");
                return;
            }

            exercise.setName(exerciseName);
            updateExercise(exercise);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDeleteConfirmationDialog(Exercise exercise) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exercise, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        EditText exerciseNameInput = dialogView.findViewById(R.id.exerciseNameInput);
        Button acceptButton = dialogView.findViewById(R.id.acceptButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        dialogTitle.setText(R.string.delete_confirmation);
        exerciseNameInput.setVisibility(View.GONE);

        acceptButton.setOnClickListener(v -> {
            deleteExercise(exercise);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void createExercise(Exercise exercise) {
        retrofitClient.getExerciseService().createExercise(exercise).enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ExerciseListActivity.this, "Exercise created", Toast.LENGTH_SHORT).show();
                    loadExercises();
                } else {
                    Toast.makeText(ExerciseListActivity.this, "Failed to create exercise", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(ExerciseListActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateExercise(Exercise exercise) {
        retrofitClient.getExerciseService().updateExercise(exercise.getId(), exercise).enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ExerciseListActivity.this, "Exercise updated", Toast.LENGTH_SHORT).show();
                    loadExercises();
                } else {
                    Toast.makeText(ExerciseListActivity.this, "Failed to update exercise", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(ExerciseListActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteExercise(Exercise exercise) {
        retrofitClient.getExerciseService().deleteExercise(exercise.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ExerciseListActivity.this, "Exercise deleted", Toast.LENGTH_SHORT).show();
                    loadExercises();
                } else {
                    Toast.makeText(ExerciseListActivity.this, "Failed to delete exercise", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ExerciseListActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
