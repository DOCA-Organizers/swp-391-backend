package com.example.demoapi.Endpoint.User;

import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        User user = userService.SearchUserByUserName(username);
        if(user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user");
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(userService.isUserNameDupplicated(user.getUserName()) || userService.isEmailDupplicated(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or Email is dupplicated.");
        } else {
            if (userService.save(user)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Create User Failed !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String id){
        if(userService.SearchUserById(id) == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            if (userService.updateUserInfo(user, id)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update User failed !");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> disableUser(@PathVariable("username") String username) {
        if(userService.disableUserByUserName(username)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot disable.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody loginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
       User user = userService.login(username,password);
       if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
       }
       else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user");
       }
    }

    //test
}
