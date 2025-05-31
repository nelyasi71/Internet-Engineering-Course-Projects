package org.miobook.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.RedisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookServices bookServices;
    @Autowired
    RedisServices redisServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"admin"})
    @PostMapping("/book")
    public BaseResponse<Void> add_book(@RequestBody AddBook command, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        command.setUsername(username);
        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/books/{title}")
    public BaseResponse<BookRecord> show_details(@PathVariable String title) {
        ShowBookDetails command = new ShowBookDetails();
        command.setTitle(title);
        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"admin"})
    @GetMapping("/get-books")
    public BaseResponse<AllBooksRecord> show_all_books(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        ShowAllBooks command = new ShowAllBooks();
        command.setUsername(username);
        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/books/{title}/content")
    public BaseResponse<BookContentRecord> show_content(@PathVariable String title, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        ShowBookContent command = new ShowBookContent();
        command.setUsername(username);
        command.setTitle(title);

        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/books/{title}/reviews")
    public BaseResponse<BookReviewRecord> show_reviews(@PathVariable String title) {
        ShowBookReviews command = new ShowBookReviews();
        command.setTitle(title);

        return command.execute(bookServices);
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/books/{title}/review")
    public BaseResponse<Void> add_review(@RequestBody AddReview command, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        command.setUsername(username);
        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/books")
    public BaseResponse<SearchedBooksRecord> search_book(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer to,
            @RequestParam(defaultValue = "none") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
            ) {

        SearchBooks command = new SearchBooks();
        command.setTitle(title);
        command.setAuthor(author);
        command.setGenre(genre);
        command.setFrom(from);
        command.setTo(to);
        command.setSortBy(sortBy);
        command.setOrder(order);
        command.setPage(page);
        command.setSize(size);

        return command.execute(bookServices);
    }
    
}
