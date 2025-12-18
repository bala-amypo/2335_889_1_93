package com.example.demo.service;

import com.example.demo.models.User;  // Changed from 'model' to 'models'
import java.util.*;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
    User createUser(User user);
    User updateUser(Long id, User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
}