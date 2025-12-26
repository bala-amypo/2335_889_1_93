package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {

    User register(User user); // matches controller

    User findByEmail(String email); // unwrap Optional here

    boolean existsByEmail(String email);

    List<User> getAllUsers();
}
