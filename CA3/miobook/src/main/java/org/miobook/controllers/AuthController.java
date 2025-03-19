package org.miobook.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthServices authServices;

    @PostMapping("/login")
    public BaseResponse<Void> login(@RequestBody Login command, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        command.setSession(session);
        return command.execute(authServices);
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(@RequestBody Logout command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setSession(session);
        return command.execute(authServices);
    }
}
