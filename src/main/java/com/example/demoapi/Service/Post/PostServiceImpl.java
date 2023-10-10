package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Comment.CommentRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService{
  @Autowired
    private PostRepository postRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Override
  public List<Post> findPostsByCategory(int categoryid){
      return postRepository.findPostsByCategory(categoryRepository.findCategoryById(categoryid));
  }
  @Override
  public List<Post> findPostsByUser(String userid){
    return postRepository.findPostByUser(userRepository.findUserById(userid));
  }
  @Override
  public List<Post> findPostsByContent(String content){
    return postRepository.findPostsByContent(content);
  }

  @Override
  public Post findPostById(String id) {
    try {
      return postRepository.findPostById(id);
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @Override
  public List<Comment> getCommentsByPost(String postid) {
    try {
        return commentRepository.findCommentsByPostIdAndStatusIsTrue(postRepository.findPostById(postid));
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean createComment(String userId, String postId, String content) {
    try {
      Comment comment = new Comment();
      String commentid = UUID.randomUUID().toString();
      comment.setId(commentid);
      List<Comment> checkComment = commentRepository.findCommentsByUserIdAndPostId(
              userRepository.findUserById(userId), postRepository.findPostById(postId));
      if (!checkComment.isEmpty()){
        int i = 0;
        while (checkComment.get(i).getId().equals(commentid)) {
          commentid = UUID.randomUUID().toString();
          comment.setId(commentid);
          i++;
        }
      }

      comment.setContent(content);
      comment.setCreateTime(new Date());
      comment.setStatus(true);
      comment.setUserId(userRepository.findUserById(userId));
      comment.setPostId(postRepository.findPostById(postId));
      commentRepository.save(comment);
      return true;
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  @Override
  public Integer countComment(String postid) {
    try {
      return commentRepository.countComment(postid);
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean updateComment(String content, String commentid) {
    try {
        return commentRepository.updateComment(content, commentid) == 1;
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Comment findCommentById(String commentid) {
    try {
      return commentRepository.findCommentById(commentid);
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean deleteComment(String commentid) {
    try {
      return commentRepository.deleteComment(commentid) == 1;
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
