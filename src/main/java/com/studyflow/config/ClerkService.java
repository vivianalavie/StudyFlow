package com.studyflow.config;

import com.clerk.backend_api.Clerk;
import io.github.cdimascio.dotenv.Dotenv;

public class ClerkService {

    private static final String CLERK_TOKEN;
    static {
        String token = null;
        try {
            token = io.github.cdimascio.dotenv.Dotenv.load().get("CLERK_TOKEN");
        } catch (Exception e) {
            // Dotenv nicht gefunden, auf Umgebungsvariable zurückgreifen
            token = System.getenv("CLERK_TOKEN");
        }
        CLERK_TOKEN = token;
    }

    private static final Clerk client = Clerk.builder()
            .bearerAuth(CLERK_TOKEN)
            .build();

    public static Clerk getClient() {
        return client;
    }
}
