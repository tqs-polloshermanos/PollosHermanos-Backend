package com.github.tqspolloshermanos.backend.Controllers;

import com.github.tqspolloshermanos.backend.DTOs.UserLoginDTO;
import com.github.tqspolloshermanos.backend.DTOs.UserRegistrationDTO;
import com.github.tqspolloshermanos.backend.Entities.User;
import com.github.tqspolloshermanos.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User user = new User(userRegistrationDTO.getEmail(), userRegistrationDTO.getPassword());
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userService.authenticateUser(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
}
