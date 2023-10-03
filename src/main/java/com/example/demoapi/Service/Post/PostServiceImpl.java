package com.example.demoapi.Service.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Repository.Category.CategoryRepository;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
}
