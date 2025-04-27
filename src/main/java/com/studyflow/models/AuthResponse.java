package com.studyflow.models;

public record AuthResponse (
    String id,
    String email,
    String created_at,
    String updated_at,
    boolean is_anonymous,
    String access_token
) {}
