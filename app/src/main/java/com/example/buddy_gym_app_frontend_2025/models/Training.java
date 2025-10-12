package com.example.buddy_gym_app_frontend_2025.models;

import com.google.gson.annotations.SerializedName;

public class Training {
    @SerializedName("id")
    private int id;

    @SerializedName("exercise")
    private int exercise;

    @SerializedName("exercise_name")
    private String exerciseName;

    @SerializedName("muscle_name")
    private String muscleName;

    @SerializedName("weight")
    private String weight;

    @SerializedName("sets")
    private int sets;

    @SerializedName("repetitions")
    private int repetitions;

    @SerializedName("datetime")
    private String datetime;

    public Training() {
    }

    public Training(int exercise, String weight, int sets, int repetitions) {
        this.exercise = exercise;
        this.weight = weight;
        this.sets = sets;
        this.repetitions = repetitions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExercise() {
        return exercise;
    }

    public void setExercise(int exercise) {
        this.exercise = exercise;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
