package org.miobook.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(authenticated)")
    public void checkAuthentication(JoinPoint joinPoint, Authenticated authenticated) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new IllegalArgumentException("Not logged in");
        }

        String userRole = (String) session.getAttribute("userRole");
        if (authenticated.roles().length > 0 && !Arrays.asList(authenticated.roles()).contains(userRole)) {
            throw new IllegalArgumentException("Access denied");
        }
    }
}
