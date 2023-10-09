package com.example.demoapi.Repository.Bookmark;

import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyBookmarkRepository extends JpaRepository<Bookmark,Integer> {
    @Query(value = "select * from tblBookmark where userid like ?1 and postid like ?2",nativeQuery = true)
    Bookmark findBookmarkByUseridAndPostid(String userid, String postid);


}
