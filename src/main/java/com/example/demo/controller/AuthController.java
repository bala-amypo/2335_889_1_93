// package com.example.demo.controller;

// import com.example.demo.model.User;
// import com.example.demo.service.AuthService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     @Autowired
//     private AuthService authService;

//     @PostMapping("/register")
//     public User register(@RequestBody User user) {
//         return authService.register(user);
//     }

//     @GetMapping("/users")
//     public List<User> getAllUsers() {
//         return authService.getAllUsers();
//     }

// } // <-- Make sure this is the only closing brace for the class
