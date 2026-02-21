package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.FollowRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import com.CircleUp.CircleUp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    @PostMapping("/{followingId}")
    public String followUser(@PathVariable Long followingId,
                             @AuthenticationPrincipal User user) {
        followService.follow(user.getId(), followingId);
        return "Followed successfully";
    }

    @DeleteMapping("/{followingId}")
    public String unfollowUser(@PathVariable Long followingId,
                               @AuthenticationPrincipal User user) {
        followService.unfollow(user.getId(), followingId);
        return "Unfollowed successfully";
    }

    @GetMapping("/followers/{id}")
    public long followersCount(@PathVariable("id") Long userId) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return followRepository.countByFollowing(target);
    }

    @GetMapping("/following/{id}")
    public long followingCount(@PathVariable("id") Long userId) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return followRepository.countByFollower(target);
    }
}
