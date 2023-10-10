package com.example.demoapi.DTO.User;

import com.example.demoapi.Entity.Post.Post;
import lombok.Data;

@Data
public class ReportDTO {
    private String postId;
    private Integer numOfPost;
}
