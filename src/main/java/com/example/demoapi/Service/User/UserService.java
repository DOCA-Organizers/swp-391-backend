package com.example.demoapi.Service.User;

import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.DTO.User.profileDTO;
import com.example.demoapi.DTO.User.topDTO;
import com.example.demoapi.DTO.User.userDTO;
import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
public interface UserService {
    User searchUserByUserName(String username);
    List<userDTO> findAll();
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
    User getUserById(String userid);
    boolean changeBanStatusUserByUserName(String username);
    Date getDateStartByUserId(String userId);
    profileDTO getUserProfileByUserId(String userid);
    userDTO loginByUsernameandPassword(loginDTO loginDTO);
    List<User> searchUserNameByKeyWord(String key);
    List<String> getUsernameReactbyPostid(String postid);
    List<String> getUsernameReactbyCommentid(String commentid);
    List<topDTO> getTopUseridPost();
    List<topDTO> getListUserinfo();
    int countUserisActive();
}
