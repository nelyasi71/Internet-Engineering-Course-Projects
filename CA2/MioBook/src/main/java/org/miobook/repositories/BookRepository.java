package org.miobook.repositories;

import org.miobook.commands.AddBook;
import org.miobook.commands.AddReview;
import org.miobook.models.*;
import org.miobook.commands.*;
import java.util.Optional;
import java.time.LocalDateTime;

import java.util.*;

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
        System.out.println("aaa");
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
}
