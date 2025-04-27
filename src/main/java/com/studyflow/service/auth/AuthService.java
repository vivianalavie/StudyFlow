package com.studyflow.service.auth;

import com.studyflow.utils.HttpClientHelper;
import com.studyflow.model.AuthResponse;
import com.studyflow.model.LoginResponse;

public class AuthService {
    private final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJoaHppbWFiaXprdHNybXd3a29oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDQ1NjA0MDQsImV4cCI6MjA2MDEzNjQwNH0.p7n4TUvkms3wGOuf0QYJQba0kvtKGArDMR4pV6QNHWM";
    private final String BASE_URL = "https://rhhzimabizktsrmwwkoh.supabase.co/auth/v1";

    public AuthResponse register(String email, String password) {
        String url = BASE_URL + "/signup";
        String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);
        return HttpClientHelper.sendPostRequest(url, json, API_KEY, AuthResponse.class);
    }

    public LoginResponse login(String email, String password) {
        String url = BASE_URL + "/token?grant_type=password";
        String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);
        return HttpClientHelper.sendPostRequest(url, json, API_KEY, LoginResponse.class);

    }
}
