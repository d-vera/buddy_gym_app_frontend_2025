package com.example.buddy_gym_app_frontend_2025.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.R;
import com.example.buddy_gym_app_frontend_2025.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises = new ArrayList<>();
    private OnExerciseActionListener listener;

    public interface OnExerciseActionListener {
        void onExerciseClick(Exercise exercise);
        void onEditClick(Exercise exercise);
        void onDeleteClick(Exercise exercise);
    }

    public ExerciseAdapter(OnExerciseActionListener listener) {
        this.listener = listener;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseNameText;
        private ImageView editExerciseIcon;
        private ImageView deleteExerciseIcon;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameText = itemView.findViewById(R.id.exerciseNameText);
            editExerciseIcon = itemView.findViewById(R.id.editExerciseIcon);
            deleteExerciseIcon = itemView.findViewById(R.id.deleteExerciseIcon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onExerciseClick(exercises.get(position));
                }
            });

            editExerciseIcon.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onEditClick(exercises.get(position));
                }
            });

            deleteExerciseIcon.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onDeleteClick(exercises.get(position));
                }
            });
        }

        public void bind(Exercise exercise) {
            exerciseNameText.setText(exercise.getName());
        }
    }
}
