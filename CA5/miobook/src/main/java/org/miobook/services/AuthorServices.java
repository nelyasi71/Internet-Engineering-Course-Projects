package org.miobook.services;

import org.miobook.Exception.MioBookException;
import org.miobook.commands.AddAuthor;
import org.miobook.commands.ShowAllAuthors;
import org.miobook.commands.ShowAuthorDetails;
import org.miobook.models.Admin;
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
        if(!userRepository.existsByUsernameAndType(dto.getUsername(), Admin.class)) {
            throw new MioBookException("Only admins can add authors.");
        }
        if(authorRepository.existsByName(dto.getName())) {
            throw new MioBookException("name", "Author with the name '" + dto.getName() + "' already exists.");
        }

        Author author = new Author(
                dto.getName(),
                dto.getPenName(),
                dto.getNationality(),
                dto.getBorn(),
                dto.getDeath()
        );
        authorRepository.save(author);
    }


    public AuthorRecord showAuthorDetails(ShowAuthorDetails dto) {
        Optional<Author> _author = authorRepository.findByName(dto.getName());
        if(_author.isEmpty()) {
            throw new MioBookException("name", "Author with the name '" + dto.getName() + "' does not exist.");
        }

        Author author = _author.get();

        return new AuthorRecord(author.getName(), author.getPenName(), author.getNationality(), author.getBorn(), author.getDeath());
    }

    public AllAuthorsRecord showAllAuthors(ShowAllAuthors dto) {
        return new AllAuthorsRecord(
            authorRepository.findAll().stream()
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
