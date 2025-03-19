package org.miobook.services;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.miobook.commands.Login;
import org.miobook.commands.Logout;
import org.miobook.models.User;
import org.miobook.repositories.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class AuthServices implements Services {
    public void login(Login dto) {
        HttpSession session = dto.getSession();

        if (session != null && session.getAttribute("username") != null) {
            throw new RuntimeException("Already logged in as: " + session.getAttribute("username"));
        }

        Optional<User> user = Repositories.userRepository.getUserByUsername(dto.getUsername());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User with username '" + dto.getUsername() + "' not found.");
        }
        if(!user.get().getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Wrong password.");
        }
        assert session != null;

        session.setAttribute("userRole", user.get().getRole());
        session.setAttribute("username", user.get().getUsername());
        System.out.println(session.getAttribute("username") + " logged in");
    }

    public void logout(Logout dto) {
        HttpSession session = dto.getSession();
        if (session == null || session.getAttribute("username") == null) {
            throw new IllegalArgumentException("No user is currently logged in.");
        }

        String loggedInUser = (String) session.getAttribute("username");
        session.invalidate();
    }
}
