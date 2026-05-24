package com.example.umc10th.global.Security.util;

import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.global.Security.entity.AuthMember;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;



@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;

    public JwtUtil(
            @Value("${jwt.token.secret-key}") String secret,
            @Value("${jwt.token.expiration.access}") Long accessExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }

    public String createAccessToken(AuthMember member) {
        return createToken(member, accessExpiration);
    }

    public String getEmail(String token) {
        try { return getClaims(token).getPayload().getSubject(); }
        catch (JwtException e) { return null; }
    }

    // JwtAuthFilter에서 호출하는 getUid 추가
    public String getUid(String token) {
        try { return getClaims(token).getPayload().getSubject(); }
        catch (JwtException e) { return null; }
    }

    public boolean isValid(String token) {
        try { getClaims(token); return true; }
        catch (JwtException e) { return false; }
    }

    public SocialType getSocialType(String token) {
        try {
            return SocialType.valueOf(
                    getClaims(token).getPayload().get("social_type").toString().toUpperCase()
            );
        } catch (JwtException e) { return null; }
    }

    // createToken 하나로 통일 (중복 제거)
    private String createToken(AuthMember member, Duration expiration) {
        Instant now = Instant.now();
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(member.getUsername())
                .claim("role", authorities)
                .claim("email", member.getUsername())
                .claim("social_type", member.getMember().getSocialType())
                .issuedAt(Date.from(now))           // Data → Date
                .expiration(Date.from(now.plus(expiration)))  // Data → Date
                .signWith(secretKey)
                .compact();
    }

    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token);
    }
}