package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;
    public List<User>searchByName(String name){
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));
        user.setEmailVerified(false);
        User saved = userRepository.save(user);
        try {
            emailService.sendVerificationEmail(saved.getEmail(), token);
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        return saved;
    }

    public String verifyEmail(String token){
        User user=userRepository.findByVerificationToken(token).orElseThrow(()->new RuntimeException("user not found!!"));
        if(user.getTokenExpiry().isBefore(LocalDateTime.now())){
            return "Token has Expired!!";

        }
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);
        return "Email verified successfully!";
    }

    public User updateProfile(Long id,User updateUser){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found with id: "+id));
        user.setName(updateUser.getName());
        user.setBio(updateUser.getBio());
        user.setLocation(updateUser.getLocation());
        user.setProfilePictureUrl(updateUser.getProfilePictureUrl());
        return userRepository.save(user);
    }
    public User getUserById(Long id) { return userRepository.findById(id) .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
