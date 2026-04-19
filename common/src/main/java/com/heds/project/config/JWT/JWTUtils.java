package com.heds.project.config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private static final String ACCESS_SECRET =
            "@./(0=0)/HEDS- (锝兓蠅銉? 路 \\_(銉?_/炉 路 (鈼?H 鈼? 路 (锛忊€敌斺€?锛?Secret-Key-i,.?w91j23ns9";
    private static final String REFRESH_SECRET =
            "KEY_ssHEDS_refresh@./(0=0)/HEDS- (锝兓蠅銉? 路 \\_(銉?_/炉 路 (鈼?H 鈼? 路 (锛忊€敌斺€?锛?Secret-Key-i,.?w91j23ns9";

    private static final long ACCESS_TOKEN_EXPIRE_MILLIS = 60L * 60 * 1000; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRE_MILLIS = 7L * 24 * 60 * 60 * 1000; // 7 days

    private Key accessSigningKey() {
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Key refreshSigningKey() {
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_MILLIS))
                .signWith(accessSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_MILLIS))
                .signWith(refreshSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token, accessSigningKey()).getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return parseClaims(token, refreshSigningKey()).getSubject();
    }

    public boolean validateToken(String token) {
        return isValid(token, accessSigningKey());
    }

    public boolean validateRefreshToken(String token) {
        return isValid(token, refreshSigningKey());
    }

    public Date getExpirationDate(String token) {
        return parseClaims(token, accessSigningKey()).getExpiration();
    }

    public Date getExpirationDateREF(String token) {
        return parseClaims(token, refreshSigningKey()).getExpiration();
    }

    private Claims parseClaims(String token, Key signingKey) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isValid(String token, Key signingKey) {
        try {
            parseClaims(token, signingKey);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
