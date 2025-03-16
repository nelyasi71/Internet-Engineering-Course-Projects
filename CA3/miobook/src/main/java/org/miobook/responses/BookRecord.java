package org.miobook.responses;

import org.miobook.models.Author;

import java.util.List;

public record BookRecord (
        String title,
        String author,
        String publisher,
        List<String> genres,
        int year,
        int price,
        String synopsis,
        double averageRating
) {
}
