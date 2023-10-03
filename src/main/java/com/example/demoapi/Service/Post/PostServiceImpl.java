package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
      return postRepository.findPostsByCategory(categoryRepository.findCategoryById(categoryid));
  }
  @Override
  public List<Post> findPostsByUser(String userid){
    return postRepository.findPostByUser(userRepository.findUserById(userid));
  }

  @Override
  public List<Post> searchPostsByTitle(String keyword) {
    return postRepository.searchPostsByTitle(keyword);
  }

  @Override
  public boolean browsePost(String postid) {
    return postRepository.browsePost(postid);
  }

}
