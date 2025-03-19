package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.BookContentRecord;
import org.miobook.responses.BookRecord;
import org.miobook.responses.BookReviewRecord;
import org.miobook.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookServices bookServices;

    @Authenticated(roles = {"admin"})
    @PostMapping("/book")
    public BaseResponse<Void> add_book(@RequestBody AddBook command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(bookServices);
    }

    @GetMapping("/book/{title}")
    public BaseResponse<BookRecord> show_details(@PathVariable String title) {
        ShowBookDetails command = new ShowBookDetails();
        command.setTitle(title);
        return command.execute(bookServices);
    }

    @Authenticated(roles = {"customer"})
    @GetMapping("/book/{title}/content")
    public BaseResponse<BookContentRecord> show_content(@PathVariable String title, HttpServletRequest request) {
        ShowBookContent command = new ShowBookContent();
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        command.setTitle(title);

        return command.execute(bookServices);
    }

    @GetMapping("/book/{title}/reviews")
    public BaseResponse<BookReviewRecord> show_reviews(@RequestBody String title) {
        ShowBookReviews command = new ShowBookReviews();
        command.setTitle(title);

        return command.execute(bookServices);
    }

    @Authenticated(roles = {"customer"})
    @PostMapping("/book/{title}/review")
    public BaseResponse<Void> add_review(@RequestBody AddReview command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));

        return command.execute(bookServices);
    }
}
