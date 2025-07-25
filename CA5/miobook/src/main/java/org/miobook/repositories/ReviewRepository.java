package org.miobook.repositories;

import org.miobook.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long> {
}
