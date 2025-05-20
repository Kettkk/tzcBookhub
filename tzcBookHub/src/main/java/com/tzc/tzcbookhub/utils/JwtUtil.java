package com.tzc.tzcbookhub.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "3Fj9K2qWxL8pM7zR1vCnYoT0eUbGaQxJfPhDsIkLmNrVwEzXtCuSbYlOdIjQmHpWeRaTfGyBkCqDzXsVnBtFmLgHpJrCwEiUkOzNpQlSxVjAyBvCmDwEqRtUyOiPkZsXcVaJhLeGnRfTyUiOpLkMjNhBgVcXaSzDwEqRfTgYhUjKiLoPzXcVbNmAsDfGhJkLqWeRtYuIoPaSkDfGjLmNbVcXzAwSeDcRfVtYbNmQpLoKiJhGfEdCsAzXqWcErTyUiOpLmNjBkVfZaXsEdRfTgYhUjKiLoPzXcVbNmAsDfGhJkLqWeRtYuIoPaSkDfGjLmNbVcXzAwSeDcRfVtYbNmQpLoKiJhGfEdCsAzXqWcErTyUiOpLmNjBkVfZaXsEdRfTgYhUjKiLoPzXcVbNmAsDfGhJkLqWeRtYuIoPaSkDfGjLmNbVcXzAwSeDcRfT";
    private static final long EXPIRATION = 24 * 60 * 60 * 1000; // 1天

    // 生成包含 user_id、username、password 的 JWT
    public static String generateToken(int userId, String username, String password) {
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("username", username)
                .claim("password", password)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
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

    public static String getPasswordFromToken(String token) {
        return parseToken(token).get("password", String.class);
    }
}