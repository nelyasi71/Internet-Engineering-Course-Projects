package org.miobook.repositories;

import org.miobook.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username AND TYPE(u) = :roleClass")
    Optional<User> findByUsernameAndType(@Param("username") String username, @Param("roleClass") Class<? extends User> roleClass);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username AND TYPE(u) = :roleClass")
    boolean existsByUsernameAndType(@Param("username") String username, @Param("roleClass") Class<? extends User> roleClass);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
