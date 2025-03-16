package org.miobook.repositories;

import org.miobook.models.Author;

public class Repositories {

    public static BookRepository bookRepository = new BookRepository();
    public static UserRepository userRepository = new UserRepository();
    public static AuthorRepository authorRepository = new AuthorRepository();

}
