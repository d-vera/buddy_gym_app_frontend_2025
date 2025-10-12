package com.example.buddy_gym_app_frontend_2025.api;

import com.example.buddy_gym_app_frontend_2025.models.Muscle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuscleService {
    @GET("muscles/")
    Call<List<Muscle>> getMuscles();

    @GET("muscles/{id}/")
    Call<Muscle> getMuscle(@Path("id") int id);
}
