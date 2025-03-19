package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.AddAuthor;
import org.miobook.commands.ShowAuthorDetails;
import org.miobook.commands.ShowPurchaseHistory;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    AuthorServices authorServices;

    @Authenticated(roles = {"admin"})
    @PostMapping("/author")
    public BaseResponse<Void> add_author(@RequestBody AddAuthor command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(authorServices);
    }

    @PostMapping("/author/{username}")
    public BaseResponse<AuthorRecord> show_author(@PathVariable String username, HttpServletRequest request) {
        ShowAuthorDetails command = new ShowAuthorDetails();
        command.setUsername(username);
        return command.execute(authorServices);
    }
}
