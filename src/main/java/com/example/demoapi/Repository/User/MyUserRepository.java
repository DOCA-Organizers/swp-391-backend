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
    @Query(value = "select * from [dbo].[tblUser]",nativeQuery = true)
    List<User> getAllUser();
    User findUserById(String id);
    User findUserByUserName(String username);
    User findUserByEmail(String email);
    @Query(value = "update [dbo].[tblUser] set [statususer] = \n" +
                        "case\n" +
                            "when [statususer] = 1 then 0 \n" +
                            "when [statususer] = 0 then 1 \n" +
                        "end \n" +
                        "where [username] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeStatusUserByUserName(String username);
    @Query(value = "update [dbo].[tblUser] set [isban] = \n" +
                        "case\n" +
                            "when [isban] = 1 then 0 \n" +
                            "when [isban] = 0 then 1 \n" +
                        "end \n" +
                        "where [username] = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer changeBanStatusUserByUserName(String username);
    @Query(value = "update [dbo].[tblUser] set [password] = :password, [fullname] = :fullName, " +
            "[email] = :email, [dob] = :dob, [gender] = :gender, [image] = :img where [id] = :id", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateUserById(@Param("password") String password, @Param("fullName") String fullName, @Param("email") String email,
                           @Param("dob") Date dob, @Param("gender") boolean gender, @Param("img") String img, @Param("id") String id);

}
