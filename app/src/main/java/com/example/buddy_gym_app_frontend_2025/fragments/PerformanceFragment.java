package com.example.buddy_gym_app_frontend_2025.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buddy_gym_app_frontend_2025.R;
import com.example.buddy_gym_app_frontend_2025.api.RetrofitClient;
import com.example.buddy_gym_app_frontend_2025.models.Training;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceFragment extends Fragment {

    private TextView currentWeekText;
    private TextView lastWeekText;
    private TextView lastMonthText;
    private BarChart barChart;
    private int exerciseId;
    private RetrofitClient retrofitClient;

    public static PerformanceFragment newInstance(int exerciseId) {
        PerformanceFragment fragment = new PerformanceFragment();
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
        return inflater.inflate(R.layout.fragment_performance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentWeekText = view.findViewById(R.id.currentWeekText);
        lastWeekText = view.findViewById(R.id.lastWeekText);
        lastMonthText = view.findViewById(R.id.lastMonthText);
        barChart = view.findViewById(R.id.barChart);

        loadPerformanceData();
        setupChart();
    }

    private void loadPerformanceData() {
        // Load current week (100% = 5 sessions)
        retrofitClient.getTrainingService().getTrainingHistory(exerciseId, null, "current_week")
                .enqueue(new Callback<List<Training>>() {
                    @Override
                    public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int count = response.body().size();
                            int percentage = Math.min((count * 100) / 5, 100);
                            currentWeekText.setText(percentage + "%");
                        } else {
                            currentWeekText.setText("0%");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Training>> call, Throwable t) {
                        currentWeekText.setText("0%");
                    }
                });

        // Load last week (100% = 5 sessions)
        retrofitClient.getTrainingService().getTrainingHistory(exerciseId, null, "last_week")
                .enqueue(new Callback<List<Training>>() {
                    @Override
                    public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int count = response.body().size();
                            int percentage = Math.min((count * 100) / 5, 100);
                            lastWeekText.setText(percentage + "%");
                        } else {
                            lastWeekText.setText("0%");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Training>> call, Throwable t) {
                        lastWeekText.setText("0%");
                    }
                });

        // Load last month (100% = 25 sessions)
        retrofitClient.getTrainingService().getTrainingHistory(exerciseId, null, "last_month")
                .enqueue(new Callback<List<Training>>() {
                    @Override
                    public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int count = response.body().size();
                            int percentage = Math.min((count * 100) / 25, 100);
                            lastMonthText.setText(percentage + "%");
                        } else {
                            lastMonthText.setText("0%");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Training>> call, Throwable t) {
                        lastMonthText.setText("0%");
                    }
                });

        // Load last year data for chart
        loadYearlyChart();
    }

    private void loadYearlyChart() {
        retrofitClient.getTrainingService().getTrainingHistory(exerciseId, null, "last_year")
                .enqueue(new Callback<List<Training>>() {
                    @Override
                    public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updateChart(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Training>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Failed to load chart data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
    }

    private void updateChart(List<Training> trainings) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        
        // Create a map to count trainings per month (0-11 for last 12 months)
        Map<Integer, Integer> monthCounts = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthCounts.put(i, 0);
        }
        
        // Get current date
        Calendar now = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        
        // Group trainings by month
        for (Training training : trainings) {
            if (training.getDatetime() != null && !training.getDatetime().isEmpty()) {
                try {
                    // Parse the training date
                    Date trainingDate = dateFormat.parse(training.getDatetime().substring(0, 19));
                    if (trainingDate != null) {
                        Calendar trainingCal = Calendar.getInstance();
                        trainingCal.setTime(trainingDate);
                        
                        // Calculate months difference from now
                        int yearDiff = now.get(Calendar.YEAR) - trainingCal.get(Calendar.YEAR);
                        int monthDiff = now.get(Calendar.MONTH) - trainingCal.get(Calendar.MONTH);
                        int totalMonthsDiff = yearDiff * 12 + monthDiff;
                        
                        // If within last 12 months, add to count
                        if (totalMonthsDiff >= 0 && totalMonthsDiff < 12) {
                            int monthIndex = 11 - totalMonthsDiff; // Reverse order (oldest to newest)
                            monthCounts.put(monthIndex, monthCounts.get(monthIndex) + 1);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Create bar entries from month counts
        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, monthCounts.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Trainings");
        dataSet.setColors(Color.parseColor("#E57373"), Color.parseColor("#BDBDBD"));
        dataSet.setDrawValues(false);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }
}
