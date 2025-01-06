package com.revature.bdong_ers.Services;

import org.springframework.stereotype.Service;

import com.revature.bdong_ers.DTOs.UserIdPermissionsDTO;
import com.revature.bdong_ers.Entities.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService {

    private RoleService roleService;

    @Autowired
    public AuthService(RoleService roleService) {
        this.roleService = roleService;
    }   

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Thank you for providing a String -> Key conversion, Les Hazlewood
     * (https://stackoverflow.com/questions/55102937/how-to-create-a-spring-security-key-for-signing-a-jwt-token)
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the specified user.
     * Thank you Bao!
     * @param user the user for whom the token is to be generated
     * @return a JWT token as a String
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("admin", roleService.hasAdminPermissions(user))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Decodes the given JWT token and retrieves the subject (ID) from it.
     * Thank you Bao!
     * @param token the JWT token to decode
     * @return the subject (ID) contained in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
     */
    public UserIdPermissionsDTO decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UserIdPermissionsDTO(claims.get("userId", Integer.class), claims.get("admin", Boolean.class));
    }

    /**
     * Gets user ID from a supplied token.
     * @param token the JWT token to decode
     * @return
     */
    public int getTokenId(String token) {
        try {
            UserIdPermissionsDTO userInfo = decodeToken(token);
            return userInfo.getUserId();
        } catch (JwtException e) {
            return -1;
        }
    }

    /**
     * Checks if the user exists - token should decode to a valid ID, since it was provided upon registration or
     * login. Returns false otherwise, or if the token is invalid / expired.
     * @param token the JWT token to decode
     * @return
     */
    public boolean userExists(String token) {
        return getTokenId(token) > 0;
    }

    /**
     * Checks if the user has admin permissions.
     * Returns false if the user does not have admin permissions,
     * or if the token is invalid / expired.
     * @param token the JWT token to decode
     * @return
     */
    public boolean hasAdminPermissions(String token) {
        try {
            return roleService.hasAdminPermissions(getTokenId(token));
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Check if the given userId matches the token's userId.
     * Returns false if it does not match, or if the token is invalid / expired.
     * @param token the JWT token to decode
     * @param userId the userId to compare against
     * @return
     */
    public boolean userMatches(String token, int userId) {
        try {
            return getTokenId(token) == userId;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Checks if the user has admin permissions, or if the givenId matches the token's userId.
     * Mainly used for checking if a user is viewing or modifying their own information / resources.
     * Returns false if both conditions are false, or if the token is invalid / expired.
     * @param token the JWT token to decode
     * @param userId the userId to compare against
     * @return
     */
    public boolean hasAdminPermissionsOrUserMatches(String token, int userId) {
        try {
            int tokenId = getTokenId(token);
            return roleService.hasAdminPermissions(tokenId) || tokenId == userId;
        } catch (JwtException e) {
            return false;
        }
    }
}