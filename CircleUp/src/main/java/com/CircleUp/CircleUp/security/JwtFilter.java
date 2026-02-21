package com.CircleUp.CircleUp.security;

import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("=== JWT FILTER ===");
        System.out.println("Request URL: " + request.getRequestURI());
        System.out.println("Auth Header: " + header);

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("No valid header — skipping filter");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            System.out.println("Token: " + token);

            boolean valid = jwtUtil.validateToken(token);
            System.out.println("Token valid: " + valid);

            if (valid) {
                String email = jwtUtil.extractEmail(token);
                System.out.println("Email: " + email);

                User user = userRepository.findByEmail(email).orElse(null);
                System.out.println("User found: " + (user != null));

                if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Auth set successfully for: " + email);
                }
            }
        } catch (Exception e) {
            System.out.println("JWT Filter ERROR: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
    }