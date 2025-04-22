package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    public String access_token;
    public String refresh_token;
    public String token_type;
    public int expires_in;
    public User user;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        public String id;
        public String email;
    }
}
