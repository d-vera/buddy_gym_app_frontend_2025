package com.example.buddy_gym_app_frontend_2025.models;

import com.google.gson.annotations.SerializedName;

public class TrainingStats {
    @SerializedName("exercise_id")
    private int exerciseId;

    @SerializedName("exercise_name")
    private String exerciseName;

    @SerializedName("muscle_name")
    private String muscleName;

    @SerializedName("low_weight")
    private String lowWeight;

    @SerializedName("high_weight")
    private String highWeight;

    @SerializedName("last_weight")
    private String lastWeight;

    @SerializedName("total_sessions")
    private int totalSessions;

    public TrainingStats() {
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public String getLowWeight() {
        return lowWeight;
    }

    public void setLowWeight(String lowWeight) {
        this.lowWeight = lowWeight;
    }

    public String getHighWeight() {
        return highWeight;
    }

    public void setHighWeight(String highWeight) {
        this.highWeight = highWeight;
    }

    public String getLastWeight() {
        return lastWeight;
    }

    public void setLastWeight(String lastWeight) {
        this.lastWeight = lastWeight;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(int totalSessions) {
        this.totalSessions = totalSessions;
    }
}
