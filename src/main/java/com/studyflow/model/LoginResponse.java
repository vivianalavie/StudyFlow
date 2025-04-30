//package com.studyflow.model;
//
//
//public class LoginResponse {
//    public String access_token;
//    public String refresh_token;
//    public String token_type;
//    public int expires_in;
//    public User user;
//
//    public static class User {
//        public String id;
//        public String email;
//    }
//}

package com.studyflow.model;

public class LoginResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private SupabaseUser user;

    // Innere Klasse für User-Daten
    public static class SupabaseUser {
        private String id;
        private String email;

        // Getter + Setter
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    // Getter + Setter für LoginResponse
    public String getAccess_token() { return access_token; }
    public void setAccess_token(String access_token) { this.access_token = access_token; }

    public String getToken_type() { return token_type; }
    public void setToken_type(String token_type) { this.token_type = token_type; }

    public int getExpires_in() { return expires_in; }
    public void setExpires_in(int expires_in) { this.expires_in = expires_in; }

    public String getRefresh_token() { return refresh_token; }
    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }

    public SupabaseUser getUser() { return user; }
    public void setUser(SupabaseUser user) { this.user = user; }
}
