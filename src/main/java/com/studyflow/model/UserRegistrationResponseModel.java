package com.studyflow.model;

public record UserRegistrationResponseModel(
    String accessToken,
    String refreshToken
) {
    public record User(
        String id
    ) {}
}
