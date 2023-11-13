package com.example.demoapi.DTO.User;

import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import lombok.Data;

import java.util.Date;
@Data
public class profileDTO {
    private User user;
    private Role role;
    private Date datestart;
    int numofpost;
    int numofcomment;
    int numofreact;
}
