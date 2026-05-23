package com.kushagra.ecommerce_application.controller;

import com.kushagra.ecommerce_application.entity.User;
import com.kushagra.ecommerce_application.service.UserDetailsServiceImpl;
import com.kushagra.ecommerce_application.service.UserService;
import com.kushagra.ecommerce_application.utility.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;
@Autowired
private UserDetailsServiceImpl userDetailsService;
    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "Good";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
            String jwt=jwtUtility.createToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error is Creating Token"+e.getMessage());
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }

}
