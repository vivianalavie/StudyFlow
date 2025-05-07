package com.studyflow.repository;

import com.studyflow.model.auth.SignUpResponse;
import com.studyflow.model.auth.UserCredentialsModel;

public interface UserRepository {
    SignUpResponse signup(UserCredentialsModel user);
    void deleteUser(String userId);
}
