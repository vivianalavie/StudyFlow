package com.studyflow.repository;

import com.google.gson.Gson;
import com.studyflow.model.survey.Questions;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SupabaseQuestionRepository {

    private static final String BASE_URL = "https://rhhzimabizktsrmwwkoh.supabase.co/rest/v1";
    private static final String API_KEY = System.getenv("SUPABASE_API_KEY");
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final Gson gson;

    public SupabaseQuestionRepository() {
        this.client = new OkHttpClient();
        this.gson = new Gson();

        // Debug: Check API-Key
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.err.println("⚠️  SUPABASE_API_KEY is missing or empty!");
        } else {
            System.out.println("✅ SUPABASE_API_KEY loaded successfully.");
        }
    }

    public List<Questions> getAllQuestions() {
        Request request = new Request.Builder()
                .url(BASE_URL + "/quiz_questions?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Accept", "application/json")
                .build();

        // Debug to show URL
        System.out.println("Requesting Supabase URL: " + request.url());

        try (Response response = client.newCall(request).execute()) {

            // Debug: HTTP-Status
            System.out.println("HTTP Response Code: " + response.code());

            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error body";
                System.err.println("❌ Supabase query failed");
                System.err.println("Status Code: " + response.code());
                System.err.println("Headers: " + response.headers());
                System.err.println("Error Body: " + errorBody);

                throw new RuntimeException("Supabase query failed: " + errorBody);
            }

            // Debug: Successful answer
            String responseBody = response.body().string();
            System.out.println("Supabase response body: " + responseBody);

            Questions[] questionArray = gson.fromJson(responseBody, Questions[].class);
            return Arrays.asList(questionArray);

        } catch (IOException e) {
            System.err.println("❗ IOException while querying Supabase:");
            e.printStackTrace();
            throw new RuntimeException("Failed to load questions from Supabase", e);
        } catch (Exception e) {
            System.err.println("❗ Unexpected exception:");
            e.printStackTrace();
            throw new RuntimeException("Unexpected error while loading questions from Supabase", e);
        }
    }
}
