package com.example.demoapi.Repository.Bookmark;

import com.example.demoapi.Entity.Post.Bookmark;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyBookmarkRepository extends JpaRepository<Bookmark,Integer> {
    @Query(value = "select * from tblBookmark where userid = ?1 and postid = ?2",nativeQuery = true)
    Bookmark findBookmarkByUseridAndPostid(String userid, String postid);

    @Query(value = "update [dbo].[tblBookmark] \n" +
            "set [status] = \n" +
            "case \n" +
            "when [status] = 1 then 0\n" +
            "when [status] = 0 then 1\n" +
            "end\n" +
            "where [id] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeBookmarkStatus(Integer id);
}
