package com.studyflow.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class HttpClientHelper {
    private static final Gson gson = new Gson();

    public static <T> T sendPostRequest(String url, String jsonBody, String apiKey, Class<T> responseType) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("🔎 RESPONSE BODY: " + response.body());

            return gson.fromJson(response.body(), responseType);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();  // Good practice when catching InterruptedException
            return null;
        }
    }
}
