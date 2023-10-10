package com.example.demoapi.Repository.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPostRepository extends JpaRepository<Post,String> {
    List<Post> findPostsByCategory(Category category);
    List<Post> findPostByUser(User user);
    Post findPostById(String id);
    @Query(value = "select a.* from [dbo].[tblPost] a where a.[content] like %?1%", nativeQuery = true)
    List<Post> findPostsByContent(String content);

}
