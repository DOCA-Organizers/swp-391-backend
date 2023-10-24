package com.example.demoapi.Endpoint.User;

import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.DTO.User.profileDTO;
import com.example.demoapi.DTO.User.userDTO;
import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getUser(@PathVariable("userid") String userid) {
        User user = userService.getUserById(userid);
        if(user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user");
    }
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(userService.isUserNameDupplicated(user.getUsername()) || userService.isEmailDupplicated(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or Email is dupplicated.");
        } else {
            if (userService.save(user)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create User Failed !");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String id){
        if(userService.getUserById(id) == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            if (userService.updateUserInfo(user, id)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update User failed !");
    }


    @GetMapping("/profile/user/{id}")
    public ResponseEntity<?> getProfileUser(@PathVariable String id){
        if(userService.getUserProfileByUserId(id)!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileByUserId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find this user");
    }


    @PutMapping("/role/{role}/{type}/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable("role") String role, @PathVariable("type") String type, @PathVariable("id") String id){
        if(userService.getUserById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            if (userService.updateUserRole(id, role, type)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update User's role failed !");
    }

    @DeleteMapping("/disable/{username}")
    public ResponseEntity<?> disableUser(@PathVariable("username") String username) {
        if(userService.changeStatusUserByUserName(username)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot disable.");
    }

    @DeleteMapping("/ban/{username}")
    public ResponseEntity<?> banUser(@PathVariable("username") String username) {
        if(userService.changeBanStatusUserByUserName(username)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot disable.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody loginDTO loginDTO){
        userDTO result = userService.loginByUsernameandPassword(loginDTO);
       if(result!=null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
       }
       else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user");
       }
    }
    @GetMapping("/user/getall")
    public ResponseEntity<?> getallUser() {
        List<User> list = userService.getAllUser();
        if(list!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("list null");
    }

}
