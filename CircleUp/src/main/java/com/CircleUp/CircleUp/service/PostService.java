package com.CircleUp.CircleUp.service;


import com.CircleUp.CircleUp.dto.PostResponse;
import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.FollowRepository;
import com.CircleUp.CircleUp.repository.LikeRepository;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;
    private final LikeRepository likeRepository;
    public PostResponse createPost(Long userId,String content){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found!!"));
        if(!user.isEmailVerified()){
            throw new RuntimeException("Please verify your email before posting");
        }
        Post post1=new Post();
        post1.setUser(user);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setContent(content);
        Post saved=repository.save(post1);
//        long likeCount=likeRepository.countByPost_Id(saved.getId());
//        return new PostResponse(saved.getId(),saved.getContent(),saved.getUser().getId(),saved.getCreatedAt(),likeCount);
        return new PostResponse(
                saved.getId(),saved.getContent(),saved.getCreatedAt(),user.getName()
        );
    }

    public List<Post>getAllPosts(){
        return repository.findAll();
    }
    public List<Post>getFeed(Long userId){
        List<Long>followingIds=followService.getFollowingIds(userId);
        if(followingIds.isEmpty()) {
            return List.of();
        }

        return repository.findByUserIdInOrderByCreatedAtDesc(followingIds);
    }

    public void deletePost(Long postId,Long userId){
        Post post=repository.findById(postId)
                .orElseThrow(()->new RuntimeException("Post not found!!"));
        if(!post.getUser().getId().equals(userId)){
            throw new RuntimeException("Unauthorized");
        }
        repository.delete(post);
    }
    public Page<Post> getPagedPosts(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
