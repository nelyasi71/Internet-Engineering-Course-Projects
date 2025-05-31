package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PurchasedBookItemRecord(
        String title,
        String author,
        String publisher,
        List<String> genres,
        int year,
        int price,
        boolean isBorrowed,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime until
) {
}
