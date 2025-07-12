package org.miobook.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.miobook.auth.Authenticated;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.commands.OAuth;
import org.miobook.commands.ShowUserDetails;
import org.miobook.responses.*;
import org.miobook.services.AuthServices;
import org.miobook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthServices authServices;
    @Autowired
    UserServices userServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user")
    @Authenticated(roles = {"admin", "customer"})
    public BaseResponse<UserRecord> fetch_user(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        ShowUserDetails command = new ShowUserDetails(username);
        return command.execute(userServices);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<JwtRecord> login(@RequestBody Login command) {
        return command.execute(authServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/logout")
    @Authenticated(roles = {"admin", "customer"})
    public BaseResponse<Void> logout(@RequestBody Logout command, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return command.execute(authServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/google/callback")
    public BaseResponse<JwtRecord> handleGoogleCallback(@RequestParam("code") String code) {
        OAuth command = new OAuth();
        command.setCode(code);
        return command.execute(authServices);
    }
}
