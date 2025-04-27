package com.studyflow.model;


public class LoginResponse {
    public String access_token;
    public String refresh_token;
    public String token_type;
    public int expires_in;
    public User user;

    public static class User {
        public String id;
        public String email;
    }
}
