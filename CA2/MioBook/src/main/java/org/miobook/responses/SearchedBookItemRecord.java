package org.miobook.responses;

import java.util.List;

public record SearchedBookItemRecord(
        String title,
        String author,
        String publisher,
        List<String> genres,
        int year,
        int price,
        String synopsis) {
}
