package com.example.demoapi.DTO.Post;

import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class commentDTO {
    private String content;
    private String userId;
    private String postId;
}
