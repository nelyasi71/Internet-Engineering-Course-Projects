package org.miobook.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.miobook.Exception.MioBookException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTAuthentication extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

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

            request.setAttribute("username", claims.getBody().get("username"));
            request.setAttribute("email", claims.getBody().get("email"));
            request.setAttribute("role", claims.getBody().get("role"));

            try {
                filterChain.doFilter(request, response);
            } catch (Exception ex) {
                throw new MioBookException("Error processing request: " + ex.getMessage());
            }

        } catch (JwtException e) {
            throw new MioBookException("Invalid or expired token");
        }
    }
}