package org.miobook.responses;

import java.util.List;

public record PurchasedBooksRecord (

    String username,
    List<PurchasedBookItemRecord> items
) {
}
