package com.example.demoapi.Repository.Role;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyRoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);
    @Query(value = "select * from tblRole where name=?1 and type =?2", nativeQuery = true)
    Role getRoleByNameAndType(String name, String type);
    @Query(value = "select * from tblRole where id = " +
            "(select roleid from tblUser_Role where userid = ?1 and isactive = 'true')", nativeQuery = true)
    Role GetRoleByUserId(String id);
}
