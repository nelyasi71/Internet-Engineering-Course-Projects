package org.miobook.services;

import jakarta.transaction.Transactional;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.AuthorRepository;
import org.miobook.repositories.BookRepository;
import org.miobook.repositories.ReviewRepository;
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


@Service
public class BookServices implements Services{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public void addBook(AddBook dto) {
        Optional<User> user = userRepository.findByUsernameAndType(dto.getUsername(), Admin.class);
        if(user.isEmpty()) {
            throw new MioBookException("Admin with username '" + dto.getUsername() + "' does not exist. Only admins can add books.");
        }
        Admin admin = (Admin) user.get();
        
        if(bookRepository.existsByTitle(dto.getTitle())) {
            throw new MioBookException("title", "A book with the title '" + dto.getTitle() + "' already exists.");
        }

        Optional<Author> author = authorRepository.findByName(dto.getAuthor());
        if(author.isEmpty()) {
            throw new MioBookException("author", "Author with the name '" + dto.getAuthor() + "' does not exist.");
        }

        Book book = new Book(
                dto.getTitle(),
                author.get(),
                dto.getPublisher(),
                dto.getYear(),
                dto.getGenres(),
                dto.getPrice(),
                dto.getContent(),
                dto.getSynopsis(),
                admin
        );

        admin.addBook(book);
        bookRepository.save(book);
    }

    public BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = bookRepository.findByTitle(dto.getTitle());
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
                bookRepository.findAll().stream()
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
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Optional<Book> book = bookRepository.findByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }

        if(!customer.hasBook(book.get().getTitle())) {
            throw new MioBookException("Customer with username '" + dto.getUsername() + "' does not own the book '" + dto.getTitle() + "'.");
        }

        return new BookContentRecord(dto.getTitle(), book.get().getAuthor().getName(), book.get().getContent());
    }

    @Transactional
    public void addReview(AddReview dto) {
        Optional<Book> bookOptional = bookRepository.findByTitle(dto.getTitle());
        if(bookOptional.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }
        Book book = bookOptional.get();

        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        if (!customer.hasBook(book.getTitle())) {
            throw new MioBookException("Customer has not purchased this book and cannot add a review.");
        }

        Review review = new Review(customer, dto.getComment(), dto.getRate(), LocalDateTime.now());
        book.addReview(review);

        review.setBook(book);
        review.setCustomer(customer);
        reviewRepository.save(review);
    }


    public SearchedBooksRecord searchBooks(SearchBooks dto) {
        if (dto.getTitle() == null && dto.getAuthor() == null && dto.getGenre() == null && dto.getFrom() == null) {
            return new SearchedBooksRecord("All Books", mapToRecord(bookRepository.findAll()));
        }

        List<List<SearchedBookItemRecord>> filters = new ArrayList<>();

        if (dto.getTitle() != null)
            filters.add(mapToRecord(bookRepository.findByTitleContainingIgnoreCase(dto.getTitle())));

        if (dto.getAuthor() != null)
            filters.add(mapToRecord(bookRepository.findByAuthorNameContainingIgnoreCase(dto.getAuthor())));

        if (dto.getGenre() != null)
            filters.add(mapToRecord(bookRepository.findByGenreIgnoreCase(dto.getGenre())));

        if (dto.getFrom() != null)
            filters.add(mapToRecord(bookRepository.findByPublishedYearBetween(dto.getFrom(), dto.getTo() != null ? dto.getTo() : LocalDateTime.now().getYear())));

        List<SearchedBookItemRecord> commonBooks = findCommonBooks(filters);
        List<SearchedBookItemRecord> sorted = applySorting(commonBooks, dto.getSortBy(), dto.getOrder());
        List<SearchedBookItemRecord> paged = applyPagination(sorted, dto.getPage(), dto.getSize());

        return new SearchedBooksRecord("Filtered Search", paged);
    }

    public BookReviewRecord showBookReviews(ShowBookReviews dto) {
        Book book = bookRepository.findByTitle(dto.getTitle())
                .orElseThrow(() -> new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found."));

        List<ReviewRecord> reviewRecords = book.getReviews().stream()
                .map(r -> new ReviewRecord(r.getCustomer().getUsername(), r.getRate(), r.getComment()))
                .toList();

        return new BookReviewRecord(book.getTitle(), reviewRecords, book.averageRating());
    }

    private List<SearchedBookItemRecord> mapToRecord(List<Book> books) {
        return books.stream().map(book -> new SearchedBookItemRecord(
                book.getTitle(),
                book.getAuthor().getName(),
                book.getPublisher(),
                book.getGenres(),
                book.getPublishedYear(),
                book.getPrice(),
                book.getSynopsis(),
                book.averageRating(),
                book.ReviewCount()
        )).toList();
    }

    private List<SearchedBookItemRecord> applySorting(List<SearchedBookItemRecord> books, String sortBy, String order) {
        Comparator<SearchedBookItemRecord> comparator = switch (sortBy != null ? sortBy.toLowerCase() : "") {
            case "average_rating" -> Comparator.comparing(SearchedBookItemRecord::averageRate);
            case "review_count" -> Comparator.comparingInt(SearchedBookItemRecord::reviewCount);
            default -> null;
        };

        if (comparator != null) {
            if ("desc".equalsIgnoreCase(order)) comparator = comparator.reversed();
            books = books.stream().sorted(comparator).toList();
        }
        return books;
    }

    private List<SearchedBookItemRecord> applyPagination(List<SearchedBookItemRecord> books, int page, int size) {
        int from = (page - 1) * size;
        int to = Math.min(from + size, books.size());
        return (from >= books.size()) ? List.of() : books.subList(from, to);
    }

    private List<SearchedBookItemRecord> findCommonBooks(List<List<SearchedBookItemRecord>> lists) {
        if (lists.isEmpty()) return List.of();
        Set<SearchedBookItemRecord> common = new HashSet<>(lists.getFirst());
        lists.stream().skip(1).forEach(l -> common.retainAll(new HashSet<>(l)));
        return new ArrayList<>(common);
    }
}
