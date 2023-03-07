package com.blog.blog.controller;

import com.blog.blog.dto.PostDTO;
import com.blog.blog.dto.PostResponse;
import com.blog.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }
    @GetMapping
    PostResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value ="pageSize", defaultValue ="10", required = false) int pageSize,
            @RequestParam(value ="sortBy", defaultValue ="id", required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getByPostById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDTO, @PathVariable Long id){
        PostDTO response = postService.updatePostById(postDTO, id);
        return  new ResponseEntity<>(response , HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
       postService.deletePostById(id);
       return new ResponseEntity<>("Post successsfully deleted!", HttpStatus.OK);
    }
}
