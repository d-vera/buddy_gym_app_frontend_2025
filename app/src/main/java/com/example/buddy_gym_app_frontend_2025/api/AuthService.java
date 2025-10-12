package com.example.buddy_gym_app_frontend_2025.api;

import com.example.buddy_gym_app_frontend_2025.models.LoginRequest;
import com.example.buddy_gym_app_frontend_2025.models.LoginResponse;
import com.example.buddy_gym_app_frontend_2025.models.RegisterRequest;
import com.example.buddy_gym_app_frontend_2025.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register/")
    Call<User> register(@Body RegisterRequest registerRequest);

    @GET("auth/profile/")
    Call<User> getProfile();
}
