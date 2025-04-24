package org.miobook.services;

import org.miobook.Exception.MioBookException;
import org.miobook.commands.AddAuthor;
import org.miobook.commands.ShowAllAuthors;
import org.miobook.commands.ShowAuthorDetails;
import org.miobook.models.Author;
import org.miobook.repositories.AuthorRepository;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.AllAuthorsRecord;
import org.miobook.responses.AuthorRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServices implements Services {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void addAuthor(AddAuthor dto) {
        if(!userRepository.doesAdminExist(dto.getUsername())) {
            throw new MioBookException("Only admins can add authors.");
        }
        if(authorRepository.doesExist(dto.getName())) {
            throw new MioBookException("name", "Author with the name '" + dto.getName() + "' already exists.");
        }

        authorRepository.add(dto);
    }

    public AuthorRecord showAuthorDetails(ShowAuthorDetails dto) {
        Optional<Author> _author = authorRepository.getByName(dto.getName());
        if(_author.isEmpty()) {
            throw new MioBookException("name", "Author with the name '" + dto.getName() + "' does not exist.");
        }

        Author author = _author.get();

        return new AuthorRecord(author.getName(), author.getPenName(), author.getNationality(), author.getBorn(), author.getDeath());
    }

    public AllAuthorsRecord showAllAuthors(ShowAllAuthors dto) {
        return new AllAuthorsRecord(
            authorRepository.getAuthors().stream()
                .map(author -> new AuthorRecord(
                    author.getName(),
                    author.getPenName(),
                    author.getNationality(),
                    author.getBorn(),
                    author.getDeath()
                ))
                .toList()
        );
    }
}
