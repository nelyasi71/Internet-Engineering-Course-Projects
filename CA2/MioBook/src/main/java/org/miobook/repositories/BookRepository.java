package org.miobook.repositories;

import org.miobook.models.*;

import java.util.Map;
import java.util.Set;

public class BookRepository {
    Set<Book> books;
    Map<Book, Set<Customer>> owners;
    Map<Book, Set<Customer>> borrower;
}
