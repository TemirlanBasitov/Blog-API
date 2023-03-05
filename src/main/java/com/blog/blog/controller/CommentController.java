package com.blog.blog.controller;

import com.blog.blog.dto.CommentDTO;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments") //creating commenttp specific post by postId
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId, @RequestBody CommentDTO commentDTO){
        return  new ResponseEntity<CommentDTO>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments") //getting all comments of post by postId
    public List<CommentDTO> getCommentsByPostId(@PathVariable long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value ="id") long id){
        return new ResponseEntity<CommentDTO>(commentService.getByCommentById(postId,id), HttpStatus.OK);
    }
}
