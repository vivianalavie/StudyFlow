package main;

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