package com.example.demoapi.Repository.React;

import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.User.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface MyReactRepository extends JpaRepository<React, Integer> {
    @Query(value = "SELECT COUNT(*) AS react_count\n" +
                    "FROM [dbo].[tblReact]\n" +
                    "WHERE (postid = ?1 AND commentid IS NULL)\n" +
                    "OR (commentid = ?1 AND postid IS NULL);", nativeQuery = true)
    Integer countReact(String id);
    React findReactByUserIdAndPostIdAndCommentId(User userId, Post postId, Comment commentId);

    @Query(value = "update [dbo].[tblReact] \n" +
                    "set [isactive] = \n" +
                        "case \n" +
                            "when [isactive] = 1 then 0\n" +
                            "when [isactive] = 0 then 1\n" +
                        "end\n" +
                    "where [id] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeReactStatus(Integer id);
    React findReactByUserId(User userId);
}
