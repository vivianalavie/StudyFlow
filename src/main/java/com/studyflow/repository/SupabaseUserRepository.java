package com.studyflow.repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.studyflow.exception.AuthServiceException;
import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.AuthenticatedUser;
import com.studyflow.model.auth.UserCredentialsModel;
import okhttp3.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class SupabaseUserRepository implements UserRepository {

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
        return postToSupabase("/signup", user);
    }

    @Override
    public AuthResponse login(UserCredentialsModel user) {
        return postToSupabase("/token?grant_type=password", user);
    }

    @Override
    public AuthResponse logout() {
        // Supabase logout is handled client-side (token invalidation not supported server-side)
        throw new UnsupportedOperationException("Logout is handled client-side in Supabase.");
    }

    @Override
    public Optional<AuthenticatedUser> getAuthenticatedUser(UserCredentialsModel user) {
        try {
            String token = fetchAccessToken(user);
            return fetchUserProfile(token);
        } catch (IOException | AuthServiceException e) {
            return Optional.empty();
        }
    }

    // ---------- Internal Helpers ----------

    private AuthResponse postToSupabase(String endpoint, UserCredentialsModel user) {
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .addHeader("apikey", Objects.requireNonNull(API_KEY, "API_KEY must not be null"))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(gson.toJson(user), JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            return parseAuthResponse(response);
        } catch (IOException e) {
            throw new AuthServiceException("Request failed", e);
        }
    }

    private String fetchAccessToken(UserCredentialsModel user) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/token?grant_type=password")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(gson.toJson(user), JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new AuthServiceException("Invalid credentials or no token returned.");
            }

            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return json.get("access_token").getAsString();
        }
    }

    private Optional<AuthenticatedUser> fetchUserProfile(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/user")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("apikey", API_KEY)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return Optional.empty();
            }

            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return Optional.of(parseUser(json));
        }
    }

    private AuthResponse parseAuthResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            String errorBody = response.body() != null ? response.body().string() : "No error body";
            throw new AuthServiceException("Supabase error: " + response.code() + " - " + errorBody);
        }

        if (response.body() == null) {
            throw new AuthServiceException("Response body is null.");
        }

        return gson.fromJson(response.body().string(), AuthResponse.class);
    }

    private AuthenticatedUser parseUser(JsonObject json) {
        return new AuthenticatedUser(
                json.get("id").getAsString(),
                json.get("email").getAsString(),
                json.get("role").getAsString(),
                !json.get("email_confirmed_at").isJsonNull(),
                Instant.parse(json.get("last_sign_in_at").getAsString())
        );
    }
}
