package com.example.demoapi.Repository.Comment;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyCommentRepository extends JpaRepository<Comment,String> {
    List<Comment> findCommentsByPostIdAndIsActiveIsTrue(Post post);
    List<Comment> findCommentsByUserIdAndPostId(User user, Post post);
    @Query(value = "SELECT COUNT(*) AS comment_count\n" +
            "FROM [dbo].[tblComment]\n" +
            "WHERE (postid = ?1 AND [isactive] = 1)\n" , nativeQuery = true)
    Integer countComment(String postid);

    @Query(value = "UPDATE [dbo].[tblComment] SET [content] = ?1 " +
            "WHERE [id] = ?2 AND [isactive] = 1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateComment(String content, String id);
    Comment findCommentById(String id);
    @Query(value = "UPDATE [dbo].[tblComment] SET [isactive] = 0" +
            "WHERE [id] = ?1 AND [isactive] = 1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer deleteComment(String id);
}
