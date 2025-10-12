package com.example.buddy_gym_app_frontend_2025.api;

import com.example.buddy_gym_app_frontend_2025.models.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExerciseService {
    @GET("exercises/")
    Call<List<Exercise>> getExercises(@Query("muscle") Integer muscleId);

    @GET("exercises/{id}/")
    Call<Exercise> getExercise(@Path("id") int id);

    @POST("exercises/")
    Call<Exercise> createExercise(@Body Exercise exercise);

    @PUT("exercises/{id}/")
    Call<Exercise> updateExercise(@Path("id") int id, @Body Exercise exercise);

    @DELETE("exercises/{id}/")
    Call<Void> deleteExercise(@Path("id") int id);
}
