package com.example.demoapi.Service.User;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Entity.User.User_Role;
import com.example.demoapi.Repository.Role.RoleRepository;
import com.example.demoapi.Repository.User.UserRepository;
import com.example.demoapi.Repository.User_Role.User_RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private User_RoleRepository user_roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User SearchUserById(String id) {
        try {
            return userRepository.findUserById(id);
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(User user) {
        try {
            String userid = UUID.randomUUID().toString();
            user.setId(userid);
            while (isUserIdDupplicated(userid)) {
                userid = UUID.randomUUID().toString();
                user.setId(userid);
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            saveUser_Role(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveUser_Role(User user) {
        try{
            User_Role user_role = new User_Role();
            user_role.setUserId(user);
            Role role = roleRepository.findRoleById(3);
            user_role.setRoleId(role);
            Date currentDateTime = new Date();
            user_role.setDateStart(currentDateTime);
            user_role.setStatus(true);
            user_roleRepository.save(user_role);
            return true;
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User SearchUserByUserName(String username) {
        try {
            return userRepository.findUserByUserName(username);
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUserInfo(User user, String id) {
        try {
            return userRepository.updateUserById(bCryptPasswordEncoder.encode(user.getPassword()), user.getFullName(),
                    user.getEmail(), user.getDob(), user.isGender(), user.getImg(), id) == 1;
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean disableUserByUserName(String username) {
        try {
            return userRepository.disableUserByUserName(username) == 1;
        } catch (DataIntegrityViolationException e) {
            // Handle specific database constraint violation (e.g., duplicate entry)
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUserIdDupplicated(String id) {
        return (userRepository.findUserById(id) != null);
    }

    @Override
    public boolean isUserNameDupplicated(String username) {
        return (userRepository.findUserByUserName(username) != null);
    }
    @Override
    public boolean isEmailDupplicated(String email) {
        return (userRepository.findUserByEmail(email) != null);
    }

    @Override
    public User login(String username,String password){
    if (bCryptPasswordEncoder.matches(password,userRepository.findUserByUserName(username).getPassword())){
        return userRepository.findUserByUserName(username);
    }
    else {
        return null;
    }
    }
}
