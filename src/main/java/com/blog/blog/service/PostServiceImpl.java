package com.blog.blog.service;

import com.blog.blog.dto.PostDTO;
import com.blog.blog.dto.PostResponse;
import com.blog.blog.entity.Post;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository ){
        this.postRepository = postRepository;
    }
    @Override
    public PostDTO createPost(PostDTO postDto) {
        Post post = convertToEntity(postDto);

        Post newPost = postRepository.save(post);
        PostDTO postResponse = convertToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        //creating sorting rule by checking given String 'sortDir'
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
               Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //pageable method return type is Page
        Page<Post> posts = postRepository.findAll(pageable);
        //making pageable list usual list
        List<Post> listOfPosts = posts.getContent();
        //converting them into DTO list
        List<PostDTO> content = listOfPosts.stream().map(post->convertToDTO(post)).collect(Collectors.toList());
        //converting DTO list into PostResponse object in order to add extra information for JSON
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;






    }

    @Override
    public PostDTO getByPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        return convertToDTO(post);
    }

    @Override
    public PostDTO updatePostById(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        Post updatedPost = postRepository.save(post);

        return convertToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        postRepository.delete(post);
    }

    private PostDTO convertToDTO(Post post){
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return  dto;
    }
    private Post convertToEntity(PostDTO dto){
        Post entity = new Post();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        return entity;
    }
}
