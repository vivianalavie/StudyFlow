

package utils; 

import java.net.http.*;
import java.net.URI;
import java.io.IOException;

import models.AuthResponse;
import models.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/*

public class HttpClientHelper {
    public static AuthResponse sendPostRequest(String url, String jsonBody, String apiKey) {
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
            ObjectMapper mapper = new ObjectMapper();
            //System.out.println("DEBUG: Supabase Antwort-Body:");
            //System.out.println(response.body());
            return mapper.readValue(response.body(), AuthResponse.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

*/


public class HttpClientHelper {
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

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), responseType);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
