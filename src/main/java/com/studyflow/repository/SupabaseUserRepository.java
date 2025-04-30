package com.studyflow.repository;

import com.studyflow.model.LoginResponse;
import com.studyflow.model.User;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class SupabaseUserRepository implements UserRepository{

    private static final String SUPABASE_URL = "https://rhhzimabizktsrmwwkoh.supabase.co/auth/v1/signup";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJoaHppbWFiaXprdHNybXd3a29oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ1NjA0MDQsImV4cCI6MjA2MDEzNjQwNH0.p7n4TUvkms3wGOuf0QYJQba0kvtKGArDMR4pV6QNHWM";

    private static final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public void save(User user) {
        try {
            String jsonBody = gson.toJson(user);

            Request request = new Request.Builder()
                    .url(SUPABASE_URL)
                    .addHeader("apikey", API_KEY)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(jsonBody, JSON))
                    .build();

            System.out.println("Body: " + jsonBody);

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to save user: " + response.code() + " - " + response.body().string());
            }

            System.out.println("User saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponse login(String email, String password) {
        String loginUrl = "https://rhhzimabizktsrmwwkoh.supabase.co/auth/v1/token?grant_type=password";

        // Baue das JSON-Body manuell
        String jsonBody = gson.toJson(new User(email, password));

        Request request = new Request.Builder()
                .url(loginUrl)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Login failed: " + response.code() + " - " + response.body().string());
            }

            // Lese und parse den Body
            String responseBody = response.body().string();
            System.out.println("Login response: " + responseBody);

            return gson.fromJson(responseBody, LoginResponse.class);

        } catch (IOException e) {
            throw new RuntimeException("Login request failed", e);
        }
    }


    @Override
    public User findById(String userId) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
