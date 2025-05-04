package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.models.Book;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookServices bookServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"admin"})
    @PostMapping("/book")
    public BaseResponse<Void> add_book(@RequestBody AddBook command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
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
    public BaseResponse<AllBooksRecord> show_details(HttpServletRequest request) {
        ShowAllBooks command = new ShowAllBooks();
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(bookServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/books/{title}/content")
    public BaseResponse<BookContentRecord> show_content(@PathVariable String title, HttpServletRequest request) {
        ShowBookContent command = new ShowBookContent();
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
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
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));

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
