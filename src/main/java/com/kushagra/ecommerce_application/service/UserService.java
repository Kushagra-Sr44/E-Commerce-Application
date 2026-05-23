package com.kushagra.ecommerce_application.service;

import com.kushagra.ecommerce_application.dto.UserDto;
import com.kushagra.ecommerce_application.entity.User;
import com.kushagra.ecommerce_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> createUser(User user) {
        Optional<User> oldUser = userRepository.findByusername(user.getUsername());
        if (oldUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of("User")));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> getUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByusername(username).orElse(null);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> editUser(Authentication authentication, UserDto userDto) {
        String username = authentication.getName();
        User user = userRepository.findByusername(username).orElse(null);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByusername(username).orElse(null);
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userRepository.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
