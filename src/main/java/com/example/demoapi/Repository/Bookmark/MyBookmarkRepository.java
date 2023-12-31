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
    @Query(value = "select * from tblBookmark where userid = ?1 and postid = ?2 and isactive=1",nativeQuery = true)
    Bookmark findBookmarkByUseridAndPostid(String userid, String postid);
    @Query(value = "select * from tblBookmark where userid = ?1 and postid = ?2",nativeQuery = true)
    Bookmark findBookmarkByUseridAndPostidUp(String userid, String postid);

    @Query(value = "update [dbo].[tblBookmark] \n" +
            "set [isactive] = \n" +
            "case \n" +
            "when [isactive] = 1 then 0\n" +
            "when [isactive] = 0 then 1\n" +
            "end\n" +
            "where [id] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeBookmarkStatus(Integer id);
    @Query(value = "UPDATE [dbo].[tblBookmark]\n" +
                    "SET [isactive] = 0\n" +
                    "where [postid] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer deleteBookmarkByPostId(String id);
}
