package org.miobook.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.miobook.Exception.MioBookException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;

@Aspect
@Component
public class AuthAspect {

    private Key secretKey;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final HttpServletRequest request;

    public AuthAspect(HttpServletRequest request) {
        this.request = request;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Before("@annotation(authenticated)")
    public void validateTokenAndRole(JoinPoint joinPoint, Authenticated authenticated) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new MioBookException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            String username = claims.getBody().get("username", String.class);
            String role = claims.getBody().get("role", String.class);

            if (username == null || role == null) {
                throw new MioBookException("Invalid token: missing claims");
            }

            request.setAttribute("username", username);
            request.setAttribute("role", role);

            String[] allowedRoles = authenticated.roles();
            if (allowedRoles.length > 0 && !Arrays.asList(allowedRoles).contains(role)) {
                throw new MioBookException("Access Denied: Role not permitted");
            }

        } catch (JwtException e) {
            throw new MioBookException("Invalid or expired token");
        }
    }
}
