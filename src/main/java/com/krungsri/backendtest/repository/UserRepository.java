package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.User;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Either<Integer, Integer> insertUser(UUID id, User user);

    default Either<Integer, Integer> insertUser(User user) {
        return insertUser(UUID.randomUUID(), user);
    }

    List<User> findAll();

    Optional<User> findById(UUID id);
}
