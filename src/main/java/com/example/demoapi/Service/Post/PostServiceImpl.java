package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostServiceImpl implements PostService{
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private UserRepository userRepository;
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
      return postRepository.findPostByUser(userRepository.findUserById(userid));
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
}
