package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record PurchasedBooksRecord (

    String username,
    List<PurchasedBookItemRecord> purchasedBookItemRecords
) {
}
