package com.example.buddy_gym_app_frontend_2025.api;

import android.content.Context;

import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
private static final String BASE_URL = "http://10.80.82.38:8000/api/";
    private static RetrofitClient instance;
    private Retrofit retrofit;
    private TokenManager tokenManager;

    private RetrofitClient(Context context) {
        tokenManager = new TokenManager(context);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();

                        String token = tokenManager.getToken();
                        if (token != null) {
                            requestBuilder.header("Authorization", "Bearer " + token);
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new AuthInterceptor(context, tokenManager))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context.getApplicationContext());
        }
        return instance;
    }

    public AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }

    public MuscleService getMuscleService() {
        return retrofit.create(MuscleService.class);
    }

    public ExerciseService getExerciseService() {
        return retrofit.create(ExerciseService.class);
    }

    public TrainingService getTrainingService() {
        return retrofit.create(TrainingService.class);
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }
}
