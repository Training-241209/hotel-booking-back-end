package com.checkinn.checkinn.Services;

import com.checkinn.checkinn.DTOs.UserLoginDTO;
import com.checkinn.checkinn.Entities.Role;
import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.RoleRepository;
import com.checkinn.checkinn.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    public final static String DEFAULT_ROLE_NAME = "User";

    @Value("${JWT_SECRET}")
    private String secretKey;

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Generates a signing key using the secret key.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the supplied user.
     * Contains user's ID, first name, last name, and email.
     * Will contain role information in the future as well.
     *
     * @param user the user to generate the token for
     * @return JWT token as a string
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("roleName", user.getRole().getRoleName())
                .claim("isAdmin", user.getRole().isAdmin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Decodes a JWT token and converts it to get the user's ID.
     *
     * @param token the JWT token to decode
     * @return a UserDTO containing the user ID
     */
    public int decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Integer.class);
    }

    /**
     * Registers a new user and save them to the database.
     * The user's email must not already be registered.
     * The user's password will be securely hashed before storage.
     *
     * @param user the user to be registered
     * @throws ResponseStatusException if the email is already registered
     */
    public void registerUser(User user) {
        // User's email cannot already be registered
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Role defaultRole = roleRepository.findByRoleName(DEFAULT_ROLE_NAME).orElseThrow();
        user.setRole(defaultRole);

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    /**
     * Log in a user.
     * If the credentials are valid, generate and return a JWT token.
     *
     * @param user the UserLoginDTO containing the email and password of the user attempting to log in
     * @return a JWT token as a string if the login is successful
     * @throws ResponseStatusException if the user's email is not found or the password is incorrect
     */
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
