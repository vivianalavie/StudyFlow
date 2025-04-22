package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    public String id;
    public String email;
    public String created_at;
    public String updated_at;
    public boolean is_anonymous;
    public String access_token;
}
