package com.blog.blog.service;

import com.blog.blog.dto.CommentDTO;
import com.blog.blog.entity.Comment;
import com.blog.blog.entity.Post;
import com.blog.blog.exception.BlogAPIException;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //set post to comment entity
        comment.setPost(post);
        // save comment entity to DB
        Comment savedComment = commentRepository.save(comment);

        return convertToDto(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        //converting comments List to commentDTO List using stream()
        return comments.stream().map(com->convertToDto(com)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getByCommentById(long postId, long commentId) {
        //retrieving post by postId
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //retrieving comment by commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        //checking if retrieved comments belongs to given post
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");

        }
        return convertToDto(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        //retrieving post by postId
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //retrieving comment by commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        //checking if retrieved comments belongs to given post
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        //updating information
        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        //saving
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        //retrieving post by postId
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        //retrieving comment by commentId
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        //checking if retrieved comments belongs to given post
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepository.delete(comment);
    }

    private CommentDTO convertToDto(Comment comment){
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        return dto;
    }
    private Comment convertToEntity(CommentDTO dto){
        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setBody(dto.getBody());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
