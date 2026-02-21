package com.CircleUp.CircleUp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    @jakarta.validation.constraints.Size(max = 5000)
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String imageUrl;

    private String hashtags;
    @PrePersist
    protected void onCreate(){
        createdAt= LocalDateTime.now();
    }



}
