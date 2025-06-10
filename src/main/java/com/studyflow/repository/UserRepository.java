package com.studyflow.repository;

import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;

import java.util.Optional;

public interface UserRepository {
    AuthResponse signup(UserCredentialsModel user);
    AuthResponse login(UserCredentialsModel user);
    AuthResponse logout();
    void deleteUser(String userId);
}
