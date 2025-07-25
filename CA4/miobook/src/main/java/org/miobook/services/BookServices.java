package org.miobook.services;

import org.miobook.Exception.MioBookException;
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
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.stream.Collectors;


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
            throw new MioBookException("Admin with username '" + dto.getUsername() + "' does not exist. Only admins can add books.");
        }
        if(bookRepository.doesBookExist(dto.getTitle())) {
            throw new MioBookException("title", "A book with the title '" + dto.getTitle() + "' already exists.");
        }

        Optional<Author> author = authorRepository.getByName(dto.getAuthor());

        if(author.isEmpty()) {
            throw new MioBookException("author", "Author with the name '" + dto.getAuthor() + "' does not exist.");
        }

        bookRepository.add(
                new Book(dto.getTitle(), author.get(), dto.getPublisher(), dto.getYear(), dto.getGenres(), dto.getPrice(), dto.getContent(), dto.getSynopsis())
        );
    }

    public BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = bookRepository.getBookByTitle(dto.getTitle());
        if(_book.isEmpty()) {
            throw new MioBookException("title", "Book with the title '" + dto.getTitle() + "' not found.");
        }
        Book book = _book.get();
        return new BookRecord(
                book.getTitle(),
                book.getAuthor().getName(),
                book.getPublisher(),
                book.getGenres(),
                book.getPublishedYear(),
                book.getPrice(),
                book.getSynopsis(),
                book.averageRating(),
                book.getTotalBuys()
        );
    }

    public AllBooksRecord showAllBooks(ShowAllBooks dto) {
        return new AllBooksRecord(
                bookRepository.getBooks().stream()
                        .map(book -> new BookRecord(
                                book.getTitle(),
                                book.getAuthor().getName(),
                                book.getPublisher(),
                                book.getGenres(),
                                book.getPublishedYear(),
                                book.getPrice(),
                                book.getSynopsis(),
                                book.averageRating(),
                                book.getTotalBuys()
                        ))
                        .toList()
        );
    }

    public BookContentRecord showBookContent(ShowBookContent dto) {
        Optional<Customer> customer = userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new MioBookException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        Optional<Book> book = bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }

        if(!customer.get().hasBook(book.get().getTitle())) {
            throw new MioBookException("Customer with username '" + dto.getUsername() + "' does not own the book '" + dto.getTitle() + "'.");
        }

        return new BookContentRecord(dto.getTitle(), book.get().getAuthor().getName(), book.get().getContent());
    }

    public void addReview(AddReview dto) {
        Optional<Book> bookOptional = bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }
        Book book = bookOptional.get();

        Optional<Customer> customerOptional = userRepository.getCustomerByUsername(dto.getUsername());
        if(customerOptional.isEmpty()) {
            throw new MioBookException("username", "Customer with username '" + dto.getUsername() + "' not found.");
        }
        Customer customer = customerOptional.get();

        if (!customer.hasBook(book.getTitle())) {
            throw new MioBookException("Customer has not purchased this book and cannot add a review.");
        }

        Review review = new Review(customer, dto.getComment(), dto.getRate(), LocalDateTime.now());
        book.addReview(review);
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
            SearchBooksByTitle titleDto = new SearchBooksByTitle();
            titleDto.setTitle(dto.getTitle());
            searchResults.add(searchBooksByTitle(titleDto));
        }
        if (dto.getAuthor() != null) {
            SearchBooksByAuthor authorDto = new SearchBooksByAuthor();
            authorDto.setName(dto.getAuthor());
            searchResults.add(searchBooksByAuthor(authorDto));
        }
        if (dto.getGenre() != null) {
            SearchBooksByGenre genreDto = new SearchBooksByGenre();
            genreDto.setGenre(dto.getGenre());
            searchResults.add(searchBooksByGenre(genreDto));
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

    public static List<SearchedBookItemRecord> applySorting(List<SearchedBookItemRecord> books, String sortBy, String order) {
        Comparator<SearchedBookItemRecord> comparator;
        switch (sortBy != null ? sortBy.toLowerCase() : "") {
            case "average_rating":
                comparator = Comparator.comparing(SearchedBookItemRecord::averageRate);
                break;
            case "review_count":
                comparator = Comparator.comparingInt(SearchedBookItemRecord::reviewCount);
                break;
            default:
                return books;
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        books = books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        return books;
    }

    public static List<SearchedBookItemRecord> applyPagination(List<SearchedBookItemRecord> books, int page, int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, books.size());

        if (fromIndex >= books.size()) {
            return List.of();
        }

        return books.subList(fromIndex, toIndex);
    }

    public static List<SearchedBookItemRecord> findCommonBooks(List<SearchedBooksRecord> searchResults) {
        if (searchResults.isEmpty()) {
            return new ArrayList<>();
        }
        Set<SearchedBookItemRecord> commonBooks = new HashSet<>(searchResults.get(0).books());
        for (int i = 1; i < searchResults.size(); i++) {
            commonBooks.retainAll(new HashSet<>(searchResults.get(i).books()));
        }
        return new ArrayList<>(commonBooks);
    }

    public SearchedBooksRecord searchBooksByTitle(SearchBooksByTitle dto) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(dto.getTitle().toLowerCase()))
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
        return new SearchedBooksRecord(dto.getTitle(),matchedBooks);
    }

    public BookReviewRecord showBookReviews(ShowBookReviews dto) {
        Optional<Book> bookOptional = bookRepository.getBookByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found. Please check the title and try again.");
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

    public SearchedBooksRecord searchBooksByAuthor(SearchBooksByAuthor dto) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
                .filter(book -> book.getAuthor().getName().toLowerCase().contains(dto.getName().toLowerCase()))
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
        return new SearchedBooksRecord(dto.getName(),matchedBooks);
    }

    public SearchedBooksRecord searchBooksByGenre(SearchBooksByGenre dto) {

        List<SearchedBookItemRecord> matchedBooks = bookRepository.getBooks().stream()
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
                        book.getSynopsis(),
                        book.averageRating(),
                        book.ReviewCount()
                ))
                .toList();

//        if (matchedBooks.isEmpty()) {
//            throw new IllegalArgumentException("No books found for the genre '" + dto.getGenre() + "'. Please try a different genre.");
//        }
        return new SearchedBooksRecord(dto.getGenre(),matchedBooks);
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
