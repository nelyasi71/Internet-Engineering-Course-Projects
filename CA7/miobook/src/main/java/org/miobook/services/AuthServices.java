package org.miobook.services;

import lombok.Getter;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.JwtRecord;
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

    public JwtRecord login(Login dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());

        if (userOpt.isEmpty()) {
            throw new MioBookException("username", "User not found.");
        }

        User user = userOpt.get();

        String hashedPassword = user.getPassword();
        if (hashedPassword == null || !hashedPassword.contains(":")) {
            throw new IllegalArgumentException("Invalid stored password format");
        }
        String[] parts = hashedPassword.split(":");
        String salt = parts[0];
        String storedHash = parts[1];
        String computedHash = new Sha256Hash(dto.getPassword(), salt, 1024).toHex();
        if (!storedHash.equals(computedHash)) {
            throw new MioBookException("password", "Wrong password.");
        }

        return securityService.generateJwt(user);
    }

    public void logout(Logout dto) {

    }
}
