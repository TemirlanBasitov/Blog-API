package com.blog.blog.service;

import com.blog.blog.dto.PostDTO;
import com.blog.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getByPostById(long id);
    PostDTO updatePostById(PostDTO postDTO, Long id);
    void deletePostById(Long id);
}
