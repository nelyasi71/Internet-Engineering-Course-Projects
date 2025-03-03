package org.miobook.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record CartItemRecord (
        String title,
        String author,
        String publisher,
        List<String> genres,
        int year,
        int price,
        boolean isBorrowed,
        int finalPrice,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer borrowDays

) {
}
