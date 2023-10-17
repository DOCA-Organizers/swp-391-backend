package com.example.demoapi.Endpoint.User;


import com.example.demoapi.DTO.JWTDTO.JwtRequest;
import com.example.demoapi.DTO.JWTDTO.JwtResponse;
import com.example.demoapi.DTO.User.loginDTO;
import com.example.demoapi.DTO.User.userDTO;
import com.example.demoapi.Security.JWTHelper;
import com.example.demoapi.Service.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JWTHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public String loginToken(loginDTO request) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
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
