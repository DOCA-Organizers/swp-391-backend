package com.example.demoapi.Service.Post;

import com.example.demoapi.DTO.User.ReportDTO;
import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.Post.Report;
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
    boolean reactAPostOrComment(String userId, String postId, String commentId);
    Integer countReact(String id);
    boolean reportAPostOrComment(String userId, String postId, String commentId, String msg);
    Integer countReport(String id);
    Integer countPostByReport();
    List<ReportDTO> showListPostWithNumberOfReport();
    List<Post> searchPostsByTitle(String keyword);
    boolean browsePost(String postid);
    List<Post> showBookmarkPosts(String userid);
//    boolean deleteReactById(String id);
//    boolean deleteReportById(String id);
//    boolean deleteBookmarkByPostId(String postid);
    boolean deletePostByPostID(String postId);

    boolean changeExchange(String postid);
    boolean createPost(Post post);
}
