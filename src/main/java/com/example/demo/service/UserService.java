package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(User user);   // ✅ not registerUser

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> getAllUsers();
}
