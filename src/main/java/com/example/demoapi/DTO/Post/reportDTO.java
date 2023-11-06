package com.example.demoapi.DTO.Post;

import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import lombok.Data;

@Data
public class reportDTO {
    private String userId;
    private String postId;
    private String commentId;
    private String message;

}
