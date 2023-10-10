package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    List<Post> findPostsByCategory(int categoryid);
    List<Post> findPostsByUser(String userid);
    List<Post> findPostsByContent(String content);
    Post findPostById(String id);
    List<Comment> getCommentsByPost(String postid);
    boolean createComment(String userId, String postId, String content);
    Integer countComment(String postid);
    boolean updateComment(String content, String commentid);
    Comment findCommentById(String commentid);
    boolean deleteComment(String commentid);
}
