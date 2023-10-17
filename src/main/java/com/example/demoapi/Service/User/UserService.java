package com.example.demoapi.Service.User;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
public interface UserService extends UserDetailsService{
    User SearchUserById(String id);
    User SearchUserByUserName(String username);
    List<User> findAll();
    boolean save(User user);
    boolean saveUser_Role(User user);
    boolean updateUserInfo(User user, String id);
    boolean changeStatusUserByUserName(String username);
    boolean isUserIdDupplicated(String id);
    boolean isUserNameDupplicated(String username);
    boolean isEmailDupplicated(String email);
    User login(String username, String password);
    boolean updateUserRole(String userId, String role, String type);
    Role getRoleByUserId(String id);
    List<User> getAllUser();

    boolean changeBanStatusUserByUserName(String username);

}
