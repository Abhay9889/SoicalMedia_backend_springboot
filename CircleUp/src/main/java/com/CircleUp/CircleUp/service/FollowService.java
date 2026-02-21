package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.Follow;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.FollowRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void follow(Long followerId, Long followingId) {
        if(followerId.equals(followingId)){
            throw new IllegalArgumentException("You can't forllow yourSelf!!");
        }
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Following user not found"));

        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        }
    }

    public void unfollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("Following user not found"));

        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    public List<Long> getFollowingIds(Long followerId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));

        return followRepository.findByFollower(follower)
                .stream()
                .map(f -> f.getFollowing().getId())
                .toList();
    }
}
