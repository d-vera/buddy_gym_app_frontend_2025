package com.example.buddy_gym_app_frontend_2025.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.R;
import com.example.buddy_gym_app_frontend_2025.models.Muscle;

import java.util.ArrayList;
import java.util.List;

public class MuscleAdapter extends RecyclerView.Adapter<MuscleAdapter.MuscleViewHolder> {

    private List<Muscle> muscles = new ArrayList<>();
    private OnMuscleClickListener listener;

    public interface OnMuscleClickListener {
        void onMuscleClick(Muscle muscle);
    }

    public MuscleAdapter(OnMuscleClickListener listener) {
        this.listener = listener;
    }

    public void setMuscles(List<Muscle> muscles) {
        this.muscles = muscles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MuscleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_muscle, parent, false);
        return new MuscleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuscleViewHolder holder, int position) {
        Muscle muscle = muscles.get(position);
        holder.bind(muscle);
    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }

    class MuscleViewHolder extends RecyclerView.ViewHolder {
        private TextView muscleNameText;

        public MuscleViewHolder(@NonNull View itemView) {
            super(itemView);
            muscleNameText = itemView.findViewById(R.id.muscleNameText);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onMuscleClick(muscles.get(position));
                }
            });
        }

        public void bind(Muscle muscle) {
            muscleNameText.setText(muscle.getDisplayName());
        }
    }
}
