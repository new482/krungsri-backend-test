package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        return insertUser(UUID.randomUUID(), user);
    }

    List<User> findAll();

    Optional<User> findById(UUID id);
}
