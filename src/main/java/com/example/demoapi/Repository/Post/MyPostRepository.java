package com.example.demoapi.Repository.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPostRepository extends JpaRepository<Post,String> {
    Post findPostById(String id);
    List<Post> findPostsByCategory(Category category);
    List<Post> findPostByUser(User user);
    List<Post> searchPostById(String id);

}
