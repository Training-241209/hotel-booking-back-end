package com.checkinn.checkinn.Controllers;

import com.checkinn.checkinn.DTOs.UserLoginDTO;
import com.checkinn.checkinn.DTOs.UserResponseDTO;
import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Ping pong!");
        return ResponseEntity.ok().body("Pong!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.registerUser(user);

        return ResponseEntity.ok().body("USER REGISTERED");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
        String token = authService.loginUser(user);

        if (token == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); }

        return ResponseEntity.ok().body(token);
    }
}
