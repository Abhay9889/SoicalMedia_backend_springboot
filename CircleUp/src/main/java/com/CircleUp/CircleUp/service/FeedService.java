package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final PostRepository postRepository;
    private final FollowService followService;
    public List<Post>getFeed(Long userId){
        List<Long>followingIds=followService.getFollowingIds(userId);
        if(followingIds.isEmpty()) return List.of();
        return postRepository.findByUserIdInOrderByCreatedAtDesc(followingIds);
    }
}
