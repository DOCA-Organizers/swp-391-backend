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
    @Query(value = "select * from [dbo].[tblPost]  where isactive=1", nativeQuery = true)
    List<Post> getAllByActiveIsTrue();
    Post findPostById(String id);
    @Query(value = "select a.* from [dbo].[tblPost] a where a.[content] like %?1%", nativeQuery = true)
    List<Post> findPostsByContent(String content);
    List<Post> findPostsByUser(User user);
    @Query(value = "select * from tblPost where id in (select postid from tblBookmark where userid= ?1) order by createtime desc",nativeQuery = true)
    List<Post> showPostbookmark(String userid);
    @Query(value = "UPDATE [dbo].[tblPost] \n" +
                    "SET [isactive] = 0\n" +
                    "WHERE [id] = ?1 AND [isactive] = 1", nativeQuery = true)
    @Transactional
    @Modifying
    Integer deletePostByPostId(String id);

    @Query(value = "update tblPost \n" +
            "set [isSold] = \n" +
            "case \n" +
            "when [isSold] = 1 then 0\n" +
            "when [isSold] = 0 then 1\n" +
            "end\n" +
            "where [id] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changePostisSold(String postid);

    @Query(value = "select count(id) from tblPost where categoryid=?1 ",nativeQuery = true)
    int getNumofPostInCategory(String category);

    @Query(value = "select * from tblPost where id in (select top 10 postid from tblComment where isactive=1 group by postid order by count(id) desc) and isactive=1 and YEAR(createtime)=?1 and MONTH(createtime)=?2",nativeQuery = true)
    List<Post> getTop10PostReactInMonthAndYear(int year , int mount);
}
