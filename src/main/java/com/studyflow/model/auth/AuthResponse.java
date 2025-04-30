package com.studyflow.model.auth;

record UserDetails (
    String id,
    String email
) {}

public record AuthResponse (
    String access_token,
    String token_type,
    int expires_in,
    int expires_at,
    String refresh_token,
    UserDetails user
) { }