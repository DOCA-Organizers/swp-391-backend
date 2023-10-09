  package com.example.demoapi.Service.Post;

  import com.example.demoapi.Entity.Post.Category;
  import com.example.demoapi.Entity.Post.Post;
  import com.example.demoapi.Entity.Post.React;
  import com.example.demoapi.Repository.Category.CategoryRepository;
  import com.example.demoapi.Repository.Comment.CommentRepository;
  import com.example.demoapi.Repository.Post.PostRepository;
  import com.example.demoapi.Repository.React.ReactRepository;
  import com.example.demoapi.Repository.User.UserRepository;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.dao.DataIntegrityViolationException;
  import org.springframework.stereotype.Repository;
  import org.springframework.stereotype.Service;

  import java.util.Date;
  import java.util.List;
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
    @Override
    public List<Post> findPostsByCategory(int categoryid){
        return postRepository.findPostsByCategory(categoryRepository.findCategoryById(categoryid));
    }
    @Override
    public List<Post> findPostsByUser(String userid){
      return postRepository.findPostByUser(userRepository.findUserById(userid));
    }

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

}
