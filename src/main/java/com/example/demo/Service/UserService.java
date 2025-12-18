package com.example.demo.services;

import com.example.demo.models.User;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
}