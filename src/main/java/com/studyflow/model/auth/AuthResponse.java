package com.studyflow.model.auth;

record UserDetails (
    String id,
    String email
) {}

public record AuthResponse (
    String accessToken,
    String tokenType,
    int expiresIn,
    int expiresAt,
    String refreshToken,
    UserDetails user
) { }