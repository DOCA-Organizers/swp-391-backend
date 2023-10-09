package com.example.demoapi.Repository.Comment;

import com.example.demoapi.Entity.Post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCommentRepository extends JpaRepository<Comment, String> {
    Comment findCommentById(String id);
}
