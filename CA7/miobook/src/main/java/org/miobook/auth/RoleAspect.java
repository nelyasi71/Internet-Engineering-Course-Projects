package org.miobook.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.miobook.Exception.MioBookException;
import org.miobook.services.RedisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RoleAspect {

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(authenticated)")
    public void checkAuthorization(JoinPoint joinPoint, Authenticated authenticated) {
        String role = (String) request.getAttribute("role");
        String username = (String) request.getAttribute("username");

        if (username == null || role == null) {
            throw new MioBookException("Unauthorized or missing token");
        }

        String[] allowedRoles = authenticated.roles();
        if (allowedRoles.length > 0 && !Arrays.asList(allowedRoles).contains(role)) {
            throw new MioBookException("Access Denied: Role not permitted");
        }
    }
}
