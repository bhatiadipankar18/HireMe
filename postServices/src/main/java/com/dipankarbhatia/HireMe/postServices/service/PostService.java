package com.dipankarbhatia.HireMe.postServices.service;

import com.dipankarbhatia.HireMe.postServices.dto.PostCreateRequestDto;
import com.dipankarbhatia.HireMe.postServices.dto.PostDto;
import com.dipankarbhatia.HireMe.postServices.entity.Post;
import com.dipankarbhatia.HireMe.postServices.exception.ResourceNotFoundException;
import com.dipankarbhatia.HireMe.postServices.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("creating post for user with id: " + userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.info("getting the post with ID: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not found with ID: "+ postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all the posts of a user with ID: {}", userId);
        List<Post> postList = postRepository.findByUserId(userId);

        return postList
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
