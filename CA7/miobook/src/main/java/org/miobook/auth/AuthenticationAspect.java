package org.miobook.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.miobook.Exception.MioBookException;
import org.miobook.services.RedisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisServices redisService;

    @Before("@annotation(authenticated)")
    public void checkAuthentication(JoinPoint joinPoint, Authenticated authenticated) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new MioBookException("Missing token");
        }

        String token = authHeader.substring(7);
        String username = redisService.getUsername(token);
        String role = redisService.getUserRole(token);

        if (username == null) {
            throw new MioBookException("Session expired or invalid");
        }

        if (authenticated.roles().length > 0 && !Arrays.asList(authenticated.roles()).contains(role)) {
            throw new MioBookException("Access Denied");
        }

//        redisService.refreshToken(token, Duration.ofMinutes(20));
    }
}
