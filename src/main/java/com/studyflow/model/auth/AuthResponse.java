package com.studyflow.model.auth;

import com.google.gson.annotations.SerializedName;


public record AuthResponse (
    @SerializedName("access_token") String accessToken
) { }