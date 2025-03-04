package org.miobook.repositories;

import org.miobook.commands.AddBook;
import org.miobook.commands.AddReview;
import org.miobook.commands.ShowBookContent;
import org.miobook.commands.ShowBookDetails;
import org.miobook.models.*;
import org.miobook.commands.*;
import java.util.Optional;
import java.time.LocalDateTime;

import org.miobook.responses.*;

import java.util.*;
import java.util.stream.Collectors;

public class BookRepository {
    private final List<Book> books = new ArrayList<>();


    public boolean doesBookExist(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equals(title));
    }

    public Optional<Book> getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst();
    }


    public void addBook(AddBook dto) {
        if(!Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }
        if(doesBookExist(dto.getTitle())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Author> author = Repositories.authorRepository.getAuthorByName(dto.getAuthor());

        if(author.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Repositories.bookRepository.books.add(
                new Book(dto.getTitle(), author.get(), dto.getPublisher(), dto.getYear(), dto.getGenres(), dto.getPrice(), dto.getContent(), dto.getSynopsis())
        );
    }

    public BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(_book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        Book book = _book.get();
        double averageRating = book.getReviews().stream()
                .mapToDouble(Review::getRate)
                .average()
                .orElse(0);
        return new BookRecord(book.getTitle(), book.getAuthor().getName(), book.getPublisher(), book.getGenres(), book.getPublishedYear(), book.getPrice(), book.getSynopsis(), averageRating);
    }

    public BookContentRecord showBookContent(ShowBookContent dto) {
        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        if(!customer.get().hasBook(book.get().getTitle())) {
            throw new IllegalArgumentException("not aaa");
        }

        return new BookContentRecord(dto.getTitle(), book.get().getContent());
    }

    public void addReview(AddReview dto) {
        Optional<Customer> customerOptional = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Book> bookOptional = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Customer customer = customerOptional.get();
        Book book = bookOptional.get();

        Review review = new Review(customer, dto.getComment(), dto.getRate(), LocalDateTime.now());
        book.addReview(review);
    }

    public SearchedBooksRecord searchBooksByTitle(SearchBooksByTitle dto) {

        List<SearchedBookRecord> matchedBooks = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(dto.getTitle().toLowerCase()))
                .map(book -> new SearchedBookRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .collect(Collectors.toList());

        if (matchedBooks.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        return new SearchedBooksRecord(dto.getTitle(),matchedBooks);
    }

    public BookReviewRecord showBookReviews(ShowBookReviews dto) {
        Optional<Book> bookOptional = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        Book book = bookOptional.get();

        List<ReviewRecord> reviewResponses = book.getReviews().stream()
                .map(review -> new ReviewRecord(
                        review.getCustomer().getUsername(),  // Assuming getUsername() exists in Customer
                        review.getRate(),
                        review.getComment()
                ))
                .collect(Collectors.toList());

        double averageRating = book.getReviews().stream()
                .mapToDouble(Review::getRate)
                .average()
                .orElse(0);

        return new BookReviewRecord(dto.getTitle(), reviewResponses, averageRating);

    }

    public SearchedBooksRecord searchBooksByAuthor(SearchBooksByAuthor dto) {

        List<SearchedBookRecord> matchedBooks = books.stream()
                .filter(book -> book.getAuthor().getName().toLowerCase().contains(dto.getName().toLowerCase()))
                .map(book -> new SearchedBookRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .collect(Collectors.toList());

        if (matchedBooks.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        return new SearchedBooksRecord(dto.getName(),matchedBooks);
    }

    public SearchedBooksRecord searchBooksByGenre(SearchBooksByGenre dto) {

        List<SearchedBookRecord> matchedBooks = books.stream()
                .filter(book -> book.getGenres().stream()
                        .anyMatch(genre -> genre.toLowerCase().contains(dto.getGenre().toLowerCase()))
                )
                .map(book -> new SearchedBookRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .collect(Collectors.toList());

        if (matchedBooks.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        return new SearchedBooksRecord(dto.getGenre(),matchedBooks);
    }
}
