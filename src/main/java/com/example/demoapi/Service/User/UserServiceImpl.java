package com.example.demoapi.Service.User;

import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.DTO.User.profileDTO;
import com.example.demoapi.DTO.User.topDTO;
import com.example.demoapi.DTO.User.userDTO;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Entity.User.User_Role;
import com.example.demoapi.Repository.Post.PostRepository;
import com.example.demoapi.Repository.Role.RoleRepository;
import com.example.demoapi.Repository.User.UserRepository;
import com.example.demoapi.Repository.User_Role.User_RoleRepository;
import com.example.demoapi.Security.JWTHelper;
import com.example.demoapi.Security.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private User_RoleRepository user_roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Override
    public List<userDTO> findAll() {
        try {
            List<userDTO> list = new ArrayList<>();
            List<User> listUser = userRepository.findAll();
            for (User u: listUser
                 ) {
                userDTO user = new userDTO();
                user.setUser(u);
                user.setRole(roleRepository.GetRoleByUserId(u.getId()));
                user.setToken("null");
                list.add(user);
            }
            return list;

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserById(String id) {
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
        try {
            User_Role user_role = new User_Role();
            user_role.setUserId(user);
            Role role = roleRepository.getRoleByNameAndType("User","All");
            System.out.println(role.getId());
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
    public User searchUserByUserName(String username) {
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
    public User login(String username, String password) {
        try {
            if (bCryptPasswordEncoder.matches(password, userRepository.findUserByUserName(username).getPassword())) {
                return userRepository.findUserByUserName(username);
            } else {
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
            user_role.setRoleId(roleRepository.getRoleByNameAndType(role, type));
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
        } catch (DataIntegrityViolationException e) {
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

    @Override
    public Date getDateStartByUserId(String userId) {
        try {
            return userRepository.getDateStart(userId);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public profileDTO getUserProfileByUserId(String userid) {
        try {
            profileDTO profile = new profileDTO();
            profile.setUser(userRepository.findUserById(userid));
            profile.setRole(roleRepository.GetRoleByUserId(userid));
            profile.setDatestart(userRepository.getDateStart(userid));
            List<Post> listpost = postRepository.findPostsByUser(userRepository.findUserById(userid));
            int react=0;
            for (Post p: listpost
                 ) {
                int i = userRepository.countReactbyPostid(p.getId());
                react = react + i;
            }
            profile.setNumofreact(react);
            profile.setNumofpost(userRepository.countPostsbyuserid(userid));
            profile.setNumofcomment(userRepository.countCommentId(userid));
            return profile;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public userDTO loginByUsernameandPassword(loginDTO loginDTO) {
        try {
            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();
            User user = this.login(username, password);
            Role role = this.getRoleByUserId(user.getId());
            String Token = myUserDetailsService.loginToken(loginDTO);
            if (user != null) {
                userDTO result = new userDTO();
                result.setUser(user);
                result.setRole(role);
                result.setToken(Token);
                return result;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> searchUserNameByKeyWord(String key) {
        try {
            List<User> list = userRepository.searchUserByUserName(key);
            if (list != null) {
                return list;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getUsernameReactbyPostid(String postid) {
        try {
            List<String> list = userRepository.getUserNameReactedbyPostid(postid);
                if (list != null) {
                return list;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getUsernameReactbyCommentid(String commentid) {
        try {
            List<String> list = userRepository.getUserNameReactedbyCommentid(commentid);
            if (list != null) {
                return list;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<topDTO> getTopUseridPost() {
        try {
           List<String> listuserid = userRepository.getTop3UserPosted();
           List<topDTO> list = new ArrayList<>();
            for (String userid: listuserid
                 ) {
                topDTO top = new topDTO();
                top.setId(userid);
                top.setUserName(userRepository.findUserById(userid).getUsername());
                top.setNumPost(userRepository.countPostsbyuserid(userid));
                list.add(top);
            }
            if (list != null) {
                return list;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<topDTO> getListUserinfo() {
        try {
            List<String> listuserid = userRepository.getListUserPosted();
            List<topDTO> list = new ArrayList<>();
            for (String userid: listuserid
            ) {
                topDTO top = new topDTO();
                top.setId(userid);
                top.setUserName(userRepository.findUserById(userid).getUsername());
                top.setNumPost(userRepository.countPostsbyuserid(userid));
                list.add(top);
            }
            if (list != null) {
                return list;
            } else return null;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int countUserisActive() {
        try {
           int list = userRepository.countUserIsActive();
           return list;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
