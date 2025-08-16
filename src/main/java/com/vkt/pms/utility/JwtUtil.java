package com.vkt.pms.utility;

import com.vkt.pms.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.expirationMillis}")
    private long expirationMillis;

    private final Key secretKey;

    public  JwtUtil(@Value("${jwt.secret}") String secret){
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(this.secretKey)
                .compact();
    }

//    public Claims extractClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(this.secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    public void validateToken(String token) {
        try {
           Jwts.parserBuilder()
                   .setSigningKey(this.secretKey)
                   .build()
                   .isSigned(token);
        } catch (SecurityException | MalformedJwtException | SignatureException ex) {
            throw new JwtException("Invalid JWT Signature");
        }catch (ExpiredJwtException ex){
            throw new JwtException("JWT Token has been expired");
        }catch (JwtException ex){
            throw new JwtException("Invalid JWT Token");
        }
    }

//    public String extractUsername(String token) {
//        return extractClaims(token).getSubject();
//    }
//
//    public String extractRole(String token) {
//        return (String) extractClaims(token).get("role");
//    }
}
