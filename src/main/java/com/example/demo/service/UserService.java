// package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    // Registers a new user, returns saved user
    User registerUser(User user);

    // Finds a user by email, throws exception if not found
    User findByEmail(String email);

    // Optional: check if a user already exists by email
    boolean existsByEmail(String email);
}
