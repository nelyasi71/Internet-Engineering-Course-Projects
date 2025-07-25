package org.miobook.services;

import org.miobook.commands.*;
import org.miobook.models.Author;
import org.miobook.models.Book;
import org.miobook.models.Customer;
import org.miobook.models.Review;
import org.miobook.repositories.BookRepository;
import org.miobook.repositories.Repositories;
import org.miobook.responses.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServices {



    public static void addBook(AddBook dto) {
        if(!Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' does not exist. Only admins can add books.");
        }
        if(Repositories.bookRepository.doesBookExist(dto.getTitle())) {
            throw new IllegalArgumentException("A book with the title '" + dto.getTitle() + "' already exists.");
        }

        Optional<Author> author = Repositories.authorRepository.getByName(dto.getAuthor());

        if(author.isEmpty()) {
            throw new IllegalArgumentException("Author with the name '" + dto.getAuthor() + "' does not exist.");
        }

        Repositories.bookRepository.add(
                new Book(dto.getTitle(), author.get(), dto.getPublisher(), dto.getYear(), dto.getGenres(), dto.getPrice(), dto.getContent(), dto.getSynopsis())
        );
    }

    public static BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(_book.isEmpty()) {
            throw new IllegalArgumentException("Book with the title '" + dto.getTitle() + "' not found.");
        }
        Book book = _book.get();
        return new BookRecord(book.getTitle(), book.getAuthor().getName(), book.getPublisher(), book.getGenres(), book.getPublishedYear(), book.getPrice(), book.getSynopsis(), book.averageRating());
    }

    public static BookContentRecord showBookContent(ShowBookContent dto) {
        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }

        if(!customer.get().hasBook(book.get().getTitle())) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not own the book '" + dto.getTitle() + "'.");
        }

        return new BookContentRecord(dto.getTitle(), book.get().getContent());
    }

    public static void addReview(AddReview dto) {
        Optional<Customer> customerOptional = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        Optional<Book> bookOptional = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }

        Customer customer = customerOptional.get();
        Book book = bookOptional.get();

        Review review = new Review(customer, dto.getComment(), dto.getRate(), LocalDateTime.now());
        book.addReview(review);
    }

    public static SearchedBooksRecord searchBooksByTitle(SearchBooksByTitle dto) {

        List<SearchedBookItemRecord> matchedBooks = Repositories.bookRepository.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(dto.getTitle().toLowerCase()))
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found matching the title '" + dto.getTitle() + "'. Please try with a different search term.");
//        }
        return new SearchedBooksRecord(dto.getTitle(),matchedBooks);
    }

    public static BookReviewRecord showBookReviews(ShowBookReviews dto) {
        Optional<Book> bookOptional = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found. Please check the title and try again.");
        }
        Book book = bookOptional.get();

        List<ReviewRecord> reviewResponses = book.getReviews().stream()
                .map(review -> new ReviewRecord(
                        review.getCustomer().getUsername(),
                        review.getRate(),
                        review.getComment()
                ))
                .toList();

        return new BookReviewRecord(dto.getTitle(), reviewResponses, book.averageRating());

    }

    public static SearchedBooksRecord searchBooksByAuthor(SearchBooksByAuthor dto) {

        List<SearchedBookItemRecord> matchedBooks = Repositories.bookRepository.getBooks().stream()
                .filter(book -> book.getAuthor().getName().toLowerCase().contains(dto.getName().toLowerCase()))
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found for the author '" + dto.getName() + "'. Please try a different author name.");
//        }
        return new SearchedBooksRecord(dto.getName(),matchedBooks);
    }

    public static SearchedBooksRecord searchBooksByGenre(SearchBooksByGenre dto) {

        List<SearchedBookItemRecord> matchedBooks = Repositories.bookRepository.getBooks().stream()
                .filter(book -> book.getGenres().stream()
                        .anyMatch(genre -> genre.equalsIgnoreCase(dto.getGenre()))
                )
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found for the genre '" + dto.getGenre() + "'. Please try a different genre.");
//        }
        return new SearchedBooksRecord(dto.getGenre(),matchedBooks);
    }

    public static SearchedBooksRecord searchBooksByYear(SearchBooksByYear dto) {
        List<SearchedBookItemRecord> matchedBooks = Repositories.bookRepository.getBooks().stream()
                .filter(book -> book.getPublishedYear() >= dto.getFrom() && book.getPublishedYear() <= dto.getTo())
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found in the given year range.");
//        }
        return new SearchedBooksRecord(dto.getFrom() + " - " + dto.getTo(), matchedBooks);
    }
}
