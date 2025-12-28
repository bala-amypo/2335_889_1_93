// package com.example.demo.service;

// import com.example.demo.model.User;

// public interface UserService {

//     User register(User user);

//     User findByEmail(String email);
// }
package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    User register(User user);

    User login(String email, String password);

    User findByEmail(String email);
}