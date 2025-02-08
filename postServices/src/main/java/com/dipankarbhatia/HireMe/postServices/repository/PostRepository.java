package com.dipankarbhatia.HireMe.postServices.repository;

import com.dipankarbhatia.HireMe.postServices.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
