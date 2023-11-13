package com.example.demoapi.Repository.User;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface MyUserRepository extends JpaRepository<User, String>{
    @Query(value = "select * from tblUser ",nativeQuery = true)
    List<User> getAllUser();
    User findUserById(String id);
    @Query(value = "select * from tblUser where username like %?1% and isactive=1",nativeQuery = true)
    List<User> searchUserByUserName(String username);
    User findUserByUserName(String username);
    User findUserByEmail(String email);
    @Query(value = "update tblUser set isactive = \n" +
                        "case\n" +
                            "when isactive = 1 then 0 \n" +
                            "when isactive = 0 then 1 \n" +
                        "end \n" +
                        "where username = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeStatusUserByUserName(String username);
    @Query(value = "update tblUser set isban = \n" +
                        "case\n" +
                            "when isban = 1 then 0 \n" +
                            "when isban = 0 then 1 \n" +
                        "end \n" +
                        "where username = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeBanStatusUserByUserName(String username);
    @Query(value = "update tblUser set password = :password, fullname = :fullName, " +
            "email = :email, dob = :dob, gender = :gender, avt = :avt where id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateUserById(@Param("password") String password, @Param("fullName") String fullName, @Param("email") String email,
                           @Param("dob") Date dob, @Param("gender") boolean gender, @Param("avt") String avt, @Param("id") String id);
    @Query(value = "select top 1 datestart from tblUser_Role where userid = ? order by datestart asc",nativeQuery = true)
    Date getDateStart(String Userid);
    @Query(value= "select username from tblUser where id in (select userid from tblReact where postid= ?1 and isactive = 1)",nativeQuery = true)
    List<String> getUserNameReactedbyPostid(String postid);
    @Query(value= "select username from tblUser where id in (select userid from tblReact where commentid= ?1 and isactive = 1)",nativeQuery = true)
    List<String> getUserNameReactedbyCommentid(String commentid);

    @Query(value = "select top 3 userid from tblPost where isactive = 1 group by userid order by count(id) desc",nativeQuery = true)
    List<String> getTop3UserPosted();

    @Query(value = "select userid from tblPost where isactive = 1 group by userid order by count(id) desc",nativeQuery = true)
    List<String> getListUserPosted();


    @Query(value = "select count (id) from tblUser where isactive=1",nativeQuery = true)
    int countUserIsActive();

    @Query(value = "select count(id) from tblPost where userid = ?1 and isactive=1",nativeQuery = true)
    int countPostsbyuserid(String userid);
    @Query(value="select count(id) from tblReact where userid = ?1 and isactive=1",nativeQuery = true)
    int countReactbyuserid(String userid);
    @Query(value = "select count(id) from tblComment where userid = ?1 and isactive=1",nativeQuery = true)
    int countCommentId(String userid);
}
