package com.example.auth_service.service;

import com.example.auth_service.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .subject(email)
                .claim("role",role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSecretKey(),Jwts.SIG.HS256)
                .compact();
    }
    public String extractUsername(String token) {
         return extractClaims(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role",String.class);
    }

    public Boolean isTokenValid(String token) {
        try{
            extractAllClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims=extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
