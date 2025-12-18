package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    // Required by STEP 4
    User register(User user);
    User findByEmail(String email);
    
    // Additional CRUD operations
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
}