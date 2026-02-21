package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.dto.UserResponse;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserResponse createUser(@RequestBody User user){
        User saved=userService.saveUser(user);
        return new UserResponse(saved.getId(),saved.getName(),saved.getEmail());
    }
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
    @PutMapping("/{id}")
    public UserResponse updateProfile(@PathVariable Long id, @RequestBody User updatedUser){
        User user=userService.updateProfile(id,updatedUser);
        return new UserResponse(user.getId(),user.getName(),user.getEmail());
    }
    @GetMapping("/search")
    public List<UserResponse>searchUsers(@RequestParam String name){
        return userService.searchByName(name)
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
