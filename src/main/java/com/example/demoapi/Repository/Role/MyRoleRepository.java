package com.example.demoapi.Repository.Role;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyRoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);
    Role findRoleByNameAndType(String name, String type);
    @Query(value = "select * from [dbo].[tblRole] where [id] = " +
            "(select [roleid] from [dbo].[tblUser_Role] where [userid] = ?1)", nativeQuery = true)
    Role GetRoleByUserId(String id);
}
