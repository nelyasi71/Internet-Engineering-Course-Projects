package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.AddAuthor;
import org.miobook.commands.ShowAllAuthors;
import org.miobook.commands.ShowAuthorDetails;
import org.miobook.responses.AllAuthorsRecord;
import org.miobook.responses.AuthorRecord;
import org.miobook.responses.BaseResponse;
import org.miobook.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    AuthorServices authorServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"admin"})
    @PostMapping("/author")
    public BaseResponse<Void> add_author(@RequestBody AddAuthor command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(authorServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"admin"})
    @GetMapping("/authors")
    public BaseResponse<AllAuthorsRecord> show_all_authors(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowAllAuthors command = new ShowAllAuthors();
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(authorServices);
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/authors/{username}")
    public BaseResponse<AuthorRecord> show_author(@PathVariable String username, HttpServletRequest request) {
        ShowAuthorDetails command = new ShowAuthorDetails();
        command.setName(username);
        return command.execute(authorServices);
    }
}
