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
 * A simple JWT authentication filter.
 * Replace the dummy token logic with real JWT validation logic.
 */
@Component("jwtAuthenticationFilter") // explicit bean name to avoid conflicts
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String DUMMY_TOKEN = "dummy-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (DUMMY_TOKEN.equals(token)) {
                // Create a dummy authenticated user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("user", null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
