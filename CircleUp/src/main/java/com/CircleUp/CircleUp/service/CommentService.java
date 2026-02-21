package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.Comment;
import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.CommentRepository;
import com.CircleUp.CircleUp.repository.NotificationRepository;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public Comment addComment(Long userId,Long postId,String text,String username){
        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found!!"));
        User user=userRepository.findById(postId).orElseThrow(()->new RuntimeException("User not found!!"));
        Comment comment=new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(text);
        Comment saved=commentRepository.save(comment);
        if(!post.getUser().getId().equals(userId)){
//            NotificationService.send(post.getUser().getId(),username+"Commented on your post");
            notificationService.send(post.getUser().getId(),username+"Commented on your post");
        }
        return saved;
    }
    public List<Comment>getCommentByPost(Long postid){
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postid);
    }
}
