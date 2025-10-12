package com.example.buddy_gym_app_frontend_2025.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddy_gym_app_frontend_2025.R;
import com.example.buddy_gym_app_frontend_2025.adapters.TrainingHistoryAdapter;
import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private TrainingHistoryAdapter adapter;
    private int exerciseId;
    private RetrofitClient retrofitClient;

    public static HistoryFragment newInstance(int exerciseId) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt("exercise_id", exerciseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseId = getArguments().getInt("exercise_id");
        }
        retrofitClient = RetrofitClient.getInstance(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new TrainingHistoryAdapter();
        historyRecyclerView.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        retrofitClient.getTrainingService().getTrainingHistory(exerciseId, null, null)
                .enqueue(new Callback<List<Training>>() {
                    @Override
                    public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapter.setTrainings(response.body());
                        } else {
                            Toast.makeText(requireContext(), "Failed to load history", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Training>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
