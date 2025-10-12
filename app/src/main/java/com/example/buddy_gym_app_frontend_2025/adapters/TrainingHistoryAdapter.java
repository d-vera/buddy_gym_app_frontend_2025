package com.example.buddy_gym_app_frontend_2025.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.R;
import com.example.buddy_gym_app_frontend_2025.models.Training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrainingHistoryAdapter extends RecyclerView.Adapter<TrainingHistoryAdapter.TrainingHistoryViewHolder> {

    private List<Training> trainings = new ArrayList<>();

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrainingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_training_history, parent, false);
        return new TrainingHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingHistoryViewHolder holder, int position) {
        Training training = trainings.get(position);
        holder.bind(training);
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    static class TrainingHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView dateText;
        private TextView weightText;
        private TextView setsText;
        private TextView repsText;

        public TrainingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            weightText = itemView.findViewById(R.id.weightText);
            setsText = itemView.findViewById(R.id.setsText);
            repsText = itemView.findViewById(R.id.repsText);
        }

        public void bind(Training training) {
            // Format date
            String formattedDate = formatDate(training.getDatetime());
            dateText.setText(formattedDate);
            weightText.setText(training.getWeight());
            setsText.setText(String.valueOf(training.getSets()));
            repsText.setText(String.valueOf(training.getRepetitions()));
        }

        private String formatDate(String datetime) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());
                Date date = inputFormat.parse(datetime);
                return outputFormat.format(date);
            } catch (ParseException e) {
                return datetime;
            }
        }
    }
}
