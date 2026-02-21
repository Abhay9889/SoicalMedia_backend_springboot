package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.Like;
import com.CircleUp.CircleUp.entity.Post;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.LikeRepository;
import com.CircleUp.CircleUp.repository.PostRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    @Transactional
    public void liekPost(Long userId,Long postId,String username){
        if(likeRepository.existsByUser_IdAndPost_Id(userId,postId)) return;

        Post post=postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found!!"));
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found!!"));
        Like like=new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
        if(!post.getUser().getId().equals(userId)){
            notificationService.send(post.getUser().getId(),username+"Liked your post!!");

        }

    }
    public long countLikes(Long postId){
        return likeRepository.countByPost_Id(postId);
    }
    @Transactional
    public void unlikePost(Long userId, Long postId) {
        if (!likeRepository.existsByUser_IdAndPost_Id(userId, postId))
        {
            throw new RuntimeException("Like not found"); }
        likeRepository.deleteByUser_IdAndPost_Id(userId, postId); }

}
