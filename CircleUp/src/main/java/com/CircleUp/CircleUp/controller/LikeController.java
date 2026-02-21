package com.CircleUp.CircleUp.controller;


import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/likes")
//@RequiredArgsConstructor
//public class LikeController {
//    private final LikeRepository likeRepository;
//    @PostMapping
//    public Like addLike(@RequestBody Like like){
//        return likeRepository.save(like);
//    }
//
//    @PostMapping public Like addLikes(@RequestBody Like like) {
//        // Check if this user already liked this post
//        if (likeRepository.existsByUser_IdAndPost_Id( like.getUser().getId(), like.getPost().getId()))
//        { throw new RuntimeException("User already liked this post");
//        } return likeRepository.save(like);
//    }
//    @GetMapping
//    public List<Like> getLikes(){
//        return likeRepository.findAll();
//    }
//    @GetMapping("/count/{postId}")
//    public long countLikes(@PathVariable Long postId)
//    { return likeRepository.countByPostId(postId); }
//}

//
//@RestController
//@RequestMapping("/likes")
//@RequiredArgsConstructor
//public class LikeController {
//
//    private final LikeRepository likeRepository;
//
//    @PostMapping
//    public Like addLike(@RequestBody Like like) {
//        if (likeRepository.existsByUser_IdAndPost_Id(
//                like.getUser().getId(),
//                like.getPost().getId())) {
//            throw new RuntimeException("User already liked this post");
//        }
//        return likeRepository.save(like);
//    }
//
//    @GetMapping
//    public List<Like> getLikes() {
//        return likeRepository.findAll();
//    }
//
//    @GetMapping("/count/{postId}")
//    public long countLikes(@PathVariable Long postId) {
//        return likeRepository.countByPost_Id(postId);
//    }
//}
//

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/{postId}")
    public String like(@PathVariable Long postId, @AuthenticationPrincipal User user){
        likeService.liekPost(user.getId(), postId, user.getName());
        return "Post liked successfully!";
    }

    @DeleteMapping("/{postId}")
    public String unlike(@PathVariable Long postId,@AuthenticationPrincipal User user){
        likeService.unlikePost(user.getId(), postId);
        return "Post unliked successfully!";
    }
    @GetMapping("/count/{postId}")
    public long countLikes(@PathVariable Long postId){
        return likeService.countLikes(postId);
    }
}