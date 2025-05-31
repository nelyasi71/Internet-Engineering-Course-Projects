package org.miobook.repositories;

import org.miobook.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository  extends JpaRepository<Purchase, Long> {
}
