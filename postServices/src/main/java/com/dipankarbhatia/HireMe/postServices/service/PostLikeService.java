package com.dipankarbhatia.HireMe.postServices.service;

import com.dipankarbhatia.HireMe.postServices.entity.Post;
import com.dipankarbhatia.HireMe.postServices.entity.PostLike;
import com.dipankarbhatia.HireMe.postServices.exception.BadRequestException;
import com.dipankarbhatia.HireMe.postServices.exception.ResourceNotFoundException;
import com.dipankarbhatia.HireMe.postServices.repository.PostLikeRepository;
import com.dipankarbhatia.HireMe.postServices.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("Liking the post with id: "+ postId);

        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with id " + postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(hasAlreadyLiked) throw new BadRequestException("Post already liked");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        //TODO: send notifications to owner of post

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;

        log.info("Unliking the post with id: "+ postId);

        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with id " + postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!hasAlreadyLiked) throw new BadRequestException("you cannot unlike a post without liking");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
