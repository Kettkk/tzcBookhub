package com.tzc.tzcbookhub.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "z8s4ZK8dNjU4a9YFbJ+nNT1K1s3WXzp0+RJzEoL9v4E=";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
    private static final long EXPIRATION = 24 * 60 * 60 * 1000;

    public static String generateToken(int userId, String username) {
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        return parseToken(token).get("username", String.class);
    }

    public static Integer getUserIdFromToken(String token) {
        return parseToken(token).get("user_id", Integer.class);
    }
}