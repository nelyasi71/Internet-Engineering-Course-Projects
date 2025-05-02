package org.miobook.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.commands.ShowUserDetails;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.UserRecord;
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
    public BaseResponse<UserRecord> fetch_user(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowUserDetails command = new ShowUserDetails((String) session.getAttribute("username"));
        return command.execute(userServices);
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
