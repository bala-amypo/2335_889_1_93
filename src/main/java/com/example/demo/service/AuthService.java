package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface AuthService {
    User registerUser(User user);
    List<User> getAllUsers();
}
