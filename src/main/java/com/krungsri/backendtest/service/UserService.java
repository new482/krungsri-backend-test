package com.krungsri.backendtest.service;

import com.krungsri.backendtest.model.User;
import com.krungsri.backendtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("postgres") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int register(User user) {
        int phoneLength = user.getPhoneNo().length();
        user.setRefCode(user.getPhoneNo().substring(phoneLength - 4, phoneLength));
        return userRepository.insertUser(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

}
