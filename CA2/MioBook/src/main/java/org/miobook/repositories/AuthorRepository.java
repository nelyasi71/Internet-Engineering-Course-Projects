package org.miobook.repositories;

import org.miobook.commands.AddAuthor;
import org.miobook.models.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepository {
    private final List<Author> authors = new ArrayList<>();

    public boolean doesExist(String name) {
        return authors.stream()
                .anyMatch(author -> author.getName().equals(name));
    }
    public Optional<Author> getByName(String name) {
        return authors.stream()
                .filter(author -> author.getName().equals(name))
                .findFirst();
    }

    public void add(AddAuthor dto) {

        authors.add(new Author(dto.getName(), dto.getPenName(), dto.getNationality(), dto.getBorn(), dto.getDeath()));
    }
}
