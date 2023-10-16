  package com.example.demoapi.Service.Post;

  import com.example.demoapi.DTO.User.ReportDTO;
  import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
  import com.example.demoapi.Entity.Post.React;
  import com.example.demoapi.Entity.Post.Report;
  import com.example.demoapi.Repository.Category.CategoryRepository;
  import com.example.demoapi.Repository.Comment.CommentRepository;
  import com.example.demoapi.Repository.Post.PostRepository;
  import com.example.demoapi.Repository.React.ReactRepository;
  import com.example.demoapi.Repository.Report.ReportRepository;
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
    private ReactRepository reactRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean reactAPostOrComment(String userId, String postId, String commentId) {
      try {
        React react = new React();
        react.setActive(true);
        react.setUserId(userRepository.findUserById(userId));
        react.setPostId(postRepository.findPostById(postId));
        react.setCommentId(commentRepository.findCommentById(commentId));
        React checkReact = reactRepository.findReactByUserIdAndPostIdAndCommentId(userRepository.findUserById(userId),
                postRepository.findPostById(postId), commentRepository.findCommentById(commentId));
        if(checkReact != null) {
            reactRepository.changeReactStatus(checkReact.getId());
        } else {
          reactRepository.save(react);
        }
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
    public Integer countReact(String id) {
      try {
        return reactRepository.countReact(id);
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

  @Override
  public List<Post> findPostsByCategory(int categoryid){
    try{
      return postRepository.findPostsByCategory(categoryRepository.findCategoryById(categoryid));
    }
    catch(DataIntegrityViolationException e){
      e.printStackTrace();
      return null;
    }
    catch (Exception e){
      e.printStackTrace();
      return null;
    }

  }
  @Override
  public List<Post> findPostsByUser(String userid){
    try{
      return postRepository.findPostsByUser(userRepository.findUserById(userid));
    }
    catch(DataIntegrityViolationException e){
      e.printStackTrace();
      return null;
    }
    catch (Exception e){
      e.printStackTrace();
      return null;
    }

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
    } }
  @Override
  public List<Post> searchPostsByTitle(String keyword) {
    try{
      return postRepository.searchPostsByTitle(keyword);
    }
    catch(DataIntegrityViolationException e){
      e.printStackTrace();
      return null;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @Override
  public List<Comment> getCommentsByPost(String postid) {
    try {
        return commentRepository.findCommentsByPostIdAndIsActiveIsTrue(postRepository.findPostById(postid));
    } catch (DataIntegrityViolationException e) {
      // Handle specific database constraint violation (e.g., duplicate entry)
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }}
  public boolean browsePost(String postid) {
    try{
      return postRepository.browsePost(postid);
    }
    catch(DataIntegrityViolationException e){
      e.printStackTrace();
      return false;
    }
    catch (Exception e){
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<Post> showBookmarkPosts(String userid) {
    try{
      return postRepository.showPostbookmark(userid);
    }
    catch(DataIntegrityViolationException e){
      e.printStackTrace();
      return null;
    }
    catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }

    @Override
    public boolean changeExchange(String postid) {
      try{
        return postRepository.changePostExchange(postid)==1;
      }
      catch(DataIntegrityViolationException e){
        e.printStackTrace();
        return false;
      }
      catch (Exception e){
        e.printStackTrace();
        return false;
      }
    }

    @Override
    public boolean createPost(Post post) {
      try{
        postRepository.save(post);
        return true;
      }
      catch(DataIntegrityViolationException e){
        e.printStackTrace();
        return false;
      }
      catch (Exception e){
        e.printStackTrace();
        return false;
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
      comment.setActive(true);
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

    @Override
    public boolean reportAPostOrComment(String userId, String postId, String commentId, String msg) {
      try {
        if(postRepository.findPostById(postId) == null && commentRepository.findCommentById(commentId) == null) {
          return false;
        }
        else {
          Report report = new Report();
          String reportId = UUID.randomUUID().toString();
          report.setId(reportId);
          List<Report> checkReport = reportRepository.findReportsByUserIdAndPostIdAndCommentId(userRepository.findUserById(userId),
                  postRepository.findPostById(postId), commentRepository.findCommentById(commentId));
          if (!checkReport.isEmpty()) {
            int i = 0;
            while (checkReport.get(i).getId().equals(reportId)) {
              reportId = UUID.randomUUID().toString();
              report.setId(reportId);
              i++;
            }
          }
          report.setMessage(msg);
          report.setActive(true);
          report.setCreateTime(new Date());
          report.setUserId(userRepository.findUserById(userId));
          report.setPostId(postRepository.findPostById(postId));
          report.setCommentId(commentRepository.findCommentById(commentId));
          reportRepository.save(report);
          return true;
        }
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }

    @Override
    public Integer countReport(String id) {
      try {
        return reportRepository.countReport(id);
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public Integer countPostByReport() {
      try {
        return reportRepository.countPostByReport();
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public List<ReportDTO> showListPostWithNumberOfReport() {
      try {
        return reportRepository.generateReportList();
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

}
