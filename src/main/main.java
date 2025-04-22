package main;
/*
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

	public class main {
	    public static void main(String[] args) throws IOException, InterruptedException {
	        String supabaseUrl = "https://rhhzimabizktsrmwwkoh.supabase.co/rest/v1/assignments?select=*";
	        String supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJoaHppbWFiaXprdHNybXd3a29oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ1NjA0MDQsImV4cCI6MjA2MDEzNjQwNH0.p7n4TUvkms3wGOuf0QYJQba0kvtKGArDMR4pV6QNHWM"; // ⚠️ Hol dir den anon key aus Supabase → Settings → API

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(supabaseUrl))
	            .header("apikey", supabaseKey)
	            .header("Authorization", "Bearer " + supabaseKey)
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        System.out.println("Antwort von Supabase:\n" + response.body());
	    }
	}
*/
/*
import auth.AuthService;
import models.AuthResponse;

public class main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        AuthResponse response = authService.register("maurice.pestka@gmail.com", "testpass123");
        if (response != null) {
            System.out.println("User ID: " + response.id);
            System.out.println("Access Token: " + response.email);
        } else {
            System.out.println("Fehler bei der Registrierung.");
        }
    }
}
*/


import auth.AuthService;
import models.AuthResponse;
import models.LoginResponse;

public class main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        // Registrierung
        AuthResponse response = authService.register("maurice.pestka@gmail.com", "testpass123");
        if (response != null) {
            System.out.println("User ID: " + response.id);
            System.out.println("E-Mail: " + response.email);
        } else {
            System.out.println("❌Fehler bei der Registrierung.");
        }

        // Login
        LoginResponse loginResponse = authService.login("maurice.pestka@gmail.com", "testpass123");
        if (loginResponse != null && loginResponse.access_token != null) {
            System.out.println("✅ Login erfolgreich! Token: " + loginResponse.access_token);
            System.out.println("User: " + loginResponse.user.email);
        } else {
            System.out.println("❌ Login fehlgeschlagen.");
        }
    }
}






