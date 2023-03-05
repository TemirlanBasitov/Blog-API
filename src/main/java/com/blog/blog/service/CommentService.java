package com.blog.blog.service;

import com.blog.blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(long postId);
    CommentDTO getByCommentById(long postId, long commentId);
}
