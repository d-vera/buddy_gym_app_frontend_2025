package com.example.buddy_gym_app_frontend_2025.models;

import com.google.gson.annotations.SerializedName;

public class Exercise {
    @SerializedName("id")
    private int id;

    @SerializedName("muscle")
    private int muscle;

    @SerializedName("muscle_name")
    private String muscleName;

    @SerializedName("name")
    private String name;

    @SerializedName("note")
    private String note;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Exercise() {
    }

    public Exercise(int muscle, String name, String note) {
        this.muscle = muscle;
        this.name = name;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMuscle() {
        return muscle;
    }

    public void setMuscle(int muscle) {
        this.muscle = muscle;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
