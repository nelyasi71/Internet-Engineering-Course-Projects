package org.miobook.repositories;


import org.miobook.models.*;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    boolean existsByTitle(String title);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Book> findByAuthorNameContainingIgnoreCase(String name);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE LOWER(g) LIKE LOWER(CONCAT('%', :genre, '%'))")
    List<Book> findByGenreIgnoreCase(String genre);

    List<Book> findByPublishedYearBetween(int from, int to);
}

