package com.example.buddy_gym_app_frontend_2025.api;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.buddy_gym_app_frontend_2025.LoginActivity;
import com.example.buddy_gym_app_frontend_2025.utils.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;
    private TokenManager tokenManager;

    public AuthInterceptor(Context context, TokenManager tokenManager) {
        this.context = context;
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        // Check if response is 401 or 403 (unauthorized/forbidden - token expired or invalid)
        if (response.code() == 401 || response.code() == 403) {
            // Show toast on main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Session expired. Please login again.", Toast.LENGTH_LONG).show();
                
                // Clear token
                tokenManager.clearToken();
                
                // Redirect to login
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            });
        }

        return response;
    }
}
