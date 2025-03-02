package org.miobook.repositories;

import org.miobook.commands.AddBook;
import org.miobook.models.*;

import java.util.*;

public class BookRepository {
    private final List<Book> books = new ArrayList<>();


    public boolean doesBookExist(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equals(title));
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
}
