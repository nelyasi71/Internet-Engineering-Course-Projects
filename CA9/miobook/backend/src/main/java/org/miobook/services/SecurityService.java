package org.miobook.services;

import jakarta.annotation.PostConstruct;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.miobook.models.User;
import org.miobook.responses.JwtRecord;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class SecurityService {

    private static final int HASH_ITERATIONS = 1024;

    @Value("${jwt.secret}")
    private String jwtSecret;
    private Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String hashPassword(String plainPassword) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String hash = new Sha256Hash(plainPassword, salt, HASH_ITERATIONS).toHex();
        return salt + ":" + hash;
    }

    public JwtRecord generateJwt(User user) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(86400);

        String token = Jwts.builder()
                .setIssuer("MioBook")
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationTime))
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new JwtRecord(token);
    }

}
