package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    List<Post> findPostsByCategory(int categoryid);
    List<Post> findPostsByUser(String userid);
    List<Post> searchPostsByTitle(String keyword);

    boolean browsePost(String postid);
}
