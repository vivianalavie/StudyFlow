package com.studyflow.config;

import com.clerk.backend_api.Clerk;
import io.github.cdimascio.dotenv.Dotenv;

public class ClerkService {

    private static final Dotenv dotenv = Dotenv.load();

    private static final Clerk client = Clerk.builder()
            .bearerAuth(dotenv.get("CLERK_TOKEN"))
            .build();

    public static Clerk getClient() {
        return client;
    }
}
