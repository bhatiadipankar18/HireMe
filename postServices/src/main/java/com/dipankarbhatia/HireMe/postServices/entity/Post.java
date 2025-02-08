package com.dipankarbhatia.HireMe.postServices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
