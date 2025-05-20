package org.miobook.controllers;

import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.commands.ShowUserDetails;
import org.miobook.responses.*;
import org.miobook.services.AuthServices;
import org.miobook.services.RedisServices;
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

    @Autowired
    RedisServices redisServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user")
    public BaseResponse<UserRecord> fetch_user(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        ShowUserDetails command = new ShowUserDetails(redisServices.getUsername(token));
        return command.execute(userServices);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<Jwt> login(@RequestBody Login command) {
        return command.execute(authServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/logout")
    public BaseResponse<Void> logout(@RequestBody Logout command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setToken(token);
        return command.execute(authServices);
    }
}
