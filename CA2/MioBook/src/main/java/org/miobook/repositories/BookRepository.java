package org.miobook.repositories;

import org.miobook.commands.AddBook;
import org.miobook.commands.ShowBookDetails;
import org.miobook.models.*;
import org.miobook.responses.BookRecord;

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
    }

    public BookRecord showBookDetails(ShowBookDetails dto) {
        Optional<Book> _book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(_book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        Book book = _book.get();
        double averageRating = book.getReviews().stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0);
        return new BookRecord(book.getTitle(), book.getAuthor().getName(), book.getPublisher(), book.getGenres(), book.getPublishedYear(), book.getPrice(), book.getSynopsis(), averageRating);
    }
}
