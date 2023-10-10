  package com.example.demoapi.Service.Post;

  import com.example.demoapi.DTO.User.ReportDTO;
  import com.example.demoapi.Entity.Post.Category;
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
        react.setStatus(true);
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
          report.setStatus(true);
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
