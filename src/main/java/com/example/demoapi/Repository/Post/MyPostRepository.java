package com.example.demoapi.Repository.Post;

import com.example.demoapi.Entity.Post.Category;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPostRepository extends JpaRepository<Post,String> {
    Post findPostById(String postid);
    List<Post> findPostsByCategory(Category category);
    List<Post> findPostsByUser(User user);
    List<Post> searchPostById(String id);
    @Query(value = "select * from tblPost where content like %?%1 and status='true' order by createTime desc",nativeQuery = true)
    List<Post> searchPostsByTitle(String keyword);

    @Query(value = "update tblPost set status='0' where id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    boolean browsePost(String postid);

    @Query(value = "select * from tblPost where id in (select postid from tblBookmark where userid= ?1) order by createTime desc",nativeQuery = true)
    List<Post> showPostbookmark(String userid);

}
