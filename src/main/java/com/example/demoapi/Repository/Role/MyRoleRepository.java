package com.example.demoapi.Repository.Role;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);
}
