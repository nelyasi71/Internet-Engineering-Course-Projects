package org.miobook.repositories;


import org.miobook.models.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    boolean existsByTitle(String title);
}

