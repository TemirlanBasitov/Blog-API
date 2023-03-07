package com.blog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDTO {
    private  long id;
    @NotEmpty(message = "Name should not be null or empty!")
    private String name;
    @NotEmpty(message = "Email should not be null or empty!")
    @Email
    private String email;
    @NotEmpty(message = "Body should not be null or empty!")
    @Size(min = 10, message = "Body should  be at least 10 characters!")
    private String body;

}
