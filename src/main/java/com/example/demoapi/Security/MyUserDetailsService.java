package com.example.demoapi.Security;

import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.Entity.User.Role;
import com.example.demoapi.Entity.User.User;
import com.example.demoapi.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHelper helper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public String loginToken(loginDTO request) {
        try {
            UserDetails userDetails = this.loadUserByUsername(request.getUsername());
            String token = this.helper.generateToken(userDetails);
            return token;
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
