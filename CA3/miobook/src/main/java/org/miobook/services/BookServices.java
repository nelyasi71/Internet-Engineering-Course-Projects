package org.miobook.services;

import org.miobook.commands.*;
import org.miobook.models.Author;
import org.miobook.models.Book;
import org.miobook.models.Customer;
import org.miobook.models.Review;
import org.miobook.repositories.AuthorRepository;
import org.miobook.repositories.BookRepository;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.miobook.utils.BookUtil.*;


@Service
public class BookServices implements Services{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public void addBook(AddBook dto) {
        if(!userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' does not exist. Only admins can add books.");
        }
        if(bookRepository.doesBookExist(dto.getTitle())) {
            throw new IllegalArgumentException("A book with the title '" + dto.getTitle() + "' already exists.");
        }

        Optional<Author> author = authorRepository.getByName(dto.getAuthor());

        if(author.isEmpty()) {
            throw new IllegalArgumentException("Author with the name '" + dto.getAuthor() + "' does not exist.");
        }

        bookRepository.add(
                new Book(dto.getTitle(), author.get(), dto.getPublisher(), dto.getYear(), dto.getGenres(), dto.getPrice(), dto.getContent(), dto.getSynopsis())
        );
    }

    public BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = bookRepository.getBookByTitle(dto.getTitle());
        if(_book.isEmpty()) {
            throw new IllegalArgumentException("Book with the title '" + dto.getTitle() + "' not found.");
        }
        Book book = _book.get();
        return new BookRecord(book.getTitle(), book.getAuthor().getName(), book.getPublisher(), book.getGenres(), book.getPublishedYear(), book.getPrice(), book.getSynopsis(), book.averageRating());
    }

    public BookContentRecord showBookContent(ShowBookContent dto) {
        Optional<Customer> customer = userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        Optional<Book> book = bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }

        if(!customer.get().hasBook(book.get().getTitle())) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not own the book '" + dto.getTitle() + "'.");
        }

        return new BookContentRecord(dto.getTitle(), book.get().getContent());
    }

    public void addReview(AddReview dto) {
        Optional<Book> bookOptional = bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }
        Book book = bookOptional.get();

        Optional<Customer> customerOptional = userRepository.getCustomerByUsername(dto.getUsername());
        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }
        Customer customer = customerOptional.get();

        if (!customer.hasBook(book.getTitle())) {
            throw new IllegalArgumentException("Customer has not purchased this book and cannot add a review.");
        }
        Review review = new Review(customer, dto.getComment(), dto.getRate(), setDateIfPresent(dto.getDate()));
        book.addReview(review);
    }

    public LocalDateTime setDateIfPresent(LocalDateTime date) {
        date = Objects.requireNonNullElseGet(date, LocalDateTime::now);
        return date;
    }

    public SearchedBooksRecord searchBooks(SearchBooks dto) {
        List<SearchedBooksRecord> searchResults = new ArrayList<>();

        if (dto.getTitle() == null && dto.getAuthor() == null && dto.getGenre() == null && dto.getFrom() == null) {
            return new SearchedBooksRecord("All Books", bookRepository.getBooks().stream()
                    .map(book -> new SearchedBookItemRecord(
                            book.getTitle(),
                            book.getAuthor().getName(),
                            book.getPublisher(),
                            book.getGenres(),
                            book.getPublishedYear(),
                            book.getPrice(),
                            book.getSynopsis(),
                            book.averageRating(),
                            book.ReviewCount()
                    ))
                    .toList());
        }

        if (dto.getTitle() != null) {
            SearchedBooksRecord titleSearchResult = searchBooksByTitle(dto.getTitle());
            searchResults.add(titleSearchResult);
        }
        if (dto.getAuthor() != null) {
            SearchedBooksRecord authorSearchResult = searchBooksByAuthor(dto.getAuthor());
            searchResults.add(authorSearchResult);
        }
        if (dto.getGenre() != null) {
            SearchedBooksRecord genreSearchResult = searchBooksByGenre(dto.getGenre());
            searchResults.add(genreSearchResult);
        }

        if (dto.getFrom() != null){
            SearchBooksByYear yearDto = new SearchBooksByYear();
            yearDto.setTo(dto.getTo());
            yearDto.setFrom(dto.getFrom());
            searchResults.add(searchBooksByYear(yearDto));
        }

        List<SearchedBookItemRecord> commonBooks = findCommonBooks(searchResults);
        List<SearchedBookItemRecord> sortedBooks = applySorting(commonBooks, dto.getSortBy(),dto.getOrder());
        List<SearchedBookItemRecord> paginatedBooks = applyPagination(sortedBooks, dto.getPage(), dto.getSize());
        return new SearchedBooksRecord(
                "Books By " + dto.getSortBy() + " in " + dto.getOrder() + " order in Page: " + dto.getPage(),
                paginatedBooks
        );
    }


    public SearchedBooksRecord searchBooksByTitle(String title) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis(),
                        book.averageRating(),
                        book.ReviewCount()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found matching the title '" + dto.getTitle() + "'. Please try with a different search term.");
//        }
        return new SearchedBooksRecord(title,matchedBooks);
    }

    public BookReviewRecord showBookReviews(ShowBookReviews dto) {
        Optional<Book> bookOptional = bookRepository.getBookByTitle(dto.getTitle());
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

    public SearchedBooksRecord searchBooksByAuthor(String name) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getAuthor().getName().toLowerCase().contains(name.toLowerCase()))
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis(),
                        book.averageRating(),
                        book.ReviewCount()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found for the author '" + dto.getName() + "'. Please try a different author name.");
//        }
        return new SearchedBooksRecord(name,matchedBooks);
    }

    public SearchedBooksRecord searchBooksByGenre(String bookGenre) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getGenres().stream()
                        .anyMatch(genre -> genre.equalsIgnoreCase(bookGenre))  // Compare each genre in the book with the input genre
                )
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis(),
                        book.averageRating(),
                        book.ReviewCount()
                ))
                .toList();
//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found for the genre '" + dto.getGenre() + "'. Please try a different genre.");
//        }
        return new SearchedBooksRecord(bookGenre,matchedBooks);
    }

    public SearchedBooksRecord searchBooksByYear(SearchBooksByYear dto) {
        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getPublishedYear() >= dto.getFrom() && book.getPublishedYear() <= dto.getTo())
                .map(book -> new SearchedBookItemRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getPublisher(),
                        book.getGenres(),
                        book.getPublishedYear(),
                        book.getPrice(),
                        book.getSynopsis(),
                        book.averageRating(),
                        book.ReviewCount()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found in the given year range.");
//        }
        return new SearchedBooksRecord(dto.getFrom() + " - " + dto.getTo(), matchedBooks);
    }
}
