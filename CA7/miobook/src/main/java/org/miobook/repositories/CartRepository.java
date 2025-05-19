package org.miobook.repositories;

import org.miobook.models.Cart;
import org.miobook.models.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Long> {
}
