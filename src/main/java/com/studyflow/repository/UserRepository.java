package com.studyflow.repository;

import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;

public interface UserRepository {
    AuthResponse signup(UserCredentialsModel user);
    AuthResponse login(UserCredentialsModel user);
}
