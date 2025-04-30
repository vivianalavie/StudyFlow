package com.studyflow.repository;

import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;

public interface UserRepository {
    AuthResponse save(UserCredentialsModel user);
    UserCredentialsModel findById(String userId);
    boolean existsByEmail(String email);
    AuthResponse login(String email, String password);
}
