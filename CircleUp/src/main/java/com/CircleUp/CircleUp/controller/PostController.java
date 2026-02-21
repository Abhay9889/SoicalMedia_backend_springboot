package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.dto.CreatePostRequest;
import com.CircleUp.CircleUp.dto.PostResponse;
import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import com.CircleUp.CircleUp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostService postService;

    @PostMapping("/newdata")
    public PostResponse createPost(@RequestBody CreatePostRequest request,
                                   @AuthenticationPrincipal User user) {

        return postService.createPost(user.getId(), request.getContent());
//        String token = header.substring(7);
//
//        String email = JwtUtil.extractEmail(token);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found!!"));
//
//        return postService.createPost(user.getId(), request.getContent());
    }

    @GetMapping
    public List<Post> getPosts() {
        return repository.findAll();
    }



    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost,@AuthenticationPrincipal User user) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if(!post.getUser().getId().equals(user.getId())) throw new RuntimeException("Unauthorized — you do not own this post");
        post.setContent(updatedPost.getContent());
        post.setImageUrl(updatedPost.getImageUrl());
        return repository.save(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id,@AuthenticationPrincipal User user) {
        postService.deletePost(id, user.getId());
        return ResponseEntity.ok("Post deleted successfully!!");
    }

    @GetMapping("/paged")
    public Page<Post> getPostsPaged(@RequestParam int page, @RequestParam int size) {
//        return repository.findAll(org.springframework.data.domain.PageRequest.of(page, size));
        return postService.getPagedPosts(page,size);
    }
}
