package com.kushagra.ecommerce_application.controller;

import com.kushagra.ecommerce_application.dto.UserDto;
import com.kushagra.ecommerce_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/hi")
    public String healthCheck() {
        return "hello";
    }
    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication){
       return userService.getUser(authentication);
    }
    @PutMapping
    public ResponseEntity<?> editUser(Authentication auth , @RequestBody UserDto userDto){
        return userService.editUser(auth,userDto);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(Authentication auth ){
        return userService.deleteUser(auth);
    }


}
