package com.CircleUp.CircleUp.controller;


import com.CircleUp.CircleUp.entity.Comment;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.CommentRepository;
import com.CircleUp.CircleUp.repository.NotificationRepository;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import com.CircleUp.CircleUp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;
    @PostMapping("/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @PathVariable Long postId,@AuthenticationPrincipal User user) {

        return commentService.addComment(user.getId(),postId,comment.getText(),user.getName());
    }

    @GetMapping("/{postId}")
    public List<Comment>getComments(@PathVariable Long postId){
        return commentService.getCommentByPost(postId);
    }
    @GetMapping("posts/{postId}")
    public List<Comment>getCommentByPost(@PathVariable Long postId){
        return commentRepository.findByPostId(postId);
    }
}
