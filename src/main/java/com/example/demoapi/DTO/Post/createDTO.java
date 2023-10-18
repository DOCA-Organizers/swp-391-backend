package com.example.demoapi.DTO.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.PostImg;
import com.example.demoapi.Entity.User.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class createDTO {
    private String userid;
    private Integer categoryid;
    private String  content;
    private String title;
    private boolean isexchange;
    private List<PostImg> list;
}