package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter.
 * 
 * This filter checks for the presence of a JWT token in the "Authorization" header.
 * Currently, it uses a dummy token for demonstration purposes.
 * Replace this logic with actual JWT verification in production.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Dummy token for testing
    private static final String DUMMY_TOKEN = "dummy-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix

            // Validate the token (dummy check for testing)
            if (DUMMY_TOKEN.equals(token)) {
                // Create a simple authenticated user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("user", null, null);

                // Attach request details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
