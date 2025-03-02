package org.miobook.repositories;

import org.miobook.commands.AddBook;
import org.miobook.models.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookRepository {
    List<Book> books;


    public boolean doesBookExist(String title) {
        return books.stream()
                .anyMatch(book -> book.getTitle().equals(title));
    }


    public void addBook(AddBook dto) {

    }
}
