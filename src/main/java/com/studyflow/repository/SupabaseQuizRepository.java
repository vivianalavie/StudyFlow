package com.studyflow.repository;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

public class SupabaseQuizRepository {

    private static final String SUPABASE_URL = "https://<your-project>.supabase.co/rest/v1/answers"; // Passe an
    private static final String API_KEY = System.getenv("SUPABASE_API_KEY");
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public void submitAnswer(SupabaseQuizRepository answer) throws IOException {
        Request request = new Request.Builder()
                .url(SUPABASE_URL)
                .addHeader("apikey", Objects.requireNonNull(API_KEY))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(gson.toJson(answer), JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to submit answer: " + response.code());
            }
        }
    }
}
