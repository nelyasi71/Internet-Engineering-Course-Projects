package org.miobook.services;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class AuthServices implements Services {

    @Autowired
    private UserRepository userRepository;

    public void login(Login dto) {
        HttpSession session = dto.getSession();

        if (session != null && session.getAttribute("username") != null) {
            throw new MioBookException("Already logged in as: " + session.getAttribute("username"));
        }

        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        if(user.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(!user.get().getPassword().equals(dto.getPassword())) {
            throw new MioBookException("password", "Wrong password.");
        }
        assert session != null;

        session.setAttribute("userRole", user.get().getRole());
        session.setAttribute("username", user.get().getUsername());
        session.setAttribute("email", user.get().getEmail());
    }

    public void logout(Logout dto) {
        HttpSession session = dto.getSession();
        if (session == null || session.getAttribute("username") == null) {
            throw new MioBookException("No user is currently logged in.");
        }

        String loggedInUser = (String) session.getAttribute("username");
        session.invalidate();
    }
}
