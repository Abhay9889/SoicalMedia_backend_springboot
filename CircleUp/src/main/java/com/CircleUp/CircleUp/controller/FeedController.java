package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.service.FeedService;
import com.CircleUp.CircleUp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FollowService followService;
    private final PostRepository postRepository;
    private final FeedService feedService;
    @GetMapping
    public List<Post> getFeed(@AuthenticationPrincipal User user) {

        return feedService.getFeed(user.getId());
    }
}
