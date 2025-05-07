package com.studyflow.repository;

import com.studyflow.exception.SignupFlowException;
import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;

public interface UserRepository {
    AuthResponse signup(UserCredentialsModel user);
    void verifyEmail(String token);
    AuthResponse login(UserCredentialsModel user);
    void deleteUser(String userId);
    void requestPasswordReset(String email);
    void resetPassword(String token, String newPassword);
}
