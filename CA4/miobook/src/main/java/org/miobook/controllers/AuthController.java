package org.miobook.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.commands.FetchUser;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.LoggedInUserRecord;
import org.miobook.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthServices authServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user")
    public BaseResponse<LoggedInUserRecord> fetch_user(HttpServletRequest request) {
        FetchUser command = new FetchUser();
        HttpSession session = request.getSession(false);
        command.setSession(session);
        return command.execute(authServices);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<Void> login(@RequestBody Login command, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        command.setSession(session);
        return command.execute(authServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/logout")
    public BaseResponse<Void> logout(@RequestBody Logout command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setSession(session);
        return command.execute(authServices);
    }
}
