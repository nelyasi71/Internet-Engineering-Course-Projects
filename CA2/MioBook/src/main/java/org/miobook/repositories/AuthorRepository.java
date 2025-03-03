package org.miobook.repositories;

import org.miobook.commands.AddAuthor;
import org.miobook.models.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepository {
    private final List<Author> authors = new ArrayList<>();

    public boolean doesAuthorExist(String name) {
        return authors.stream()
                .anyMatch(author -> author.getName().equals(name));
    }
    public Optional<Author> getAuthorByName(String name) {
        return authors.stream()
                .filter(author -> author.getName().equals(name))
                .findFirst();
    }

    public void addAuthor(AddAuthor dto) {
        if(doesAuthorExist(dto.getName())) {
            throw new IllegalArgumentException("not aaa");
        }
        if(!Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        authors.add(new Author(dto.getName(), dto.getPenName(), dto.getBorn(), dto.getDeath()));
    }
}
