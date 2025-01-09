package com.checkinn.checkinn.Services;

import com.checkinn.checkinn.DTOs.UserLoginDTO;
import com.checkinn.checkinn.DTOs.UserResponseDTO;
import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    private UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        System.out.println(secretKey);
        this.userRepository = userRepository;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(getSigningKey())
                .compact();
    }

    public UserResponseDTO decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UserResponseDTO(claims.get("userId", Integer.class),
                claims.get("firstName", String.class),
                claims.get("lastName", String.class),
                claims.get("email", String.class));
    }

    public void registerUser(User user) {
        // User's email cannot already be registered
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public String loginUser(UserLoginDTO user) {
        User foundUser = userRepository.findByEmail(user.getEmail()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // Password must match the hashed password
        if (!BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return generateToken(foundUser);
    }
}
