package com.studyflow.model.auth;

import com.google.gson.annotations.SerializedName;

record UserDetails (
    String id,
    String email
) {}

public record AuthResponse (
    @SerializedName("access_token") String accessToken,
    @SerializedName("token_type") String tokenType,
    @SerializedName("expires_in") int expiresIn,
    @SerializedName("expires_at") int expiresAt,
    @SerializedName("refresh_token") String refreshToken,
    UserDetails user
) { }