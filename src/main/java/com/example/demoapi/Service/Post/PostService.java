package com.example.demoapi.Service.Post;

import com.example.demoapi.DTO.Post.createDTO;
import com.example.demoapi.DTO.Post.postImg;
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
    List<Post> showBookmarkPosts(String userid);
    boolean deletePostByPostID(String postId);
    int savePostsimg(String postid,List<postImg> listimg);

    boolean changeExchange(String postid);
    String createPost(createDTO post);
    List<Post> searchPostsByContent(String key);
}
