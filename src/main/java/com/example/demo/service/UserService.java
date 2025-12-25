package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    // Register user (returns saved User)
    User registerUser(User user);

    // Find user by email (returns User directly)
    User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> getAllUsers();
}
