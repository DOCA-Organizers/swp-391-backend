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
        try {
            return userRepository.findAll();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User SearchUserById(String id) {
        try {
            return userRepository.findUserById(id);
        } catch (DataIntegrityViolationException e) {
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
            user.setBan(false);
            user.setIsActive(true);
            userRepository.save(user);
            saveUser_Role(user);
            return true;
        } catch (DataIntegrityViolationException e) {
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
            Role role = roleRepository.findRoleByNameAndType("User", "All");
            user_role.setRoleId(role);
            Date currentDateTime = new Date();
            user_role.setDateStart(currentDateTime);
            user_role.setActive(true);
            user_roleRepository.save(user_role);
            return true;
        } catch (DataIntegrityViolationException e) {
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
                    user.getEmail(), user.getDob(), user.isGender(), user.getAvt(), id) == 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeStatusUserByUserName(String username) {
        try {
            return userRepository.changeStatusUserByUserName(username) == 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUserIdDupplicated(String id) {
        try {
            return (userRepository.findUserById(id) != null);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUserNameDupplicated(String username) {
        try {
            return (userRepository.findUserByUserName(username) != null);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean isEmailDupplicated(String email) {
        try {
            return (userRepository.findUserByEmail(email) != null);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String username,String password){
        try {
            if (bCryptPasswordEncoder.matches(password,userRepository.findUserByUserName(username).getPassword())){
                return userRepository.findUserByUserName(username);
            }
            else {
                return null;
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUserRole(String userId, String role, String type) {
        try {
            Date curDate = new Date();
            User user = userRepository.findUserById(userId);
            user_roleRepository.updateUserRole(curDate, user.getId());
            User_Role user_role = new User_Role();
            user_role.setUserId(user);
            user_role.setRoleId(roleRepository.findRoleByNameAndType(role, type));
            user_role.setDateStart(curDate);
            user_role.setActive(true);
            user_roleRepository.save(user_role);
            return true;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Role getRoleByUserId(String id) {
        try {
            return roleRepository.GetRoleByUserId(id);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAllUser() {
        try {
            return userRepository.getAllUser();
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changeBanStatusUserByUserName(String username) {
        try {
            return userRepository.changeBanStatusUserByUserName(username) == 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
