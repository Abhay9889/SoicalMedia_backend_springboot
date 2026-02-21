package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.dto.AuthResponse;
import com.CircleUp.CircleUp.dto.LoginRequest;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.UserRepository;
import com.CircleUp.CircleUp.security.JwtUtil;
import com.CircleUp.CircleUp.service.EmailService;
import com.CircleUp.CircleUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final EmailService emailService;
    @PostMapping("/login")
     public ResponseEntity<?> login (@RequestBody LoginRequest request){
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        if(!encoder.matches(request.getPassword(),user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password!!");

        }

        String token= jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), 3600));
    }
    @GetMapping("/verify")
    public ResponseEntity<String >verifyEmail(@RequestParam String token){
        String result=userService.verifyEmail(token);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        try {
            emailService.sendVerificationEmail("ay9526194@gmail.com", "test-token-123");
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Email failed: " + e.getMessage());
        }
    }
}
