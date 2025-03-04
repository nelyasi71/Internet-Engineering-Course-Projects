package org.miobook.services;

import org.miobook.commands.AddAuthor;
import org.miobook.commands.ShowAuthorDetails;
import org.miobook.models.Author;
import org.miobook.repositories.Repositories;
import org.miobook.responses.AuthorRecord;

import java.util.Optional;

public class AuthorServices {
    public static void addAuthor(AddAuthor dto) {
        if(!Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Only admins can add authors.");
        }
        if(Repositories.authorRepository.doesExist(dto.getName())) {
            throw new IllegalArgumentException("Author with the name '" + dto.getName() + "' already exists.");
        }

        Repositories.authorRepository.add(dto);
    }

    public static AuthorRecord showAuthorDetails(ShowAuthorDetails dto) {
        Optional<Author> _author = Repositories.authorRepository.getByName(dto.getUsername());
        if(_author.isEmpty()) {
            throw new IllegalArgumentException("Author with the name '" + dto.getUsername() + "' does not exist.");
        }

        Author author = _author.get();

        return new AuthorRecord(author.getName(), author.getPenName(), author.getNationality(), author.getBorn(), author.getDeath());

    }
}
