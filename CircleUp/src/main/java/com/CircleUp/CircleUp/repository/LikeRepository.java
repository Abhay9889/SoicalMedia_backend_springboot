package com.CircleUp.CircleUp.repository;

import com.CircleUp.CircleUp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByUser_IdAndPost_Id(Long userId, Long postId);
    Optional<Like> findByUser_IdAndPost_Id(Long userId, Long postId);
    void deleteByUser_IdAndPost_Id(Long userId, Long postId);
    long countByPost_Id(Long postId);
}
