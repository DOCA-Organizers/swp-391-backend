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
    List<Post> findPostsByCategory(Category category);
    List<Post> findPostByUser(User user);
    Post findPostById(String id);
    @Query(value = "select a.* from [dbo].[tblPost] a where a.[content] like %?1%", nativeQuery = true)
    List<Post> findPostsByContent(String content);
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

    @Query(value = "update tblPost \n" +
            "set [exchange] = \n" +
            "case \n" +
            "when [exchange] = 1 then 0\n" +
            "when [exchange] = 0 then 1\n" +
            "end\n" +
            "where [id] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changePostExchange(String postid);



}
