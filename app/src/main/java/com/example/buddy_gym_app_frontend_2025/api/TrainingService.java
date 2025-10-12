package com.example.buddy_gym_app_frontend_2025.api;

import com.example.buddy_gym_app_frontend_2025.models.Training;
import com.example.buddy_gym_app_frontend_2025.models.TrainingStats;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrainingService {
    @GET("trainings/")
    Call<List<Training>> getTrainings();

    @GET("trainings/{id}/")
    Call<Training> getTraining(@Path("id") int id);

    @POST("trainings/")
    Call<Training> createTraining(@Body Training training);

    @PUT("trainings/{id}/")
    Call<Training> updateTraining(@Path("id") int id, @Body Training training);

    @DELETE("trainings/{id}/")
    Call<Void> deleteTraining(@Path("id") int id);

    @GET("trainings/history/")
    Call<List<Training>> getTrainingHistory(
            @Query("exercise") Integer exerciseId,
            @Query("muscle") Integer muscleId,
            @Query("period") String period
    );

    @GET("trainings/stats/")
    Call<List<TrainingStats>> getTrainingStats(
            @Query("exercise") Integer exerciseId,
            @Query("muscle") Integer muscleId
    );
}
