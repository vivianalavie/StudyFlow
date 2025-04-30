package com.studyflow;

import com.studyflow.service.auth.AuthService;
import com.studyflow.model.AuthResponse;
import com.studyflow.model.LoginResponse;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        // Registrierung
        AuthResponse response = authService.register("maurice.pestka@gmail.com", "testpass123");
        if (response != null) {
            System.out.println("User ID: " + response.id());
            System.out.println("E-Mail: " + response.email());
        } else {
            System.out.println("❌Fehler bei der Registrierung.");
        }

        // Login
        LoginResponse loginResponse = authService.login("maurice.pestka@gmail.com", "testpass123");
        if (loginResponse != null && loginResponse.getAccess_token() != null) {
            System.out.println("✅ Login erfolgreich! Token: " + loginResponse.getAccess_token());
            System.out.println("User: " + loginResponse.getUser().getEmail());
        } else {
            System.out.println("❌ Login fehlgeschlagen.");
        }
    }
}





