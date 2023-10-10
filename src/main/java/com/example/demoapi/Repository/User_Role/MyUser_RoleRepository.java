package com.example.demoapi.Repository.User_Role;

import com.example.demoapi.Entity.User.User_Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface MyUser_RoleRepository extends JpaRepository<User_Role, Integer> {
    @Query(value = "update [dbo].[tblUser_Role] set [dateend] = ?1, [status] = 'false' where [userid] = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateUserRole(Date dateEnd, String id);
}
