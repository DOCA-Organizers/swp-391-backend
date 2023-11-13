package com.example.demoapi.DTO.Post;

import com.example.demoapi.Entity.Post.Category;
import lombok.Data;

@Data
public class categoryDTO {
    String id;
    String name;
    int numberpost;
    int numbercomment;
}
