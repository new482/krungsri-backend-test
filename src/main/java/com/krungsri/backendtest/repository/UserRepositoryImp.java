package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class UserRepositoryImp implements UserRepository {

    private static List<User> DB = new ArrayList<>();

    @Override
    public int insertUser(UUID id, User user) {
        DB.add(new User(id,
                user.getUsername(),
                user.getPassword(),
                user.getPhoneNo(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getSalary(),
                user.getRefCode()));
        return 1;
    }

    @Override
    public List<User> findAll() {
        return DB;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return DB.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
}
