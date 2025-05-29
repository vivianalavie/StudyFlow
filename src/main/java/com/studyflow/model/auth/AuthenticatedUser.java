package com.studyflow.model.auth;
import java.time.Instant;

public record AuthenticatedUser(
        String id,
        String email,
        String role,
        boolean emailConfirmed,
        Instant lastSignInAt
) {}