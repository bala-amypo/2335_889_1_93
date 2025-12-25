// package com.example.demo.model;

// import jakarta.persistence.*;

// @Entity
// @Table(
//     name = "users",
//     uniqueConstraints = @UniqueConstraint(columnNames = "email")
// )
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;

//     @Column(nullable = false, unique = true)
//     private String email;

//     private String password;

//     private String role;

//     public User() {}

//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }

//     public String getEmail() { return email; }
//     public void setEmail(String email) { this.email = email; }

//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }

//     public String getRole() { return role; }
//     public void setRole(String role) { this.role = role; }
// }

package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    // ✅ DEFAULT ROLE REQUIRED BY TESTS
    private String role = "USER";

    // ✅ NO-ARG CONSTRUCTOR
    public User() {
        this.role = "USER";
    }

    // ✅ CONSTRUCTOR REQUIRED BY TESTS
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    // OPTIONAL (used in some flows)
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = (role == null ? "USER" : role);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) {
        this.role = (role == null ? "USER" : role);
    }
}
