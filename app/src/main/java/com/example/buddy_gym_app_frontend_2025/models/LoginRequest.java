package com.example.buddy_gym_app_frontend_2025.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email_or_username")
    private String emailOrUsername;

    @SerializedName("password")
    private String password;

    public LoginRequest(String emailOrUsername, String password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
