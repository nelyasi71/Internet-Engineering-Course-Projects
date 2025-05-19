package org.miobook.services;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.UserLoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
public class AuthServices implements Services {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisServices redisServices;

    public UserLoggedIn login(Login dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());

        if (userOpt.isEmpty()) {
            throw new MioBookException("username", "User not found.");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new MioBookException("password", "Wrong password.");
        }

        String token = UUID.randomUUID().toString();

        redisServices.saveToken(
            token,
            user.getUsername(),
            user.getRole(),
            Duration.ofMinutes(20)
        );

        return new UserLoggedIn(token);
    }

    public void logout(Logout dto) {
        String token = dto.getToken();
        redisServices.deleteToken(token);
    }
}
