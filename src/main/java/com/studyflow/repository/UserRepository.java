package com.studyflow.repository;

import com.studyflow.model.User;

public interface UserRepository {
    void save(User user);
    User findById(String userId);
    boolean existsByEmail(String email);
}
