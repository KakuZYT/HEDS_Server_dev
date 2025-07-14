package com.heds.project.config.JWT;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

        private final String secretKey = "@./(0=0)/HEDS- (｢・ω・) · \\_(ツ)_/¯ · (◕ H ◕) · (／‵Д′)／-Secret-Key-i,.?w91j23ns9";
    private final String RefreshKey = "KEY_ssHEDS_refresh@./(0=0)/HEDS- (｢・ω・) · \\_(ツ)_/¯ · (◕ H ◕) · (／‵Д′)／-Secret-Key-i,.?w91j23ns9";

    private final long expireMillis = 10 * 60 * 60 * 60; // 1 Hour

        //Generate Token
        public String generateToken(String subject) {
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date()) //iat 签发时间
                    .setExpiration(new Date(System.currentTimeMillis() + expireMillis)) //exp 过期时间
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                    .compact(); //output jwt 输出jwt (String format)
        }

        // 生成 refresh token
        // generate refresh token
        public String generateRefreshToken(String suject) {
            return Jwts.builder()
                    .setSubject(suject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                    .signWith(Keys.hmacShaKeyFor(RefreshKey.getBytes()), SignatureAlgorithm.HS256)
                    .compact();
        }

        //Resolve Tokken
        public String getUsernameFromToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes()) //set key
                    .build()
                    .parseClaimsJws(token) // verify signatures and resolve token 验证签名
                    .getBody()
                    .getSubject();
        }
        // resolve refresh token
        public String getUsernameFromRefreshToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(RefreshKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        // refresh token 是否有效
        public boolean validateRefreshToken(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(RefreshKey.getBytes()).build().parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }

        // Verify Token
        public boolean validateToken(String token) {
            try {
                //如果签名不匹配抛出异常
                //If the signature does not match, throw an exception.
                Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }


        //用于获取 token 的过期时间戳
        //Transition period for acquiring tokens
        private io.jsonwebtoken.Claims getClaimsFromToken(String token) {
            return Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                    .build().parseClaimsJws(token).getBody();
        }

    private io.jsonwebtoken.Claims getClaimsFromTokenREF(String token) {
        return Jwts.parserBuilder().setSigningKey(RefreshKey.getBytes())
                .build().parseClaimsJws(token).getBody();
    }

        //return expiration date
        public Date getExpirationDate(String token) {
            return getClaimsFromToken(token).getExpiration();
        }
    //return expiration date
    public Date getExpirationDateREF(String token) {
        return getClaimsFromTokenREF(token).getExpiration();
    }

}
