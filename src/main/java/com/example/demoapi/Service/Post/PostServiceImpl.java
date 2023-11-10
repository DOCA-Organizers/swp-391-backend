  package com.example.demoapi.Service.Post;

  import com.example.demoapi.DTO.Post.createDTO;
  import com.example.demoapi.DTO.Post.postImg;
  import com.example.demoapi.DTO.User.ReportDTO;
  import com.example.demoapi.Entity.Post.*;
  import com.example.demoapi.Repository.Bookmark.BookmarkRepository;
  import com.example.demoapi.Repository.Category.CategoryRepository;
  import com.example.demoapi.Repository.Comment.CommentRepository;
  import com.example.demoapi.Repository.Pet_Breed.Pet_BreedRepository;
  import com.example.demoapi.Repository.Post.PostRepository;
  import com.example.demoapi.Repository.PostImg.PostImgRepository;
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
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private PostImgRepository postImgRepository;
    @Autowired
    private Pet_BreedRepository pet_breedRepository;
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
  public List<Comment> getCommentsByPost(String postid) {
    try {
        return commentRepository.findCommentsByPostIdAndIsActiveIsTrue(postRepository.findPostById(postid));
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }}

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
        return postRepository.changePostisSold(postid)==1;
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
    public String createPost(createDTO createDTO,String petbread) {
      try{Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setCategory(categoryRepository.findCategoryById(createDTO.getCategoryid()));
        post.setUser(userRepository.findUserById(createDTO.getUserid()));
        post.setActive(true);
        post.setCreateTime(new Date());
        post.setContent(createDTO.getContent());
        post.setExchange(createDTO.isExchange());
        if (createDTO.isExchange()) {
          post.setSold(true);
        }
        else post.setSold(false);
        post.setPet_Breed(pet_breedRepository.getPet_BreedById(petbread));
        postRepository.save(post);
        return post.getId();
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
    public int savePostsimg(String postid, List<postImg> listimg) {
      try {
        int result =0;
        Post post = postRepository.findPostById(postid);
        for (postImg p: listimg
        ) {
          PostImg postimg = new PostImg();
          postimg.setId(UUID.randomUUID().toString());
          postimg.setImg(p.getImg());
          postimg.setDescription(p.getDescription());
          postimg.setPostId(post);
          postImgRepository.save(postimg);
          result=result+1;
        }
        return result;
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return 0;
      } catch (Exception e) {
        e.printStackTrace();
        return 0;
      }
    }

    @Override
    public List<Report> getReportByPostId(String postid) {
      try {
        return reportRepository.getReportsByPostId(postid);
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public List<Post> searchPostsByContent(String key) {
      try {
        return postRepository.findPostsByContent(key);
      } catch (DataIntegrityViolationException e) {
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

    @Override
    public boolean deletePostByPostID(String postId) {
      try {
        postRepository.deletePostByPostId(postId);
        commentRepository.deleteCommentByPostId(postId);
        reactRepository.deleteReactById(postId);
        reportRepository.deleteReportById(postId);
        bookmarkRepository.deleteBookmarkByPostId(postId);
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
        return commentRepository.deleteCommentByCommentId(commentid) == 1;
      } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
}
