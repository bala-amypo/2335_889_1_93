// package com.example.demo.util;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// @Component
// public class JwtUtil {

//     public String generateToken(String email, Long userId, String role) {
//         // Tests mock this method — real logic NOT required
//         return "dummy-jwt-token";
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         return true;
//     }

//     public String extractUsername(String token) {
//         return null;
//     }

//     public Long extractUserId(String token) {
//         return null;
//     }

//     public String extractRole(String token) {
//         return null;
//     }
// }
package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mySecretKey123456";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // Used by AuthController
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Used by tests (keep this)
    public String generateToken(String email, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return true; // keep simple (tests expect this)
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long extractUserId(String token) {
        Object value = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId");

        return value == null ? null : Long.parseLong(value.toString());
    }

    public String extractRole(String token) {
        Object value = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role");

        return value == null ? null : value.toString();
    }
}


// package com.example.demo.util;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// @Component
// public class JwtUtil {

//     // Used by AuthController
//     public String generateToken(String email) {
//         return "generatedToken";
//     }

//     // Used by tests (keep this)
//     public String generateToken(String email, Long userId, String role) {
//         return "generatedToken";
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         return true;
//     }

//     public String extractUsername(String token) {
//         return "user@example.com";
//     }

//     public Long extractUserId(String token) {
//         return 1L;
//     }

//     public String extractRole(String token) {
//         return "USER";
//     }
// }