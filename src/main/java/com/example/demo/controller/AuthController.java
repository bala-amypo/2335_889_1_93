package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = userService.findByEmail(request.getEmail());

        if (!user.getPassword().equals(request.getPassword())) {
            return "Invalid email or password";
        }

        return "Login successful for user: " + user.getEmail();
    }
}
