package org.miobook.repositories;

import lombok.Getter;
import org.miobook.commands.AddBook;
import org.miobook.commands.AddReview;
import org.miobook.commands.ShowBookContent;
import org.miobook.commands.ShowBookDetails;
import org.miobook.models.*;
import org.miobook.commands.*;
import java.util.Optional;
import java.time.LocalDateTime;

import org.miobook.responses.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Repository
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

    public void add(Book newBook) {
        books.add(newBook);
    }

}
