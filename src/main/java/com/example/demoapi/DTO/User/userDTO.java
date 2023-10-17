package com.example.demoapi.DTO.User;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import lombok.Data;

@Data
public class userDTO {
    private User user;
    private Role role;
    private String token;
}
