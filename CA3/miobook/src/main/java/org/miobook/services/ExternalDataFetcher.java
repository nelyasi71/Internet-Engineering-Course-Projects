package org.miobook.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.miobook.commands.AddAuthor;
import org.miobook.commands.AddBook;
import org.miobook.commands.AddReview;
import org.miobook.commands.AddUser;
import org.miobook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalDataFetcher {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    @Autowired
    private UserServices userServices;

    @Autowired
    private BookServices bookServices;

    @Autowired
    private AuthorServices authorServices;

    public ExternalDataFetcher() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // This line fixes LocalDate issue
    }

    public void fetchAndProcessData() {
        fetchUsers();
        fetchAuthors();
        fetchBooks();
        fetchReviews();
    }

    public void fetchUsers() {
        try {
            String url = "http://194.60.230.196:8000/users";
            String json = restTemplate.getForObject(url, String.class);

            AddUser[] users = objectMapper.readValue(json, AddUser[].class);

            for (AddUser user : users) {
                try {
                    userServices.addUser(user);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping user: " + user.getUsername() + " - " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchBooks() {
        try {
            String url = "http://194.60.230.196:8000/books";
            String json = restTemplate.getForObject(url, String.class);

            AddBook[] books = objectMapper.readValue(json, AddBook[].class);

            for (AddBook book : books) {
                try {
                    bookServices.addBook(book);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping book: " + book.getTitle() + " - " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchAuthors() {
        try {
            String url = "http://194.60.230.196:8000/authors";
            String json = restTemplate.getForObject(url, String.class);

            AddAuthor[] authors = objectMapper.readValue(json, AddAuthor[].class);

            for (AddAuthor author : authors) {
                try {
                    authorServices.addAuthor(author);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping author: " + author.getName() + " - " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchReviews() {
        try {
            String url = "http://194.60.230.196:8000/reviews";
            String json = restTemplate.getForObject(url, String.class);

            AddReview[] reviews = objectMapper.readValue(json, AddReview[].class);

            for (AddReview review : reviews) {
                try {
                    bookServices.addReview(review);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping review for book: " + review.getTitle() + " by user: " + review.getUsername() + " - " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
