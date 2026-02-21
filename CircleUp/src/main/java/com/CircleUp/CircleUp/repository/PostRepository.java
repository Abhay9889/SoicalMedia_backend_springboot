package com.CircleUp.CircleUp.repository;

import com.CircleUp.CircleUp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post>findByUserIdIn(List<Long>userIds);
    List<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds);

    Page<Post> findAll(Pageable pageable);
}
