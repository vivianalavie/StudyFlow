package com.studyflow.repository;

import com.studyflow.exception.AuthServiceException;
import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class SupabaseUserRepository implements UserRepository{

    private static final String BASE_URL = "https://rhhzimabizktsrmwwkoh.supabase.co/auth/v1";
    private static final String API_KEY = System.getenv("SUPABASE_API_KEY");
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final Gson gson;

    public SupabaseUserRepository() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    @Override
    public AuthResponse signup(UserCredentialsModel user) {
        return sendAuthRequest("/signup", user);
    }

    @Override
    public AuthResponse login(UserCredentialsModel user) {
        return sendAuthRequest("/token?grant_type=password", user);
    }

    private AuthResponse sendAuthRequest(String endpoint, UserCredentialsModel user) {
        String jsonBody = gson.toJson(user);
        Request request = buildRequest(BASE_URL + endpoint, jsonBody);

        try (Response response = client.newCall(request).execute()) {
            return handleResponse(response);
        } catch (IOException e) {
            throw new AuthServiceException("Auth request failed", e);
        }
    }

    private Request buildRequest(String url, String jsonBody) {
        return new Request.Builder()
                .url(url)
                .addHeader("apikey", Objects.requireNonNull(API_KEY, "API_KEY must not be null"))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, JSON))
                .build();
    }

    private AuthResponse handleResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            String errorBody = response.body() != null ? response.body().string() : "No error body";
            throw new AuthServiceException("Request failed: " + response.code() + " - " + errorBody);
        }

        if (response.body() == null) {
            throw new AuthServiceException("Response body was null.");
        }

        String responseBody = response.body().string();
        System.out.println("Auth response: " + responseBody);
        return gson.fromJson(responseBody, AuthResponse.class);
    }

}