package com.revature.bdong_ers.Controllers;

import com.revature.bdong_ers.DTOs.UserLoginDTO;
import com.revature.bdong_ers.DTOs.UserIdPermissionsDTO;
import com.revature.bdong_ers.DTOs.UserResponseDTO;
import com.revature.bdong_ers.Entities.User;
import com.revature.bdong_ers.Services.AuthService;
import com.revature.bdong_ers.Services.RoleService;
import com.revature.bdong_ers.Services.UserService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class AuthController {

    public static final String AUTH_HEADER_NAME = "authorization";

    private UserService userService;
    private AuthService authService;
    private RoleService roleService;

    @Autowired
    public AuthController(UserService userService, AuthService authService, RoleService roleService) {
        this.userService = userService;
        this.authService = authService;
        this.roleService = roleService;
    }

    @GetMapping(value="/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Ping pong!");
        return ResponseEntity.ok().body("Pong!");
    }

    @Transactional
    @PostMapping(value="/register")
    public ResponseEntity<UserIdPermissionsDTO> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerAccount(user);
        return ResponseEntity.ok().body(new UserIdPermissionsDTO(registeredUser));
    }

    @PostMapping(value="/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        User validUser = userService.loginAccount(userLoginDTO);
        HttpHeaders headers = null;
        UserResponseDTO response = null;
        if (validUser != null) {
            String token = authService.generateToken(validUser);
            response = new UserResponseDTO(validUser, roleService);
            headers = createHttpAuthHeaders(token);
        }
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PatchMapping(value="/promote/{id}")
    public ResponseEntity<UserResponseDTO> promoteUser(@RequestHeader("Authorization") String token,  @PathVariable int id) {

        // Check if user is not an admin
        if (!authService.hasAdminPermissions(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok().body(new UserResponseDTO(roleService.promoteUser(id), roleService));

    }

    private HttpHeaders createHttpAuthHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTH_HEADER_NAME, token);
        httpHeaders.setAccessControlExposeHeaders(Collections.singletonList(AUTH_HEADER_NAME));
        return httpHeaders;
    }
}
