package com.example.buddy_gym_app_frontend_2025.models;

import com.google.gson.annotations.SerializedName;

public class Muscle {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Muscle() {
    }

    public Muscle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        // Capitalize first letter
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
