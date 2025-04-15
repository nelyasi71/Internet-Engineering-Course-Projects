package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record PurchasedBookItemRecord(
        String title,
        String author,
        String publisher,
        List<String> genres,
        int year,
        int price,
        boolean isBorrowed
) {
}
