package org.miobook.services;

import lombok.Getter;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.Jwt;
import org.miobook.responses.JwtPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Getter
public class AuthServices implements Services {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisServices redisServices;

    @Autowired
    private SecurityService securityService;

    public Jwt login(Login dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());

        if (userOpt.isEmpty()) {
            throw new MioBookException("username", "User not found.");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new MioBookException("password", "Wrong password.");
        }

        return securityService.generateJwt(user);
    }

    public void logout(Logout dto) {
        String token = dto.getToken();
//        redisServices.deleteToken(token);
    }
}
